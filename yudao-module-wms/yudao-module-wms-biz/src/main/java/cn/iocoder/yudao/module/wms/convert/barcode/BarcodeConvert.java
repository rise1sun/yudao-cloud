package cn.iocoder.yudao.module.wms.convert.barcode;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.barcode.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.barcode.BarcodeDO;

/**
 * 条码 Convert
 *
 * @author 管理员
 */
@Mapper
public interface BarcodeConvert {

    BarcodeConvert INSTANCE = Mappers.getMapper(BarcodeConvert.class);

    BarcodeDO convert(BarcodeCreateReqVO bean);

    BarcodeDO convert(BarcodeUpdateReqVO bean);

    BarcodeRespVO convert(BarcodeDO bean);

    List<BarcodeRespVO> convertList(List<BarcodeDO> list);

    @Mapping(target = "BarcodeRespVO.createTime", expression = "java(toDate(BarcodeDO.createTime))")
    PageResult<BarcodeRespVO> convertPage(PageResult<BarcodeDO> page);

    List<BarcodeExcelVO> convertList02(List<BarcodeDO> list);

    default Date toDate(LocalDateTime dateTime) {
        //LocalDateTime adjustedDateTime = dateTime.minusHours(8); // 减去 8 小时
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
