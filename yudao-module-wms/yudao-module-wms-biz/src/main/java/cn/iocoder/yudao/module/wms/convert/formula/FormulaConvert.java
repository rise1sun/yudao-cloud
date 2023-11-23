package cn.iocoder.yudao.module.wms.convert.formula;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.formula.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formula.FormulaDO;

/**
 * 工艺流程 Convert
 *
 * @author 管理员
 */
@Mapper
public interface FormulaConvert {

    FormulaConvert INSTANCE = Mappers.getMapper(FormulaConvert.class);

    FormulaDO convert(FormulaCreateReqVO bean);

    FormulaDO convert(FormulaUpdateReqVO bean);

    FormulaRespVO convert(FormulaDO bean);

    List<FormulaRespVO> convertList(List<FormulaDO> list);

    @Mapping(target = "FormulaRespVO.createTime", expression = "java(toDate(FormulaDO.createTime))")
    PageResult<FormulaRespVO> convertPage(PageResult<FormulaDO> page);

    default Date toDate(LocalDateTime dateTime) {
        //LocalDateTime adjustedDateTime = dateTime.minusHours(8); // 减去 8 小时
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    List<FormulaExcelVO> convertList02(List<FormulaDO> list);

}
