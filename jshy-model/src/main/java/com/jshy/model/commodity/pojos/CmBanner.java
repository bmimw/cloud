package com.jshy.model.commodity.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("cm_banner")
public class CmBanner  implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *  主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *  广告图片地址
     * */
    @TableField("img_url")
    private String imgUrl;
    /**
     *  点击地址
     * */
    @TableField("href_url")
    private String hrefUrl;
    /**
     *  类型
     * */
    @TableField("type")
    private Integer type;

}
