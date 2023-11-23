package cn.iocoder.yudao.module.wms.controller.admin.tray;

import cn.iocoder.yudao.module.wms.enums.enums.StatusEnum;
import cn.iocoder.yudao.module.wms.enums.enums.TypeEnum;
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

import cn.iocoder.yudao.module.wms.controller.admin.tray.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import cn.iocoder.yudao.module.wms.convert.tray.TrayConvert;
import cn.iocoder.yudao.module.wms.service.tray.TrayService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name= "管理后台 - 托盘")
@RestController
@RequestMapping("/wms/tray")
@Validated
public class TrayController {

    @Resource
    private TrayService trayService;

    @PostMapping("/create")
    @Operation(description ="创建托盘")
    @PreAuthorize("@ss.hasPermission('wms:tray:create')")
    public CommonResult<Long> createTray(@Valid @RequestBody TrayCreateReqVO createReqVO) {
        return success(trayService.createTray(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新托盘")
    @PreAuthorize("@ss.hasPermission('wms:tray:update')")
    public CommonResult<Boolean> updateTray(@Valid @RequestBody TrayUpdateReqVO updateReqVO) {
        trayService.updateTray(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除托盘")
    @PreAuthorize("@ss.hasPermission('wms:tray:delete')")
    public CommonResult<Boolean> deleteTray(@RequestParam("id") Long id) {
        trayService.deleteTray(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得托盘")
    @PreAuthorize("@ss.hasPermission('wms:tray:query')")
    public CommonResult<TrayRespVO> getTray(@RequestParam("id") Long id) {
        TrayDO tray = trayService.getTray(id);
        return success(TrayConvert.INSTANCE.convert(tray));
    }

    @GetMapping("/list")
    @Operation(description ="获得托盘列表")
    @PreAuthorize("@ss.hasPermission('wms:tray:query')")
    public CommonResult<List<TrayRespVO>> getTrayList(@RequestParam("ids") Collection<Long> ids) {
        List<TrayDO> list = trayService.getTrayList(ids);
        return success(TrayConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description ="获得托盘分页")
    @PreAuthorize("@ss.hasPermission('wms:tray:query')")
    public CommonResult<PageResult<TrayRespVO>> getTrayPage(@Valid TrayPageReqVO pageVO) {
        PageResult<TrayDO> pageResult = trayService.getTrayPage(pageVO);
        return success(TrayConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出托盘 Excel")
    @PreAuthorize("@ss.hasPermission('wms:tray:export')")
    @OperateLog(type = EXPORT)
    public void exportTrayExcel(@Valid TrayExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TrayDO> list = trayService.getTrayList(exportReqVO);
        // 导出 Excel
        List<TrayExcelVO> datas = TrayConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "托盘.xls", "数据", TrayExcelVO.class, datas);
    }

    @GetMapping("/get-import-template")
    @Operation(description ="获得导入用户模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<TrayImportExcelVO> list = Arrays.asList(
                TrayImportExcelVO.builder().trayNo("T00001").status(StatusEnum.BIND.getStatus()).type(TypeEnum.BIG.getType()).maxBindNumber(196).maxUseNumber(10000).build(),
                TrayImportExcelVO.builder().trayNo("T00002").status(StatusEnum.FREE.getStatus()).type(TypeEnum.NORMOL.getType()).maxBindNumber(100).maxUseNumber(20000).build()
        );

        // 输出
        ExcelUtils.write(response, "托盘导入模板.xls", "托盘列表", TrayImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(description ="导入托盘")
    @PreAuthorize("@ss.hasPermission('system:user:import')")
    public CommonResult<TrayImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<TrayImportExcelVO> list = ExcelUtils.read(file, TrayImportExcelVO.class);
        return success(trayService.importTrayList(list, updateSupport));
    }

    @GetMapping("/checkTray")
    @Operation(description ="托盘校验")
    public CommonResult<Map<Boolean,String>> checkTray(@RequestParam("tray") String tray) {
        Map<Boolean,String> checkTray = trayService.checkTray(tray);
        return success(checkTray);
    }

}
