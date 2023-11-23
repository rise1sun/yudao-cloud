package cn.iocoder.yudao.module.wms.controller.admin.storage;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.wms.controller.admin.storage.vo.*;
import cn.iocoder.yudao.module.wms.convert.storage.StorageConvert;
import cn.iocoder.yudao.module.wms.dal.dataobject.storage.StorageDO;
import cn.iocoder.yudao.module.wms.mq.producer.TaskProducer;
import cn.iocoder.yudao.module.wms.service.storage.StorageService;
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

@Tag(name= "管理后台 - 库位")
@RestController
@RequestMapping("/wms/storage")
@Validated
public class StorageController {

    @Resource
    private StorageService storageService;

    /*@Resource
    private AreaApi areaApi;*/

    @Resource
    private TaskProducer taskProducer;

    @PostMapping("/create")
    @Operation(description ="创建库位")
    @PreAuthorize("@ss.hasPermission('wms:storage:create')")
    public CommonResult<Long> createStorage(@Valid @RequestBody StorageCreateReqVO createReqVO) {
        return success(storageService.createStorage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新库位")
    @PreAuthorize("@ss.hasPermission('wms:storage:update')")
    public CommonResult<Boolean> updateStorage(@Valid @RequestBody StorageUpdateReqVO updateReqVO) {
        storageService.updateStorage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除库位")
    @PreAuthorize("@ss.hasPermission('wms:storage:delete')")
    public CommonResult<Boolean> deleteStorage(@RequestParam("id") Long id) {
        storageService.deleteStorage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得库位")
    @PreAuthorize("@ss.hasPermission('wms:storage:query')")
    public CommonResult<StorageRespVO> getStorage(@RequestParam("id") Long id) {
        StorageDO storage = storageService.getStorage(id);
        return success(StorageConvert.INSTANCE.convert(storage));
    }

    @GetMapping("/list")
    @Operation(description ="获得库位列表")
    @PreAuthorize("@ss.hasPermission('wms:storage:query')")
    public CommonResult<List<StorageRespVO>> getStorageList(@RequestParam("ids") Collection<Long> ids) {
        List<StorageDO> list = storageService.getStorageList(ids);
        return success(StorageConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description ="获得库位分页")
    @PreAuthorize("@ss.hasPermission('wms:storage:query')")
    public CommonResult<PageResult<StorageRespVO>> getStoragePage(@Valid StoragePageReqVO pageVO) {
        PageResult<StorageDO> pageResult = storageService.getStoragePage(pageVO);
        return success(StorageConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出库位 Excel")
    @PreAuthorize("@ss.hasPermission('wms:storage:export')")
    @OperateLog(type = EXPORT)
    public void exportStorageExcel(@Valid StorageExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<StorageDO> list = storageService.getStorageList(exportReqVO);
        // 导出 Excel
        List<StorageExcelVO> datas = StorageConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "库位.xls", "数据", StorageExcelVO.class, datas);
    }

   /* @GetMapping("/getArealist")
    @Operation(description ="获得区域列表")
    @PreAuthorize("@ss.hasPermission('wms:storage:query')")
    public CommonResult<List<AreaDto>> getArealist() {
        CommonResult<List<AreaDto>> allAreas = areaApi.getAllAreas();
        return allAreas;
    }*/

}
