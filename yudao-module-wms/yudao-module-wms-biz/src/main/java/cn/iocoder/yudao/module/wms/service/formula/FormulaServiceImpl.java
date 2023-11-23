package cn.iocoder.yudao.module.wms.service.formula;

import cn.iocoder.yudao.module.wms.convert.formulaitem.FormulaItemConvert;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;
import cn.iocoder.yudao.module.wms.dal.mysql.formulaitem.FormulaItemMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.wms.controller.admin.formula.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formula.FormulaDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.convert.formula.FormulaConvert;
import cn.iocoder.yudao.module.wms.dal.mysql.formula.FormulaMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.*;

/**
 * 工艺流程 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class FormulaServiceImpl implements FormulaService {

    @Resource
    private FormulaMapper formulaMapper;

    @Resource
    private FormulaItemMapper formulaItemMapper;

    @Getter
    private volatile List<Map<String,Integer>> formulasCache = Collections.EMPTY_LIST;

    @Override
    public Integer createFormula(FormulaCreateReqVO createReqVO) {
        // 校验
        // 插入工艺主表数据
        FormulaDO formula = FormulaConvert.INSTANCE.convert(createReqVO);
        formulaMapper.insert(formula);
        // 插入工艺主表数据
        List<FormulaBaseVO.FormulaItem> formulaItemList = createReqVO.getFormulaItemList();
        List<FormulaItemDO> formulaItemDOS = FormulaItemConvert.INSTANCE.convertList(formulaItemList,formula);
        formulaItemMapper.insertBatch(formulaItemDOS);



        // 返回
        return formula.getId();
    }

    @Override
    public void updateFormula(FormulaUpdateReqVO updateReqVO) {
        // 校验存在
        validateFormulaExists(updateReqVO.getId());
        //更新子表
        List<FormulaUpdateBaseVO.FormulaItem> formulaItemList = updateReqVO.getFormulaItemList();
        // 更新主表
        FormulaDO updateObj = FormulaConvert.INSTANCE.convert(updateReqVO);
        formulaMapper.updateById(updateObj);
        List<FormulaItemDO> formulaItemDOS = FormulaItemConvert.INSTANCE.convertList04(formulaItemList);
        formulaItemMapper.updateBatch(formulaItemDOS,formulaItemDOS.size());
    }

    @Override
    public void deleteFormula(Integer id) {
        // 校验存在
        validateFormulaExists(id);

        List<FormulaItemDO> formulaItemDOS = formulaItemMapper.selectList(FormulaItemDO::getFormulaId, id);
        formulaItemMapper.deleteBatchIds(formulaItemDOS);
        // 删除
        formulaMapper.deleteById(id);
    }

    private void validateFormulaExists(Integer id) {
        if (formulaMapper.selectById(id) == null) {
            throw exception(FORMULA_NOT_EXISTS);
        }
    }

    @Override
    public FormulaDO getFormula(Integer id) {
        return formulaMapper.selectById(id);
    }

    @Override
    public List<FormulaDO> getFormulaList(Collection<Integer> ids) {
        return formulaMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FormulaDO> getFormulaPage(FormulaPageReqVO pageReqVO) {
        return formulaMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FormulaDO> getFormulaList(FormulaExportReqVO exportReqVO) {
        return formulaMapper.selectList(exportReqVO);
    }

    @Override
    public List<FormulaItemDO> getFormulaForUpdate(Integer id) {
        List<FormulaItemDO> formulaItemDOS = formulaItemMapper.selectList(FormulaItemDO::getFormulaId, id);
        return formulaItemDOS;
    }

    @Override
    public List<FormulaDO> getFormulas() {
        List<FormulaDO> formulaDOS = formulaMapper.selectList();
        return formulaDOS;
    }

    @Override
   // @PostConstruct
    public void initLocalCache() {
        // 第一步：查询数据
        List<FormulaDO> formulaDOS = formulaMapper.selectList();
        log.info("[initLocalCache][缓存工艺流程，数量为:{}]", formulaDOS.size());

        // 第二步：构建缓存
        formulaDOS.forEach(formulaDO -> {
            HashMap<String, Integer> map = new HashMap<>();
            map.put(formulaDO.getName(),formulaDO.getId());
            formulasCache.add(map);
        });

    }


}
