package com.jshy.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.dtos.WmMaterialDto;
import com.jshy.model.wemedia.pojos.WmMaterial;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 图片上传
     *
     * @param multipartFile
     * @return
     */
    public ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 素材列表查询
     *
     * @param dto
     * @return
     */
    public ResponseResult findList(WmMaterialDto dto);

    /**
     * 素材主键删除
     *
     * @param id
     * @return
     */
    public ResponseResult del_picture(Integer id);

    /**
     * 素材主键收藏
     *
     * @param id
     * @return
     */
    public ResponseResult collect(Integer id);

    /**
     * 素材主键取消收藏
     *
     * @param id
     * @return
     */
    public ResponseResult cancel_collect(Integer id);

}