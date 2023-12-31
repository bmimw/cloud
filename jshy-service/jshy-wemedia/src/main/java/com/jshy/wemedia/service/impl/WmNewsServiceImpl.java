package com.jshy.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.common.constants.WemediaConstants;
import com.jshy.common.exception.CustomException;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdUserDto;
import com.jshy.model.admin.dtos.AdWemediaNewsDto;
import com.jshy.model.admin.dtos.AdWemediaNewsUserNameDto;
import com.jshy.model.commodity.pojos.CmGoods;
import com.jshy.model.common.dtos.PageResponseResult;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.wemedia.dtos.WmNewsDto;
import com.jshy.model.wemedia.dtos.WmNewsPageReqDto;
import com.jshy.model.wemedia.pojos.*;
import com.jshy.utils.thread.WmThreadLocalUtils;
import com.jshy.wemedia.mapper.WmMaterialMapper;
import com.jshy.wemedia.mapper.WmNewsMapper;
import com.jshy.wemedia.mapper.WmNewsMaterialMapper;
import com.jshy.wemedia.mapper.WmUserMapper;
import com.jshy.wemedia.service.WmNewsAutoScanService;
import com.jshy.wemedia.service.WmNewsService;
import com.jshy.wemedia.service.WmNewsTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    /**
     * 查询文章
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findAll(WmNewsPageReqDto dto) {

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //1.检查参数
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //分页参数检查
        dto.checkParam();
        //获取当前登录人的信息
        WmUser user = WmThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //2.分页条件查询
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmNews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //状态精确查询
        if (dto.getStatus() != null) {
            lambdaQueryWrapper.eq(WmNews::getStatus, dto.getStatus());
        }

        //频道精确查询
        if (dto.getChannelId() != null) {
            lambdaQueryWrapper.eq(WmNews::getChannelId, dto.getChannelId());
        }

        //时间范围查询
        if (dto.getBeginPubDate() != null && dto.getEndPubDate() != null) {
            lambdaQueryWrapper.between(WmNews::getPublishTime, dto.getBeginPubDate(), dto.getEndPubDate());
        }

        //关键字模糊查询
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            lambdaQueryWrapper.like(WmNews::getTitle, dto.getKeyword());
        }

        //查询当前登录用户的文章
        lambdaQueryWrapper.eq(WmNews::getUserId, user.getId());

        //发布时间倒序查询
        lambdaQueryWrapper.orderByDesc(WmNews::getCreatedTime);

        page = page(page, lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());

        return responseResult;
    }


    @Autowired
    private WmNewsAutoScanService wmNewsAutoScanService;

    @Autowired
    private WmNewsTaskService wmNewsTaskService;

    /**
     * 发布文章或保存为草稿
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult submitNews(WmNewsDto dto) {

        //0.条件判断
        if (dto == null || dto.getContent() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //1.保存或修改文章

        WmNews wmNews = new WmNews();
        //属性拷贝 属性名词和类型相同才能拷贝
        BeanUtils.copyProperties(dto, wmNews);
        //封面图片  list---> string
        if (dto.getImages() != null && dto.getImages().size() > 0) {
            //[1dddfsd.jpg,sdlfjldk.jpg]-->   1dddfsd.jpg,sdlfjldk.jpg
            String imageStr = StringUtils.join(dto.getImages(), ",");
            wmNews.setImages(imageStr);
        }
        //如果当前封面类型为自动 -1
        if (dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)) {
            wmNews.setType(null);
        }

        saveOrUpdateWmNews(wmNews);

        //2.判断是否为草稿  如果为草稿结束当前方法
        if (dto.getStatus().equals(WmNews.Status.NORMAL.getCode())) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        //3.不是草稿，保存文章内容图片与素材的关系
        //获取到文章内容中的图片信息
        List<String> materials = ectractUrlInfo(dto.getContent());
        saveRelativeInfoForContent(materials, wmNews.getId());

        //4.不是草稿，保存文章封面图片与素材的关系，如果当前布局是自动，需要匹配封面图片
        saveRelativeInfoForCover(dto, wmNews, materials);


        //审核文章
//        wmNewsAutoScanService.autoScanWmNews(wmNews.getId());
        wmNewsTaskService.addNewsToTask(wmNews.getId(), wmNews.getPublishTime());

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    /**
     * 第一个功能：如果当前封面类型为自动，则设置封面类型的数据
     * 匹配规则：
     * 1，如果内容图片大于等于1，小于3  单图  type 1
     * 2，如果内容图片大于等于3  多图  type 3
     * 3，如果内容没有图片，无图  type 0
     * <p>
     * 第二个功能：保存封面图片与素材的关系
     *
     * @param dto
     * @param wmNews
     * @param materials
     */
    private void saveRelativeInfoForCover(WmNewsDto dto, WmNews wmNews, List<String> materials) {

        List<String> images = dto.getImages();

        //如果当前封面类型为自动，则设置封面类型的数据
        if (dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)) {
            //多图
            if (materials.size() >= 3) {
                wmNews.setType(WemediaConstants.WM_NEWS_MANY_IMAGE);
                images = materials.stream().limit(3).collect(Collectors.toList());
            } else if (materials.size() >= 1 && materials.size() < 3) {
                //单图
                wmNews.setType(WemediaConstants.WM_NEWS_SINGLE_IMAGE);
                images = materials.stream().limit(1).collect(Collectors.toList());
            } else {
                //无图
                wmNews.setType(WemediaConstants.WM_NEWS_NONE_IMAGE);
            }

            //修改文章
            if (images != null && images.size() > 0) {
                wmNews.setImages(StringUtils.join(images, ","));
            }
            updateById(wmNews);
        }
        if (images != null && images.size() > 0) {
            saveRelativeInfo(images, wmNews.getId(), WemediaConstants.WM_COVER_REFERENCE);
        }

    }


    /**
     * 处理文章内容图片与素材的关系
     *
     * @param materials
     * @param newsId
     */
    private void saveRelativeInfoForContent(List<String> materials, Integer newsId) {
        saveRelativeInfo(materials, newsId, WemediaConstants.WM_CONTENT_REFERENCE);
    }

    @Autowired
    private WmMaterialMapper wmMaterialMapper;

    /**
     * 保存文章图片与素材的关系到数据库中
     *
     * @param materials
     * @param newsId
     * @param type
     */
    private void saveRelativeInfo(List<String> materials, Integer newsId, Short type) {
        if (materials != null && !materials.isEmpty()) {
            //通过图片的url查询素材的id
            List<WmMaterial> dbMaterials = wmMaterialMapper.selectList(Wrappers.<WmMaterial>lambdaQuery().in(WmMaterial::getUrl, materials));

            //判断素材是否有效
            if (dbMaterials == null || dbMaterials.size() == 0) {
                //手动抛出异常   第一个功能：能够提示调用者素材失效了，第二个功能，进行数据的回滚
                throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
            }

            if (materials.size() != dbMaterials.size()) {
                throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
            }

            List<Integer> idList = dbMaterials.stream().map(WmMaterial::getId).collect(Collectors.toList());

            //批量保存
            wmNewsMaterialMapper.saveRelations(idList, newsId, type);
        }

    }


    /**
     * 提取文章内容中的图片信息
     *
     * @param content
     * @return
     */
    private List<String> ectractUrlInfo(String content) {
        List<String> materials = new ArrayList<>();

        List<Map> maps = JSON.parseArray(content, Map.class);
        for (Map map : maps) {
            if (map.get("type").equals("image")) {
                String imgUrl = (String) map.get("value");
                materials.add(imgUrl);
            }
        }

        return materials;
    }

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    /**
     * 保存或修改文章
     *
     * @param wmNews
     */
    private void saveOrUpdateWmNews(WmNews wmNews) {
        //补全属性
        wmNews.setUserId(WmThreadLocalUtils.getUser().getId());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        wmNews.setEnable((short) 1);//默认上架

        if (wmNews.getId() == null) {
            //保存
            save(wmNews);
        } else {
            //修改
            //删除文章图片与素材的关系
            wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId, wmNews.getId()));
            updateById(wmNews);
        }

    }

    /**
     * 文章的上下架
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult downOrUp(WmNewsDto dto) {
        //1.检查参数
        if (dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.查询文章
        WmNews wmNews = getById(dto.getId());
        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "文章不存在");
        }

        //3.判断文章是否已发布
        if (!wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "当前文章不是发布状态，不能上下架");
        }

        //4.修改文章enable
        if (dto.getEnable() != null && dto.getEnable() > -1 && dto.getEnable() < 2) {
            update(Wrappers.<WmNews>lambdaUpdate().set(WmNews::getEnable, dto.getEnable())
                    .eq(WmNews::getId, wmNews.getId()));
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Autowired
    WmUserMapper wmUserMapper;
    /**
     * 文章的人工审批
     *
     */
    @Override
    public ResponseResult findListVo(AdWemediaNewsDto adWemediaNewsDto) {
        //分页校验
        adWemediaNewsDto.checkParam();
        //查询创建
        IPage page=new Page(adWemediaNewsDto.getPage(),adWemediaNewsDto.getSize());
        LambdaQueryWrapper<WmNews> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        //条件查询
        lambdaQueryWrapper.like(StringUtils.isNotBlank(adWemediaNewsDto.getTitle()),WmNews::getTitle,adWemediaNewsDto.getTitle());
        lambdaQueryWrapper.eq(adWemediaNewsDto.getStatus()!=null,WmNews::getStatus,adWemediaNewsDto.getStatus());
        lambdaQueryWrapper.orderByDesc(WmNews::getCreatedTime);

        //进行查询
        page = page(page, lambdaQueryWrapper);

        List<AdWemediaNewsUserNameDto> adWemediaNewsUserNameDtos=new ArrayList<>();
        for (Object object:page.getRecords()){
            if (object instanceof WmNews){
                WmNews wmNews = (WmNews) object;
                AdWemediaNewsUserNameDto adWemediaNewsUserNameDto=new AdWemediaNewsUserNameDto();
                BeanUtils.copyProperties(wmNews,adWemediaNewsUserNameDto);
                adWemediaNewsUserNameDto.setAuthorName(wmUserMapper.selectById(adWemediaNewsUserNameDto.getUserId()).getName());
                adWemediaNewsUserNameDtos.add(adWemediaNewsUserNameDto);
            }
        }

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(adWemediaNewsDto.getPage(), adWemediaNewsDto.getSize(), (int) page.getTotal());
        responseResult.setData(adWemediaNewsUserNameDtos);


        return responseResult;
    }
    /**
     * 查询文章详情
     */
    @Override
    public ResponseResult findOneVo(Integer id) {
        //参数校验
        if (id.equals(null)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmNews wmNews=getById(id);
        if (wmNews==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        AdWemediaNewsUserNameDto adWemediaNewsUserNameDto=new AdWemediaNewsUserNameDto();
        BeanUtils.copyProperties(wmNews,adWemediaNewsUserNameDto);
        adWemediaNewsUserNameDto.setAuthorName(wmUserMapper.selectById(adWemediaNewsUserNameDto.getUserId()).getName());

        return ResponseResult.okResult(adWemediaNewsUserNameDto);
    }
    /**
     * 审核失败
     */
    @Override
    public ResponseResult authFail(AdWemediaNewsDto adWemediaNewsDto) {
        //参数校验
        if (StringUtils.isBlank(adWemediaNewsDto.getTitle()) && StringUtils.isBlank(adWemediaNewsDto.getMsg()) ){
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH,"驳回原因不可为空");
        }
        if (adWemediaNewsDto.getId()==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmNews wmNews=getById(adWemediaNewsDto.getId());
        if (wmNews==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        wmNews.setReason(StringUtils.isBlank(adWemediaNewsDto.getTitle())?adWemediaNewsDto.getMsg(): adWemediaNewsDto.getTitle());
        wmNews.setStatus((short) 2);

        updateById(wmNews);
        return ResponseResult.okResult(wmNews);
    }

    /**
     * 审核成功
     */
    @Override
    public ResponseResult authPass(AdWemediaNewsDto adWemediaNewsDto) {
        //参数校验
        if (adWemediaNewsDto.getId()==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmNews wmNews=getById(adWemediaNewsDto.getId());
        if (wmNews==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        wmNews.setReason("人工审核通过");
        wmNews.setStatus((short) 4);

        updateById(wmNews);
        return ResponseResult.okResult(wmNews);
    }
}