package com.jshy.model.commodity.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cm_specs")
public class CmSpecs {
  /**
   * 主键
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  /**
   * spu的id
   */
  @TableField("spu_code")
  private String spuCode;
  /**
   * sku的id
   * */
  @TableField("sku_code")
  private String skuCode;
  /**
   * 规格名称
   * */
  @TableField("name")
  private String name;
  /**
   * 规格可选值
   * */
  @TableField("value_name")
  private String valueName;
}
