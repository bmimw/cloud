package com.jshy.model.commodity.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("cm_recommend")
public class CmRecommend {

  private static final long serialVersionUID = 1L;
  /**
   *  主键
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  /**
   *  推荐说明
   */
  @TableField("alt")
  private String alt;
  /**
   *  图片合集
   */
  @TableField("pictures")
  private String pictures;
  /**
   *  跳转地址
   */
  @TableField("target")
  private String target;
  /**
   *  推荐标题
   */
  @TableField("title")
  private String title;

  /**
   * 活动图片
   * */
  @TableField("banner_picture")
  private String bannerPicture;

  /**
   *  推荐类型
   */
  @TableField("type")
  private String type;
}
