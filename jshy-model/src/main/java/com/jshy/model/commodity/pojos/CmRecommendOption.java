package com.jshy.model.commodity.pojos;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("cm_recommend_option")
public class CmRecommendOption implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   *  主键
   */
  @TableId(value = "id", type = IdType.AUTO)
  private long id;
  /**
   * 活动类型id
   * */
  @TableField("type_id")
  private String typeId;
  /**
   * 活动具体选项集合
   * */
  @TableField("title")
  private String title;

}
