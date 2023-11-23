package cn.iocoder.yudao.module.wms.controller.admin.traylog;

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

import cn.iocoder.yudao.module.wms.controller.admin.traylog.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.module.wms.convert.traylog.TrayLogConvert;
import cn.iocoder.yudao.module.wms.service.traylog.TrayLogService;

@Tag(name= "管理后台 - wms托盘日志记录")
@RestController
@RequestMapping("/wms/tray-log")
@Validated
public class TrayLogController {

    @Resource
    private TrayLogService trayLogService;

    @PostMapping("/create")
    @Operation(description ="创建wms托盘日志记录")
    @PreAuthorize("@ss.hasPermission('wms:tray-log:create')")
    public CommonResult<Integer> createTrayLog(@Valid @RequestBody TrayLogCreateReqVO createReqVO) {
        return success(trayLogService.createTrayLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新wms托盘日志记录")
    @PreAuthorize("@ss.hasPermission('wms:tray-log:update')")
    public CommonResult<Boolean> updateTrayLog(@Valid @RequestBody TrayLogUpdateReqVO updateReqVO) {
        trayLogService.updateTrayLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除wms托盘日志记录")
    @PreAuthorize("@ss.hasPermission('wms:tray-log:delete')")
    public CommonResult<Boolean> deleteTrayLog(@RequestParam("id") Integer id) {
        trayLogService.deleteTrayLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得wms托盘日志记录")
    @PreAuthorize("@ss.hasPermission('wms:tray-log:query')")
    public CommonResult<TrayLogRespVO> getTrayLog(@RequestParam("id") Integer id) {
        TrayLogDO trayLog = trayLogService.getTrayLog(id);
        return success(TrayLogConvert.INSTANCE.convert(trayLog));
    }

    @GetMapping("/list")
    @Operation(description ="获得wms托盘日志记录列表")
    @PreAuthorize("@ss.hasPermission('wms:tray-log:query')")
    public CommonResult<List<TrayLogRespVO>> getTrayLogList(@RequestParam("ids") Collection<Integer> ids) {
        List<TrayLogDO> list = trayLogService.getTrayLogList(ids);
        return success(TrayLogConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description ="获得wms托盘日志记录分页")
    @PreAuthorize("@ss.hasPermission('wms:tray-log:query')")
    public CommonResult<PageResult<TrayLogRespVO>> getTrayLogPage(@Valid TrayLogPageReqVO pageVO) {
        PageResult<TrayLogDO> pageResult = trayLogService.getTrayLogPage(pageVO);
        return success(TrayLogConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出wms托盘日志记录 Excel")
    @PreAuthorize("@ss.hasPermission('wms:tray-log:export')")
    @OperateLog(type = EXPORT)
    public void exportTrayLogExcel(@Valid TrayLogExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TrayLogDO> list = trayLogService.getTrayLogList(exportReqVO);
        // 导出 Excel
        List<TrayLogExcelVO> datas = TrayLogConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "wms托盘日志记录.xls", "数据", TrayLogExcelVO.class, datas);
    }

}
