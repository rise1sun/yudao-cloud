package cn.iocoder.yudao.module.wms.convert.storage;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.storage.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.storage.StorageDO;

/**
 * 库位 Convert
 *
 * @author 管理员
 */
@Mapper()
public interface StorageConvert {

    StorageConvert INSTANCE = Mappers.getMapper(StorageConvert.class);

    StorageDO convert(StorageCreateReqVO bean);

    StorageDO convert(StorageUpdateReqVO bean);

    StorageRespVO convert(StorageDO bean);

    List<StorageRespVO> convertList(List<StorageDO> list);
    @Mapping(target = "StorageRespVO.createTime", expression = "java(toDate(StorageDO.createTime))")
    PageResult<StorageRespVO> convertPage(PageResult<StorageDO> page);

    default Date toDate(LocalDateTime dateTime) {
        //LocalDateTime adjustedDateTime = dateTime.minusHours(8); // 减去 8 小时
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    List<StorageExcelVO> convertList02(List<StorageDO> list);

}
