package com.jshy.model.commodity.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("cm_goods")
public class CmGoods implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   *  主键
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  /**
   * 所属推荐id
   * */
  @TableField("belong_id")
  private String belongId;
  /**
   *  商品名称
   * */
  @TableField("name")
  private String name;
  /**
   *  对应的spu_id
   * */
  @TableField("spu_code")
  private String spuCode;
  /**
   *  商品描述
   * */
  @TableField("notice")
  private String notice;
  /**
   *  商品价格
   * */
  @TableField("price")
  private Integer price;
  /**
   *  商品原价
   * */
  @TableField("old_price")
  private Integer oldPrice;
  /**
   *  商品类型id
   * */
  @TableField("type")
  private Integer type;
  /**
   *  商品总库存数
   * */
  @TableField("inventory")
  private String inventory;
  /**
   *  商品总销量
   * */
  @TableField("sales_count")
  private String salesCount;
  /**
   *  商品评价总数
   * */
  @TableField("comment_count")
  private String commentCount;
  /**
   *  商品收藏，点赞总数
   * */
  @TableField("collect_count")
  private String collectCount;
  /**
   *  商品主图视频合集
   * */
  @TableField("main_videos")
  private String mainVideos;
  /**
   *  主图图片集合
   * */
  @TableField("main_pictures")
  private String mainPictures;
  /**
   * 商品折扣
   * */
  @TableField("discount")
  private Integer discount;
  /**
   * 商品已下单数
   * */
  @TableField("orderNum")
  private Integer orderNum;
}
