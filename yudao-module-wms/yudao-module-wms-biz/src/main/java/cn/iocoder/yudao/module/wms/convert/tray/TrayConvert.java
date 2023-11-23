package cn.iocoder.yudao.module.wms.convert.tray;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.wms.controller.admin.tray.vo.*;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.WarehousingVO;
import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * 托盘 Convert
 *
 * @author 管理员
 */
@Mapper
public interface TrayConvert {

    TrayConvert INSTANCE = Mappers.getMapper(TrayConvert.class);

    TrayDO convert(TrayCreateReqVO bean);

    TrayDO convert(TrayUpdateReqVO bean);

    TrayRespVO convert(TrayDO bean);

    List<TrayRespVO> convertList(List<TrayDO> list);

    @Mapping(target = "TrayRespVO.createTime", expression = "java(toDate(TrayDO.createTime))")
    PageResult<TrayRespVO> convertPage(PageResult<TrayDO> page);

    default Date toDate(LocalDateTime dateTime) {
        //LocalDateTime adjustedDateTime = dateTime.minusHours(8); // 减去 8 小时
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    List<TrayExcelVO> convertList02(List<TrayDO> list);

    TrayDO convert(TrayImportExcelVO bean);

    TrayDO convert(WarehousingVO warehousingDTO);
}
