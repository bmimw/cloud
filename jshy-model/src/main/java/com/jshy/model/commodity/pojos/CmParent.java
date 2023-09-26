package com.jshy.model.commodity.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("cm_parent")
public class CmParent implements Serializable{

  private static final long serialVersionUID = 1L;
  /**
   * 主键
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  /**
   * 分类名称
   */
  @TableField("name")
  private String name;
  /**
   * 分类的图标
   */
  @TableField("icon")
  private String icon;
  /**
   *
   * 当前层级
   */
  @TableField("layer")
  private Integer layer;
  /**
   * 父类的id
   */
  @TableField("parent_id")
  private Integer parentId;

}
