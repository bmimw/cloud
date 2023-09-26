package com.jshy.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.commodity.mapper.CmGoodsMapper;
import com.jshy.commodity.service.CmGoodsService;
import com.jshy.model.commodity.dtos.CmLikeDto;
import com.jshy.model.commodity.dtos.CmLikeGoodDto;
import com.jshy.model.commodity.dtos.CmParentDto;
import com.jshy.model.commodity.dtos.CmRecommendDetailsDto;
import com.jshy.model.commodity.pojos.CmGoods;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.pojos.WmMaterial;
import com.jshy.model.wemedia.pojos.WmNews;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CmGoodsServiceImpl extends ServiceImpl<CmGoodsMapper, CmGoods> implements CmGoodsService {

    @Autowired
    CmGoodsService cmGoodsService;

    @Override
    public ResponseResult getGuessLike(Integer page, Integer pageSize) {
        CmLikeDto cmLikeDto = new CmLikeDto();
        if (page == null || page <= 0) {
            // 处理 param1,页码默认值1
            cmLikeDto.setPage(1);
        } else {
            cmLikeDto.setPage(page);
        }
        if (pageSize == null || pageSize <= 10) {
            // 处理 param2，页大小默认是10
            cmLikeDto.setPageSize(10);
        } else {
            cmLikeDto.setPageSize(pageSize);
        }
        // 进行商品分页配置
        IPage iPage = new Page(cmLikeDto.getPage(), cmLikeDto.getPageSize());
        // 使用 MyBatis-Plus 封装查询
        LambdaQueryWrapper<CmGoods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 按照倒叙id查询
        lambdaQueryWrapper.orderByDesc(CmGoods::getId);
        // 进行查询
        iPage = page(iPage, lambdaQueryWrapper);

        //对数据进行拷贝
        List<CmLikeGoodDto> cmLikeGoodDtos = new ArrayList<>();
        for (Object cmGoods : iPage.getRecords()) {
            //因为分页iPage.getRecords() 返回的是一个通用的 List，它可能包含不同的对象类型，包括不是 CmGoods 类型的对象。
            //为了解决这个问题，可以在遍历前先过滤出 CmGoods 类型的对象。通过使用 instanceof 运算符来实现
            if (cmGoods instanceof CmGoods) {
                CmGoods cmGood = (CmGoods) cmGoods;
                CmLikeGoodDto cmLikeGoodDto = new CmLikeGoodDto();
                BeanUtils.copyProperties(cmGood, cmLikeGoodDto);
                //商品描述
                cmLikeGoodDto.setDesc(cmGood.getNotice());
                //展示图片，取第一个照片
                cmLikeGoodDto.setPicture(cmGood.getMainPictures().split(",")[0]);
                cmLikeGoodDtos.add(cmLikeGoodDto);
            }
        }
        // 将查询结果其余值设置到cmLikeDto
        cmLikeDto.setItems(cmLikeGoodDtos); // 设置商品列表
        cmLikeDto.setCounts((int) iPage.getTotal()); // 设置总记录数
        cmLikeDto.setPages((int) Math.ceil((double) iPage.getTotal() / iPage.getSize()));//设置总页数

        return ResponseResult.okResult(cmLikeDto);

    }

    /**
     * 获取对应的推荐商品
     */
    @Override
    public CmRecommendDetailsDto getRecommendGoods(CmRecommendDetailsDto cmRecommendDetailsDto) {

        for (CmRecommendDetailsDto.SubType subType : cmRecommendDetailsDto.getSubTypes()) {
            CmRecommendDetailsDto.SubType.GoodsItems goodsItem = new CmRecommendDetailsDto.SubType.GoodsItems();
            // 处理页码默认值1
            goodsItem.setPage(1);
            // 处理页大小默认是10
            goodsItem.setPageSize(10);
            // 进行商品分页配置
            IPage iPage = new Page(goodsItem.getPage(), goodsItem.getPageSize());
            // 使用 MyBatis-Plus 封装查询
            LambdaQueryWrapper<CmGoods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 按照推荐类型id查询
            lambdaQueryWrapper.eq(CmGoods::getBelongId, subType.getId());
            // 进行查询
            iPage = page(iPage, lambdaQueryWrapper);
            //对数据进行拷贝
            List<CmRecommendDetailsDto.SubType.GoodsItems.Items> items = new ArrayList<>();
            for (Object cmGoods : iPage.getRecords()) {
                //因为分页iPage.getRecords() 返回的是一个通用的 List，它可能包含不同的对象类型，包括不是 CmGoods 类型的对象。
                //为了解决这个问题，可以在遍历前先过滤出 CmGoods 类型的对象。通过使用 instanceof 运算符来实现
                if (cmGoods instanceof CmGoods) {
                    CmGoods cmGood = (CmGoods) cmGoods;
                    CmRecommendDetailsDto.SubType.GoodsItems.Items item = new CmRecommendDetailsDto.SubType.GoodsItems.Items();
                    BeanUtils.copyProperties(cmGood, item);
                    //商品描述
                    item.setDesc(cmGood.getNotice());
                    //展示图片，取第一个照片
                    item.setPicture(cmGood.getMainPictures().split(",")[0]);
                    items.add(item);
                }
            }
            goodsItem.setItems(items);// 设置商品列表
            goodsItem.setCounts((int) iPage.getTotal()); // 设置总记录数
            goodsItem.setPages((int) Math.ceil((double) iPage.getTotal() / iPage.getSize()));//设置总页数

            subType.setGoodsItems(goodsItem);
        }

        return cmRecommendDetailsDto;
    }

    /**
     * 根据分类二级搜索商品
     */

    @Override
    public List<CmParentDto.Children.Goods> getParentGoods(Integer typeId) {
        //优化方案:可以再这里面添加个性化推荐逻辑，目前是只显示前九个
        Integer integer=1;
        List<CmParentDto.Children.Goods> goods = new ArrayList<>();
        List<CmGoods> cmGoods = list(Wrappers.<CmGoods>lambdaQuery().eq(CmGoods::getType, typeId));
        for (CmGoods cmGood : cmGoods) {
            if (integer>9){
                break;
            }
//            BeanUtils.copyProperties(cmGood,good);
            CmParentDto.Children.Goods good=new CmParentDto.Children.Goods();
            good.setName(cmGood.getName());
            good.setId(String.valueOf(cmGood.getId()));
            good.setPrice(String.valueOf(cmGood.getPrice()));
            good.setDesc(cmGood.getNotice());
            good.setPicture(cmGood.getMainPictures().split(",")[0]);

            goods.add(good);
            integer++;
        }

        return goods;
    }
}
