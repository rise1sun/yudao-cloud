package cn.iocoder.yudao.module.wms.controller.admin.restingtask;

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

import cn.iocoder.yudao.module.wms.controller.admin.restingtask.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.restingtask.RestingTaskDO;
import cn.iocoder.yudao.module.wms.convert.restingtask.RestingTaskConvert;
import cn.iocoder.yudao.module.wms.service.restingtask.RestingTaskService;

@Tag(name= "管理后台 - 静置任务信息")
@RestController
@RequestMapping("/wms/resting-task")
@Validated
public class RestingTaskController {

    @Resource
    private RestingTaskService restingTaskService;

    @PostMapping("/create")
    @Operation(description ="创建静置任务信息")
    @PreAuthorize("@ss.hasPermission('wms:resting-task:create')")
    public CommonResult<Integer> createRestingTask(@Valid @RequestBody RestingTaskCreateReqVO createReqVO) {
        return success(restingTaskService.createRestingTask(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新静置任务信息")
    @PreAuthorize("@ss.hasPermission('wms:resting-task:update')")
    public CommonResult<Boolean> updateRestingTask(@Valid @RequestBody RestingTaskUpdateReqVO updateReqVO) {
        restingTaskService.updateRestingTask(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除静置任务信息")
    @PreAuthorize("@ss.hasPermission('wms:resting-task:delete')")
    public CommonResult<Boolean> deleteRestingTask(@RequestParam("id") Integer id) {
        restingTaskService.deleteRestingTask(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得静置任务信息")
    @PreAuthorize("@ss.hasPermission('wms:resting-task:query')")
    public CommonResult<RestingTaskRespVO> getRestingTask(@RequestParam("id") Integer id) {
        RestingTaskDO restingTask = restingTaskService.getRestingTask(id);
        return success(RestingTaskConvert.INSTANCE.convert(restingTask));
    }

    @GetMapping("/list")
    @Operation(description ="获得静置任务信息列表")
    @PreAuthorize("@ss.hasPermission('wms:resting-task:query')")
    public CommonResult<List<RestingTaskRespVO>> getRestingTaskList(@RequestParam("ids") Collection<Integer> ids) {
        List<RestingTaskDO> list = restingTaskService.getRestingTaskList(ids);
        return success(RestingTaskConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description ="获得静置任务信息分页")
    @PreAuthorize("@ss.hasPermission('wms:resting-task:query')")
    public CommonResult<PageResult<RestingTaskRespVO>> getRestingTaskPage(@Valid RestingTaskPageReqVO pageVO) {
        PageResult<RestingTaskDO> pageResult = restingTaskService.getRestingTaskPage(pageVO);
        return success(RestingTaskConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出静置任务信息 Excel")
    @PreAuthorize("@ss.hasPermission('wms:resting-task:export')")
    @OperateLog(type = EXPORT)
    public void exportRestingTaskExcel(@Valid RestingTaskExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<RestingTaskDO> list = restingTaskService.getRestingTaskList(exportReqVO);
        // 导出 Excel
        List<RestingTaskExcelVO> datas = RestingTaskConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "静置任务信息.xls", "数据", RestingTaskExcelVO.class, datas);
    }

}
