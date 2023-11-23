package cn.iocoder.yudao.module.wms.controller.admin.fireProcess;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;

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

import cn.iocoder.yudao.module.wms.controller.admin.fireProcess.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.fireprocess.FireProcessDO;
import cn.iocoder.yudao.module.wms.convert.fireprocess.FireProcessConvert;
import cn.iocoder.yudao.module.wms.service.fireprocess.FireProcessService;

@Tag(name = "管理后台 - 消防任务")
@RestController
@RequestMapping("/wms/fire-process")
@Validated
public class FireProcessController {

    @Resource
    private FireProcessService fireProcessService;

    @PostMapping("/create")
    @Operation(description ="创建消防任务")
    @PreAuthorize("@ss.hasPermission('wms:fire-process:create')")
    public CommonResult<Long> createFireProcess(@Valid @RequestBody FireProcessCreateReqVO createReqVO) {
        return success(fireProcessService.createFireProcess(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新消防任务")
    @PreAuthorize("@ss.hasPermission('wms:fire-process:update')")
    public CommonResult<Boolean> updateFireProcess(@Valid @RequestBody FireProcessUpdateReqVO updateReqVO) {
        fireProcessService.updateFireProcess(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除消防任务")
    @PreAuthorize("@ss.hasPermission('wms:fire-process:delete')")
    public CommonResult<Boolean> deleteFireProcess(@RequestParam("id") Long id) {
        fireProcessService.deleteFireProcess(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得消防任务")
    @PreAuthorize("@ss.hasPermission('wms:fire-process:query')")
    public CommonResult<FireProcessRespVO> getFireProcess(@RequestParam("id") Long id) {
        FireProcessDO fireProcess = fireProcessService.getFireProcess(id);
        return success(FireProcessConvert.INSTANCE.convert(fireProcess));
    }

    @GetMapping("/list")
    @Operation(description ="获得消防任务列表")
    @PreAuthorize("@ss.hasPermission('wms:fire-process:query')")
    public CommonResult<List<FireProcessRespVO>> getFireProcessList(@RequestParam("ids") Collection<Long> ids) {
        List<FireProcessDO> list = fireProcessService.getFireProcessList(ids);
        return success(FireProcessConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description ="获得消防任务分页")
    @PreAuthorize("@ss.hasPermission('wms:fire-process:query')")
    public CommonResult<PageResult<FireProcessRespVO>> getFireProcessPage(@Valid FireProcessPageReqVO pageVO) {
        PageResult<FireProcessDO> pageResult = fireProcessService.getFireProcessPage(pageVO);
        return success(FireProcessConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出消防任务 Excel")
    @PreAuthorize("@ss.hasPermission('wms:fire-process:export')")
    @OperateLog(type = EXPORT)
    public void exportFireProcessExcel(@Valid FireProcessExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FireProcessDO> list = fireProcessService.getFireProcessList(exportReqVO);
        // 导出 Excel
        List<FireProcessExcelVO> datas = FireProcessConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "消防任务.xls", "数据", FireProcessExcelVO.class, datas);
    }

}
