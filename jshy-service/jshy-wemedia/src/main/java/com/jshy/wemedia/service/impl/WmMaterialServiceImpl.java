package com.jshy.wemedia.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.file.service.FileStorageService;
import com.jshy.model.common.dtos.PageResponseResult;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.wemedia.dtos.WmMaterialDto;
import com.jshy.model.wemedia.pojos.WmMaterial;
import com.jshy.model.wemedia.pojos.WmNewsMaterial;
import com.jshy.utils.thread.WmThreadLocalUtils;
import com.jshy.wemedia.mapper.WmMaterialMapper;
import com.jshy.wemedia.service.WmMaterialService;
import com.jshy.wemedia.service.WmNewsMaterialSercice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    @Autowired
    private FileStorageService fileStorageService;


    /**
     * 图片上传
     *
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {

        //1.检查参数
        if (multipartFile == null || multipartFile.getSize() == 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.上传图片到minIO中，并且将uuid里面的“-”转化为“”
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //aa.jpg，截取“.jpg”后缀
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            //将改名字后的图片进行上传
            fileId = fileStorageService.uploadImgFile("", fileName + postfix, multipartFile.getInputStream());
            log.info("上传图片到MinIO中，fileId:{}", fileId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WmMaterialServiceImpl-上传文件失败");
        }

        //3.保存到数据库中
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUserId(WmThreadLocalUtils.getUser().getId());
        wmMaterial.setUrl(fileId);
        wmMaterial.setIsCollection((short) 0);
        wmMaterial.setType((short) 0);
        wmMaterial.setCreatedTime(new Date());
        save(wmMaterial);

        //4.返回结果
        return ResponseResult.okResult(wmMaterial);
    }

    /**
     * 素材列表查询
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findList(WmMaterialDto dto) {

        //1.检查参数
        dto.checkParam();

        //2.分页查询
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //是否收藏
        if (dto.getIsCollection() != null && dto.getIsCollection() == 1) {
            lambdaQueryWrapper.eq(WmMaterial::getIsCollection, dto.getIsCollection());
        }

        //按照用户查询
        lambdaQueryWrapper.eq(WmMaterial::getUserId, WmThreadLocalUtils.getUser().getId());

        //按照时间倒序
        lambdaQueryWrapper.orderByDesc(WmMaterial::getCreatedTime);

        //上面是进行查询条件创建，这句这是真的开始查询
        page = page(page, lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    @Autowired
    WmNewsMaterialSercice wmNewsMaterialSercice;

    /**
     * 素材主键删除
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult del_picture(Integer id) {
        //1.校验参数
        if (can(id)) {
            //根据id查找对应的素材
            WmMaterial wmMaterial = getOne(Wrappers.<WmMaterial>lambdaQuery().eq(WmMaterial::getId, id));
            //判断是否找到
            if (wmMaterial == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "素材没有找到");
            } else {
                //找到了进行删除判断是否有文章正在引用
                ResponseResult<List<WmNewsMaterial>> allNewMaterial = wmNewsMaterialSercice.findAllNewMaterial(id);
                List<WmNewsMaterial> referencedMaterials = allNewMaterial.getData();
                if (referencedMaterials == null || referencedMaterials.isEmpty()) {
                    //没有引用则直接删除
                    removeById(wmMaterial.getId());
                    return ResponseResult.okResult(wmMaterial);
                } else {
                    //优化方案：这里可以进一步扩展，比如显示到底是哪些文章用到了这个素材
                    return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH, "目前有文章正在引用，无法删除");
                }
            }
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "无效参数");
        }
    }

    /**
     * 素材主键收藏
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult collect(Integer id) {
        //1.校验参数
        if (can(id)) {
            //先查询出对应的数据
            WmMaterial wmMaterial = getOne(Wrappers.<WmMaterial>lambdaQuery().eq(WmMaterial::getId, id));
            //将参数进行修改
            wmMaterial.setIsCollection((short) 1);
            // 更改数据，使用 updateById 方法
            boolean success = updateById(wmMaterial);
            if (success) {
                // 更新成功
                return ResponseResult.okResult(wmMaterial);
            } else {
                // 更新失败
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_FAILED, "更新数据失败");
            }
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "无效参数");
        }
    }

    /**
     * 校验参数
     */
    public boolean can(Integer integer) {
        if (integer != null || integer > 0) {
            return true;
        } else {
            return false;
        }
    }
    //优化方案：将上面和这个两个大妈相似度很高的进行拆分一个类，降低耦合
    /**
     * 素材主键取消收藏
     */
    public ResponseResult cancel_collect(Integer id) {
        //1.校验参数
        if (can(id)) {
            //查询出来对应的数据
            WmMaterial wmMaterial = getOne(Wrappers.<WmMaterial>lambdaQuery().eq(WmMaterial::getId, id));
            //修改对应的数据
            wmMaterial.setIsCollection((short) 0);
            //根据主键保存修改后的数据
            boolean success = updateById(wmMaterial);
            if (success) {
                // 更新成功
                return ResponseResult.okResult(wmMaterial);
            } else {
                // 更新失败
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_FAILED, "更新数据失败");
            }
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "参数无效");
        }
    }
}