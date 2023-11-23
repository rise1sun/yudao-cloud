package cn.iocoder.yudao.module.wms.convert.formulaitem;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.module.wms.controller.admin.formula.vo.FormulaBaseVO;
import cn.iocoder.yudao.module.wms.controller.admin.formula.vo.FormulaUpdateBaseVO;
import cn.iocoder.yudao.module.wms.controller.admin.formula.vo.FormulaUpdateReqVO;
import cn.iocoder.yudao.module.wms.dal.dataobject.formula.FormulaDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.formulaitem.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;

/**
 * 工艺流程节点 Convert
 *
 * @author 管理员
 */
@Mapper
public interface FormulaItemConvert {

    FormulaItemConvert INSTANCE = Mappers.getMapper(FormulaItemConvert.class);

    FormulaItemDO convert(FormulaItemCreateReqVO bean);

    FormulaItemDO convert(FormulaItemUpdateReqVO bean);

    FormulaItemRespVO convert(FormulaItemDO bean);

    FormulaItemDO convert(FormulaBaseVO.FormulaItem bean);

    List<FormulaItemRespVO> convertList(List<FormulaItemDO> list);

    PageResult<FormulaItemRespVO> convertPage(PageResult<FormulaItemDO> page);

    List<FormulaItemExcelVO> convertList02(List<FormulaItemDO> list);

    default List<FormulaItemDO> convertList(List<FormulaBaseVO.FormulaItem> formulaItems, FormulaDO formula) {
        return CollectionUtils.convertList(formulaItems,formulaItem -> convert(formulaItem).setFormulaId(String.valueOf(formula.getId())));
    }

    List<FormulaUpdateBaseVO.FormulaItem> convertList03(List<FormulaItemDO> formulaForUpdate);

    List<FormulaItemDO> convertList04(List<FormulaUpdateBaseVO.FormulaItem> formulaItemList);
}
