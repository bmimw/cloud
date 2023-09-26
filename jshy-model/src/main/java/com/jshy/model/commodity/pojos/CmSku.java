package com.jshy.model.commodity.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("cm_sku")
public class CmSku implements Serializable {
  /**
   * 主键
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  /**
   * sku的id
   */
  @TableField("sku_code")
  private String skuCode;
  /**
   * 当前价格
   */
  @TableField("price")
  private Integer price;
  /**
   * 旧价格
   */
  @TableField("old_price")
  private Integer oldPrice;
  /**
   * 当前规格库存
   */
  @TableField("inventory")
  private String inventory;
  /**
   * 所有图片存储位置地址
   */
  @TableField("pictures")
  private String pictures;


}
