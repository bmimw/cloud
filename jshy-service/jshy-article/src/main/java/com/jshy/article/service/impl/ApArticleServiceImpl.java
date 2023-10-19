package com.jshy.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.article.mapper.ApArticleConfigMapper;
import com.jshy.article.mapper.ApArticleContentMapper;
import com.jshy.article.mapper.ApArticleMapper;
import com.jshy.article.service.ApArticleService;
import com.jshy.article.service.ArticleFreemarkerService;
import com.jshy.common.constants.ArticleConstants;
import com.jshy.common.redis.CacheService;
import com.jshy.model.article.dtos.ArticleDto;
import com.jshy.model.article.dtos.ArticleHomeDto;

import com.jshy.model.article.dtos.ArticleInfoDto;
import com.jshy.model.article.dtos.CollectionBehaviorDto;
import com.jshy.model.article.mess.ArticleVisitStreamMess;
import com.jshy.model.article.pojos.ApArticle;
import com.jshy.model.article.pojos.ApArticleConfig;
import com.jshy.model.article.pojos.ApArticleContent;
import com.jshy.model.article.vos.HotArticleVo;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.user.pojos.ApUser;
import com.jshy.utils.thread.AppThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {

    // 单页最大加载的数字
    private final static short MAX_PAGE_SIZE = 50;

    @Autowired
    private ApArticleMapper apArticleMapper;

    /**
     * 根据参数加载文章列表
     *
     * @param loadtype 1为加载更多  2为加载最新
     * @param dto
     * @return
     */
    @Override
    public ResponseResult load(Short loadtype, ArticleHomeDto dto) {
        //1.校验参数
        Integer size = dto.getSize();
        if (size == null || size == 0) {
            size = 10;
        }
        size = Math.min(size, MAX_PAGE_SIZE);
        dto.setSize(size);

        //类型参数检验，如果既不是1，2有效参数，那么就默认1
        if (!loadtype.equals(ArticleConstants.LOADTYPE_LOAD_MORE) && !loadtype.equals(ArticleConstants.LOADTYPE_LOAD_NEW)) {
            loadtype = ArticleConstants.LOADTYPE_LOAD_MORE;
        }
        //文章频道校验
        if (StringUtils.isEmpty(dto.getTag())) {
            dto.setTag(ArticleConstants.DEFAULT_TAG);
        }
        //时间校验
        if (dto.getMaxBehotTime() == null) dto.setMaxBehotTime(new Date());
        if (dto.getMinBehotTime() == null) dto.setMinBehotTime(new Date());
        //2.查询数据
        List<ApArticle> apArticles = apArticleMapper.loadArticleList(dto, loadtype);

        //3.结果封装
        ResponseResult responseResult = ResponseResult.okResult(apArticles);
        return responseResult;
    }

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;


    @Autowired
    ArticleFreemarkerService articleFreemarkerService;

    /**
     * 保存app端相关文章
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveArticle(ArticleDto dto) {
        //1.检查参数
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApArticle apArticle = new ApArticle();
        BeanUtils.copyProperties(dto, apArticle);

        //2.判断是否存在id
        if (dto.getId() == null) {
            //2.1 不存在id  保存  文章  文章配置  文章内容

            //保存文章
            save(apArticle);

            //保存配置
            ApArticleConfig apArticleConfig = new ApArticleConfig(apArticle.getId());
            apArticleConfigMapper.insert(apArticleConfig);

            //保存 文章内容
            ApArticleContent apArticleContent = new ApArticleContent();
            apArticleContent.setArticleId(apArticle.getId());
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.insert(apArticleContent);

        } else {
            //2.2 存在id   修改  文章  文章内容

            //修改  文章
            updateById(apArticle);

            //修改文章内容
            ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, dto.getId()));
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.updateById(apArticleContent);
        }

        //异步调用 生成静态文件上传到minio中
        articleFreemarkerService.buildArticleToMinIO(apArticle, dto.getContent());

        //3.结果返回  文章的id
        return ResponseResult.okResult(apArticle.getId());
    }

    @Autowired
    CacheService cacheService;

    /**
     * 用户收藏、取消收藏
     */
    @Override
    public ResponseResult collectionBehavior(HttpServletRequest request, CollectionBehaviorDto collectionBehaviorDto) {
        //得到header中的信息
        String userId = request.getHeader("userId");
        if (userId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        String keyUser = userId + "_collect";
        String collectUser = collectionBehaviorDto.getEntryId() + "_beCollected";
        if (collectionBehaviorDto.getOperation() == 0) {
            //收藏
            //以用户为主键添加收藏
            cacheService.sAdd(keyUser, String.valueOf(collectionBehaviorDto.getEntryId()));
            //给文章添加收藏者
            cacheService.sAdd(collectUser, userId);
            return ResponseResult.okResult(null);
        } else if (collectionBehaviorDto.getOperation() == 1) {
            //取消收藏
            //以用户为主键取消收藏
            cacheService.sRemove(keyUser, String.valueOf(collectionBehaviorDto.getEntryId()));
            //给文章取消收藏者
            cacheService.sRemove(collectUser, userId);
            return ResponseResult.okResult(null);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

    /**
     * 用户回显
     */
    @Override
    public ResponseResult loadArticleBehavior(ArticleInfoDto articleInfoDto, HttpServletRequest request) {
//        //获取用户
//        ApUser apUser= AppThreadLocalUtils.getUser();
//        if (apUser==null){
//            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
//        }
        //得到header中的信息
        String userId = request.getHeader("userId");
        if (userId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        Map<String, Boolean> map = new HashMap<>();
        // 判断户是否已经关注该文章的作者
        String keyFollow = userId + "_follow";
        map.put("isfollow", cacheService.sIsMember(keyFollow, String.valueOf(articleInfoDto.getAuthorId())));
        // 是否收藏了此文章
        String keyCollect = userId + "_collect";
        map.put("iscollection", cacheService.sIsMember(keyCollect, String.valueOf(articleInfoDto.getArticleId())));
        // 是否点赞了文章
        String keyLike = userId + "_like_0";
        map.put("islike", cacheService.sIsMember(keyLike, String.valueOf(articleInfoDto.getArticleId())));
        // 是否不喜欢该文章
        String keyDislike = userId + "_dislike";
        map.put("isunlike", cacheService.sIsMember(keyDislike, String.valueOf(articleInfoDto.getArticleId())));
        return ResponseResult.okResult(map);
    }

    /**
     * 加载文章列表
     * @param dto
     * @param type      1 加载更多   2 加载最新
     * @param firstPage true  是首页  flase 非首页
     * @return
     */
    @Override
    public ResponseResult load2(ArticleHomeDto dto, Short type, boolean firstPage) {
        if(firstPage){
            String jsonStr = cacheService.get(ArticleConstants.HOT_ARTICLE_FIRST_PAGE + dto.getTag());
            if(StringUtils.isNotBlank(jsonStr)){
                List<HotArticleVo> hotArticleVoList = JSON.parseArray(jsonStr, HotArticleVo.class);
                ResponseResult responseResult = ResponseResult.okResult(hotArticleVoList);
                return responseResult;
            }
        }
        return load(type,dto);
    }

    /**
     * 更新文章的分值  同时更新缓存中的热点文章数据
     * @param mess
     */
    @Override
    public void updateScore(ArticleVisitStreamMess mess) {
        //1.更新文章的阅读、点赞、收藏、评论的数量
        ApArticle apArticle = updateArticle(mess);
        //2.计算文章的分值
        Integer score = computeScore(apArticle);
        score = score * 3;

        //3.替换当前文章对应频道的热点数据
        replaceDataToRedis(apArticle, score, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + apArticle.getChannelId());

        //4.替换推荐对应的热点数据
        replaceDataToRedis(apArticle, score, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + ArticleConstants.DEFAULT_TAG);

    }

    /**
     * 替换数据并且存入到redis
     * @param apArticle
     * @param score
     * @param s
     */
    private void replaceDataToRedis(ApArticle apArticle, Integer score, String s) {
        String articleListStr = cacheService.get(s);
        if (StringUtils.isNotBlank(articleListStr)) {
            List<HotArticleVo> hotArticleVoList = JSON.parseArray(articleListStr, HotArticleVo.class);

            boolean flag = true;

            //如果缓存中存在该文章，只更新分值
            for (HotArticleVo hotArticleVo : hotArticleVoList) {
                if (hotArticleVo.getId().equals(apArticle.getId())) {
                    hotArticleVo.setScore(score);
                    flag = false;
                    break;
                }
            }

            //如果缓存中不存在，查询缓存中分值最小的一条数据，进行分值的比较，如果当前文章的分值大于缓存中的数据，就替换
            if (flag) {
                if (hotArticleVoList.size() >= 30) {
                    hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
                    HotArticleVo lastHot = hotArticleVoList.get(hotArticleVoList.size() - 1);
                    if (lastHot.getScore() < score) {
                        hotArticleVoList.remove(lastHot);
                        HotArticleVo hot = new HotArticleVo();
                        BeanUtils.copyProperties(apArticle, hot);
                        hot.setScore(score);
                        hotArticleVoList.add(hot);
                    }


                } else {
                    HotArticleVo hot = new HotArticleVo();
                    BeanUtils.copyProperties(apArticle, hot);
                    hot.setScore(score);
                    hotArticleVoList.add(hot);
                }
            }
            //缓存到redis
            hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
            cacheService.set(s, JSON.toJSONString(hotArticleVoList));
        }
    }

    /**
     * 更新文章行为数量
     * @param mess
     */
    private ApArticle updateArticle(ArticleVisitStreamMess mess) {
        ApArticle apArticle = getById(mess.getArticleId());
        apArticle.setCollection(apArticle.getCollection()==null?0:apArticle.getCollection()+mess.getCollect());
        apArticle.setComment(apArticle.getComment()==null?0:apArticle.getComment()+mess.getComment());
        apArticle.setLikes(apArticle.getLikes()==null?0:apArticle.getLikes()+mess.getLike());
        apArticle.setViews(apArticle.getViews()==null?0:apArticle.getViews()+mess.getView());
        updateById(apArticle);
        return apArticle;

    }

    /**
     * 计算文章的具体分值
     * @param apArticle
     * @return
     */
    private Integer computeScore(ApArticle apArticle) {
        Integer score = 0;
        if(apArticle.getLikes() != null){
            score += apArticle.getLikes() * ArticleConstants.HOT_ARTICLE_LIKE_WEIGHT;
        }
        if(apArticle.getViews() != null){
            score += apArticle.getViews();
        }
        if(apArticle.getComment() != null){
            score += apArticle.getComment() * ArticleConstants.HOT_ARTICLE_COMMENT_WEIGHT;
        }
        if(apArticle.getCollection() != null){
            score += apArticle.getCollection() * ArticleConstants.HOT_ARTICLE_COLLECTION_WEIGHT;
        }

        return score;
    }
}