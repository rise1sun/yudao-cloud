package cn.iocoder.yudao.module.wms.controller.admin.barcode;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.wms.controller.admin.barcode.vo.*;
import cn.iocoder.yudao.module.wms.convert.barcode.BarcodeConvert;
import cn.iocoder.yudao.module.wms.dal.dataobject.barcode.BarcodeDO;
import cn.iocoder.yudao.module.wms.enums.api.barcode.dto.BarcodeCreateDTO;
import cn.iocoder.yudao.module.wms.service.barcode.BarcodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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

@Tag(name = "管理后台 - 条码")
@RestController
@RequestMapping("/wms/barcode")
@Validated
@Slf4j
public class BarcodeController {

    @Resource
    private BarcodeService barcodeService;

    @PostMapping("/create")
    @Operation(description = "创建条码")
    @PreAuthorize("@ss.hasPermission('wms:barcode:create')")
    public CommonResult<Long> createBarcode(@Valid @RequestBody BarcodeCreateReqVO createReqVO) {
        return success(barcodeService.createBarcode(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description="更新条码")
    @PreAuthorize("@ss.hasPermission('wms:barcode:update')")
    public CommonResult<Boolean> updateBarcode(@Valid @RequestBody BarcodeUpdateReqVO updateReqVO) {
        barcodeService.updateBarcode(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description="删除条码")
    @PreAuthorize("@ss.hasPermission('wms:barcode:delete')")
    public CommonResult<Boolean> deleteBarcode(@RequestParam("id") Long id, @RequestParam("createTime") Long createTime) {
        barcodeService.deleteBarcode(id, createTime);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description="获得条码")
     @PreAuthorize("@ss.hasPermission('wms:barcode:query')")
    public CommonResult<BarcodeRespVO> getBarcode(@RequestParam("id") Long id, @RequestParam("createTime") Long createTime) {
        BarcodeDO barcode = barcodeService.getBarcode(id, createTime);
        return success(BarcodeConvert.INSTANCE.convert(barcode));
    }

    @GetMapping("/list")
    @Operation(description="获得条码列表")
    @PreAuthorize("@ss.hasPermission('wms:barcode:query')")
    public CommonResult<List<BarcodeRespVO>> getBarcodeList(@RequestParam("ids") Collection<Long> ids) {
        List<BarcodeDO> list = barcodeService.getBarcodeList(ids);
        return success(BarcodeConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description="获得条码分页")
    @PreAuthorize("@ss.hasPermission('wms:barcode:query')")
    public CommonResult<PageResult<BarcodeRespVO>> getBarcodePage(@Valid BarcodePageReqVO pageVO) {
        PageResult<BarcodeDO> pageResult = barcodeService.getBarcodePage(pageVO);
        return success(BarcodeConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description="导出条码 Excel")
    @PreAuthorize("@ss.hasPermission('wms:barcode:export')")
    @OperateLog(type = EXPORT)
    public void exportBarcodeExcel(@Valid BarcodeExportReqVO exportReqVO,
                                   HttpServletResponse response) throws IOException {
        List<BarcodeDO> list = barcodeService.getBarcodeList(exportReqVO);
        // 导出 Excel
        List<BarcodeExcelVO> datas = BarcodeConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "条码.xls", "数据", BarcodeExcelVO.class, datas);
    }

    @PostMapping("/barcodeCreateByThirdParty")
    @Operation(description="第三方创建条码")
    @PreAuthorize("@ss.hasPermission('wms:barcode:create')")
    public CommonResult<Long> createBarcodeByThirdParty(@RequestBody @Valid BarcodeCreateDTO reqDTO) {
        return success(barcodeService.barcodeCreateByThirdParty(reqDTO.getTray(), reqDTO.getBarcodeLists(),reqDTO.getDataSource()));
    }


}
