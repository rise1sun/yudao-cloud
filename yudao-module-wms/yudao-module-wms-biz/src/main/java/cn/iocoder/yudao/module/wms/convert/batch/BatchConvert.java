package cn.iocoder.yudao.module.wms.convert.batch;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.dal.dataobject.formula.FormulaDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.batch.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.batch.BatchDO;

/**
 * 批次 Convert
 *
 * @author 管理员
 */
@Mapper
public interface BatchConvert {

    BatchConvert INSTANCE = Mappers.getMapper(BatchConvert.class);

    BatchDO convert(BatchCreateReqVO bean);

    BatchDO convert(BatchUpdateReqVO bean);

    BatchRespVO convert(BatchDO bean);

    List<BatchRespVO> convertList(List<BatchDO> list);

    @Mapping(target = "FormulaRespVO.createTime", expression = "java(toDate(FormulaDO.createTime))")
    PageResult<BatchRespVO> convertPage(PageResult<BatchDO> page);

    List<BatchExcelVO> convertList02(List<BatchDO> list);

    default Date toDate(LocalDateTime dateTime) {
        //LocalDateTime adjustedDateTime = dateTime.minusHours(8); // 减去 8 小时
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    default List<BatchExcelVO> convertList03(List<BatchDO> list, List<FormulaDO> formulaDOS){
        List<BatchExcelVO> result = new ArrayList<>();
        list.forEach(batchDO -> {
            Integer formulaId = batchDO.getFormulaId().intValue();
            for (FormulaDO formulaDO : formulaDOS) {
                if(formulaId.equals(formulaDO.getId())){
                    BatchExcelVO batchExcelVO = new BatchExcelVO();
                    batchExcelVO.setFormulaName(formulaDO.getName());
                    batchExcelVO.setName(batchDO.getName());
                    batchExcelVO.setNumber(batchDO.getNumber());
                    batchExcelVO.setSpec(batchDO.getSpec());
                    batchExcelVO.setCreateTime(toDate(batchDO.getCreateTime()));
                    batchExcelVO.setStatus(batchDO.getStatus());
                    batchExcelVO.setId(batchDO.getId());
                    batchExcelVO.setRemark(batchDO.getRemark());
                    batchExcelVO.setFormulaId(batchDO.getFormulaId());
                    result.add(batchExcelVO);
                    break;
                }
            }
        });
        return result;
    }

}
