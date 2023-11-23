package cn.iocoder.yudao.module.wms.controller.admin.batch;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.wms.controller.admin.batch.vo.*;
import cn.iocoder.yudao.module.wms.convert.batch.BatchConvert;
import cn.iocoder.yudao.module.wms.dal.dataobject.batch.BatchDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.formula.FormulaDO;
import cn.iocoder.yudao.module.wms.service.batch.BatchService;
import cn.iocoder.yudao.module.wms.service.formula.FormulaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name= "管理后台 - 批次")
@RestController
@RequestMapping("/wms/batch")
@Validated
public class BatchController {

    @Resource
    private BatchService batchService;

    @Resource
    private FormulaService formulaService;
    @PostMapping("/create")
    @Operation(description = "创建批次")
    @PreAuthorize("@ss.hasPermission('wms:batch:create')")
    public CommonResult<Long> createBatch(@Valid @RequestBody BatchCreateReqVO createReqVO) {
        return success(batchService.createBatch(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新批次")
    @PreAuthorize("@ss.hasPermission('wms:batch:update')")
    public CommonResult<Boolean> updateBatch(@Valid @RequestBody BatchUpdateReqVO updateReqVO) {
        batchService.updateBatch(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除批次")
    @PreAuthorize("@ss.hasPermission('wms:batch:delete')")
    public CommonResult<Boolean> deleteBatch(@RequestParam("id") Long id) {
        batchService.deleteBatch(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得批次")
    @PreAuthorize("@ss.hasPermission('wms:batch:query')")
    public CommonResult<BatchRespVO> getBatch(@RequestParam("id") Long id) {
        BatchDO batch = batchService.getBatch(id);
        return success(BatchConvert.INSTANCE.convert(batch));
    }

    @GetMapping("/list")
    @Operation(description ="获得批次列表")
    @PreAuthorize("@ss.hasPermission('wms:batch:query')")
    public CommonResult<List<BatchRespVO>> getBatchList(@RequestParam("ids") Collection<Long> ids) {
        List<BatchDO> list = batchService.getBatchList(ids);
        return success(BatchConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description ="获得批次分页")
    @PreAuthorize("@ss.hasPermission('wms:batch:query')")
    public CommonResult<PageResult<BatchRespVO>> getBatchPage(@Valid BatchPageReqVO pageVO) {
        PageResult<BatchDO> pageResult = batchService.getBatchPage(pageVO);
        return success(BatchConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出批次 Excel")
    @PreAuthorize("@ss.hasPermission('wms:batch:export')")
    @OperateLog(type = EXPORT)
    public void exportBatchExcel(@Valid BatchExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<BatchDO> list = batchService.getBatchList(exportReqVO);
        List<FormulaDO> formulas = formulaService.getFormulas();
        // 导出 Excel
        List<BatchExcelVO> datas = BatchConvert.INSTANCE.convertList03(list,formulas);
        ExcelUtils.write(response, "批次.xls", "数据", BatchExcelVO.class, datas);

    }
}
