package cn.iocoder.yudao.module.wms.controller.admin.formula;

import cn.iocoder.yudao.module.wms.controller.admin.batch.vo.BatchRespVO;
import cn.iocoder.yudao.module.wms.convert.batch.BatchConvert;
import cn.iocoder.yudao.module.wms.convert.formulaitem.FormulaItemConvert;
import cn.iocoder.yudao.module.wms.dal.dataobject.batch.BatchDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;


import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.wms.controller.admin.formula.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formula.FormulaDO;
import cn.iocoder.yudao.module.wms.convert.formula.FormulaConvert;
import cn.iocoder.yudao.module.wms.service.formula.FormulaService;

@Tag(name = "管理后台 - 工艺流程")
@RestController
@RequestMapping("/wms/formula")
@Validated
public class FormulaController {

    @Resource
    private FormulaService formulaService;

    @PostMapping("/create")
    @Operation(description ="创建工艺流程")
    @PreAuthorize("@ss.hasPermission('wms:formula:create')")
    public CommonResult<Integer> createFormula(@Valid @RequestBody FormulaCreateReqVO createReqVO) {
        return success(formulaService.createFormula(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新工艺流程")
    @PreAuthorize("@ss.hasPermission('wms:formula:update')")
    public CommonResult<Boolean> updateFormula(@Valid @RequestBody FormulaUpdateReqVO updateReqVO) {
        formulaService.updateFormula(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除工艺流程")
   @PreAuthorize("@ss.hasPermission('wms:formula:delete')")
    public CommonResult<Boolean> deleteFormula(@RequestParam("id") Integer id) {
        formulaService.deleteFormula(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得工艺流程")
    @PreAuthorize("@ss.hasPermission('wms:formula:query')")
    public CommonResult<FormulaRespVO> getFormula(@RequestParam("id") Integer id) {
        List<FormulaItemDO> formulaForUpdate = formulaService.getFormulaForUpdate(id);
        FormulaDO formula = formulaService.getFormula(id);
        List<FormulaUpdateBaseVO.FormulaItem> formulaItems = FormulaItemConvert.INSTANCE.convertList03(formulaForUpdate);
        FormulaRespVO convert = FormulaConvert.INSTANCE.convert(formula);
        convert.setFormulaItemList(formulaItems);
        return  success(convert);
    }

    @GetMapping("/list")
    @Operation(description ="获得工艺流程列表")
     @PreAuthorize("@ss.hasPermission('wms:formula:query')")
    public CommonResult<List<FormulaRespVO>> getFormulaList(@RequestParam("ids") Collection<Integer> ids) {
        List<FormulaDO> list = formulaService.getFormulaList(ids);
        return success(FormulaConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description ="获得工艺流程分页")
    @PreAuthorize("@ss.hasPermission('wms:formula:query')")
    public CommonResult<PageResult<FormulaRespVO>> getFormulaPage(@Valid FormulaPageReqVO pageVO) {
        PageResult<FormulaDO> pageResult = formulaService.getFormulaPage(pageVO);
        return success(FormulaConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出工艺流程 Excel")
    @PreAuthorize("@ss.hasPermission('wms:formula:export')")
    @OperateLog(type = EXPORT)
    public void exportFormulaExcel(@Valid FormulaExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FormulaDO> list = formulaService.getFormulaList(exportReqVO);
        // 导出 Excel
        List<FormulaExcelVO> datas = FormulaConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "工艺流程.xls", "数据", FormulaExcelVO.class, datas);
    }

    @GetMapping("/getFormulas")
    @Operation(description ="获取工艺流程列表")
    @PreAuthorize("@ss.hasPermission('wms:batch:query')")
    public CommonResult<List<FormulaDO>> getFormulas() {
        List<FormulaDO> ListResult = formulaService.getFormulas();
        return success(ListResult);
    }
}
