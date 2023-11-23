package cn.iocoder.yudao.module.wms.controller.admin.task;

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

import cn.iocoder.yudao.module.wms.controller.admin.task.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.task.TaskDO;
import cn.iocoder.yudao.module.wms.convert.task.TaskConvert;
import cn.iocoder.yudao.module.wms.service.task.TaskService;

@Tag(name= "管理后台 - 任务管理")
@RestController
@RequestMapping("/wms/task")
@Validated
public class TaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("/create")
    @Operation(description ="创建任务管理")
    @PreAuthorize("@ss.hasPermission('wms:task:create')")
    public CommonResult<Integer> createTask(@Valid @RequestBody TaskCreateReqVO createReqVO) {
        return success(taskService.createTask(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新任务管理")
    @PreAuthorize("@ss.hasPermission('wms:task:update')")
    public CommonResult<Boolean> updateTask(@Valid @RequestBody TaskUpdateReqVO updateReqVO) {
        taskService.updateTask(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除任务管理")
    @PreAuthorize("@ss.hasPermission('wms:task:delete')")
    public CommonResult<Boolean> deleteTask(@RequestParam("id") Integer id) {
        taskService.deleteTask(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得任务管理")
    @PreAuthorize("@ss.hasPermission('wms:task:query')")
    public CommonResult<TaskRespVO> getTask(@RequestParam("id") Integer id) {
        TaskDO task = taskService.getTask(id);
        return success(TaskConvert.INSTANCE.convert(task));
    }

    @GetMapping("/list")
    @Operation(description ="获得任务管理列表")
    @PreAuthorize("@ss.hasPermission('wms:task:query')")
    public CommonResult<List<TaskRespVO>> getTaskList(@RequestParam("ids") Collection<Integer> ids) {
        List<TaskDO> list = taskService.getTaskList(ids);
        return success(TaskConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description ="获得任务管理分页")
    @PreAuthorize("@ss.hasPermission('wms:task:query')")
    public CommonResult<PageResult<TaskRespVO>> getTaskPage(@Valid TaskPageReqVO pageVO) {
        PageResult<TaskDO> pageResult = taskService.getTaskPage(pageVO);
        return success(TaskConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出任务管理 Excel")
    @PreAuthorize("@ss.hasPermission('wms:task:export')")
    @OperateLog(type = EXPORT)
    public void exportTaskExcel(@Valid TaskExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TaskDO> list = taskService.getTaskList(exportReqVO);
        // 导出 Excel
        List<TaskExcelVO> datas = TaskConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "任务管理.xls", "数据", TaskExcelVO.class, datas);
    }

    @GetMapping("/executeTask")
    @Operation(description ="执行任务")
    @PreAuthorize("@ss.hasPermission('wms:task:update')")
    public CommonResult<TaskRespVO> executeTask(@RequestParam("id") Integer id) {
        TaskDO task = taskService.executeTask(id);
        return success(TaskConvert.INSTANCE.convert(task));
    }

}
