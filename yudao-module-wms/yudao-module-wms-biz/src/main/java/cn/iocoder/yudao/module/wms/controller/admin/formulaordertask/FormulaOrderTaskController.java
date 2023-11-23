package cn.iocoder.yudao.module.wms.controller.admin.formulaordertask;

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

import cn.iocoder.yudao.module.wms.controller.admin.formulaordertask.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaordertask.FormulaOrderTaskDO;
import cn.iocoder.yudao.module.wms.convert.formulaordertask.FormulaOrderTaskConvert;
import cn.iocoder.yudao.module.wms.service.formulaordertask.FormulaOrderTaskService;

@Tag(name= "管理后台 - 工艺流程订单任务")
@RestController
@RequestMapping("/wms/formula-order-task")
@Validated
public class FormulaOrderTaskController {

    @Resource
    private FormulaOrderTaskService formulaOrderTaskService;

    @PostMapping("/create")
    @Operation(description ="创建工艺流程订单任务")
    @PreAuthorize("@ss.hasPermission('wms:formula-order-task:create')")
    public CommonResult<Integer> createFormulaOrderTask(@Valid @RequestBody FormulaOrderTaskCreateReqVO createReqVO) {
        return success(formulaOrderTaskService.createFormulaOrderTask(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新工艺流程订单任务")
    @PreAuthorize("@ss.hasPermission('wms:formula-order-task:update')")
    public CommonResult<Boolean> updateFormulaOrderTask(@Valid @RequestBody FormulaOrderTaskUpdateReqVO updateReqVO) {
        formulaOrderTaskService.updateFormulaOrderTask(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除工艺流程订单任务")
    @PreAuthorize("@ss.hasPermission('wms:formula-order-task:delete')")
    public CommonResult<Boolean> deleteFormulaOrderTask(@RequestParam("id") Integer id) {
        formulaOrderTaskService.deleteFormulaOrderTask(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得工艺流程订单任务")
    @PreAuthorize("@ss.hasPermission('wms:formula-order-task:query')")
    public CommonResult<FormulaOrderTaskRespVO> getFormulaOrderTask(@RequestParam("id") Integer id) {
        FormulaOrderTaskDO formulaOrderTask = formulaOrderTaskService.getFormulaOrderTask(id);
        return success(FormulaOrderTaskConvert.INSTANCE.convert(formulaOrderTask));
    }

    @GetMapping("/list")
    @Operation(description ="获得工艺流程订单任务列表")
    @PreAuthorize("@ss.hasPermission('wms:formula-order-task:query')")
    public CommonResult<List<FormulaOrderTaskRespVO>> getFormulaOrderTaskList(@RequestParam("ids") Collection<Integer> ids) {
        List<FormulaOrderTaskDO> list = formulaOrderTaskService.getFormulaOrderTaskList(ids);
        return success(FormulaOrderTaskConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description ="获得工艺流程订单任务分页")
    @PreAuthorize("@ss.hasPermission('wms:formula-order-task:query')")
    public CommonResult<PageResult<FormulaOrderTaskRespVO>> getFormulaOrderTaskPage(@Valid FormulaOrderTaskPageReqVO pageVO) {
        PageResult<FormulaOrderTaskDO> pageResult = formulaOrderTaskService.getFormulaOrderTaskPage(pageVO);
        return success(FormulaOrderTaskConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出工艺流程订单任务 Excel")
    @PreAuthorize("@ss.hasPermission('wms:formula-order-task:export')")
    @OperateLog(type = EXPORT)
    public void exportFormulaOrderTaskExcel(@Valid FormulaOrderTaskExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FormulaOrderTaskDO> list = formulaOrderTaskService.getFormulaOrderTaskList(exportReqVO);
        // 导出 Excel
        List<FormulaOrderTaskExcelVO> datas = FormulaOrderTaskConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "工艺流程订单任务.xls", "数据", FormulaOrderTaskExcelVO.class, datas);
    }

}
