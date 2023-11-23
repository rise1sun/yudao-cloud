package cn.iocoder.yudao.module.wms.convert.formulaordertask;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.formulaordertask.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaordertask.FormulaOrderTaskDO;

/**
 * 工艺流程订单任务 Convert
 *
 * @author 管理员
 */
@Mapper
public interface FormulaOrderTaskConvert {

    FormulaOrderTaskConvert INSTANCE = Mappers.getMapper(FormulaOrderTaskConvert.class);

    FormulaOrderTaskDO convert(FormulaOrderTaskCreateReqVO bean);

    FormulaOrderTaskDO convert(FormulaOrderTaskUpdateReqVO bean);

    FormulaOrderTaskRespVO convert(FormulaOrderTaskDO bean);

    List<FormulaOrderTaskRespVO> convertList(List<FormulaOrderTaskDO> list);

    PageResult<FormulaOrderTaskRespVO> convertPage(PageResult<FormulaOrderTaskDO> page);

    List<FormulaOrderTaskExcelVO> convertList02(List<FormulaOrderTaskDO> list);

}
