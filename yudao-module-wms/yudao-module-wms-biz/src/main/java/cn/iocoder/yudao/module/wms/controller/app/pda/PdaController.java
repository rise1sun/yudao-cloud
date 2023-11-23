package cn.iocoder.yudao.module.wms.controller.app.pda;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.wms.controller.admin.barcode.vo.BarcodeDeleteReqVO;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.GroupTrayVO;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.ManualCuttingVO;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.TrayStorageVO;
import cn.iocoder.yudao.module.wms.enums.api.barcode.dto.BarcodeCreateDTO;
import cn.iocoder.yudao.module.wms.service.barcode.BarcodeService;
import cn.iocoder.yudao.module.wms.service.pda.PdaService;
import cn.iocoder.yudao.module.wms.service.pda.manualCutting.ManualCuttingService;
import cn.iocoder.yudao.module.wms.service.restingtask.RestingTaskService;
import cn.iocoder.yudao.module.wms.service.storage.StorageService;
import cn.iocoder.yudao.module.wms.service.tray.TrayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * @author jiangfeng
 * @date 2023/7/13
 */
@Tag(name= "管理后台 - PDA")
@RestController
@RequestMapping("/wms/pda")
@Validated
public class PdaController {

    @Resource
    private PdaService pdaService;

    @Resource
    private TrayService trayService;

    @Resource
    private BarcodeService barcodeService;

    @Resource
    private RestingTaskService restingTaskService;

    @Resource
    private StorageService storageService;

    @Resource
    private ManualCuttingService manualCuttingService;

    @PostMapping("/groupTray")
    @Operation(description ="PDA人工组托")
    public CommonResult<String> groupTray(@Valid @RequestBody GroupTrayVO reqDTO) {
        return success(pdaService.groupTray(reqDTO));
    }

    @PostMapping("/checkParameters")
    @Operation(description ="库位检验")
    public CommonResult<Map<Boolean,String>> checkParameters(@Valid @RequestBody TrayStorageVO reqDTO) {
        Map<Boolean, String> map = pdaService.checkParameters(reqDTO);
        return success(map);
    }

    @GetMapping("/checkTray")
    @Operation(description ="托盘校验")
    public CommonResult<Map<Boolean,String>> checkTray(@RequestParam("tray") String tray) {
        Map<Boolean,String> checkTray = trayService.checkTray(tray);
        return success(checkTray);
    }

    @PostMapping("/checkStorage")
    @Operation(description ="库位检验")
    public CommonResult<Boolean> checkStorage(@RequestParam("storage") String storage) {
        Boolean checkStorage = storageService.checkStorage(storage);
        return success(checkStorage);
    }

    @PostMapping("/barcodeCreateByPDA")
    @Operation(description ="PDA创建条码")
    public CommonResult<Long> createBarcodeByPDA(@RequestBody @Valid BarcodeCreateDTO reqDTO) {
        Long aLong = barcodeService.barcodeCreateByPDA(reqDTO.getTray(), reqDTO.getBarcodeLists(), reqDTO.getChannelIndex());
        return success(aLong);
    }

    @PostMapping("/deleteBarcode")
    @Operation(description ="PDA条码删除")
    public CommonResult<Integer> deleteBarcode(@RequestBody @Valid BarcodeDeleteReqVO reqVO) {
        return success(barcodeService.deleteBarcode(reqVO.getBarcode()));
    }

    @PostMapping("/manualCutting")
    @Operation(description ="手动下料")
    public CommonResult<String> manualCutting(@RequestBody @Valid ManualCuttingVO reqVO) {
        String tray = reqVO.getTrayNO();
        String storage = reqVO.getStorage();
        //校验是否允许下料
        Boolean cuttingValid = pdaService.cuttingValid(tray,storage);
        if(!cuttingValid){
            return error(0,"托盘未在指定库位,不允许下料！");
        }
        Boolean vaildEndStorage =storageService.vaildEndStorage("TS-01");
        if(!vaildEndStorage){
            return error(0,"下料口正在被使用,请稍后再试！");
        }
        return success(manualCuttingService.manualCutting(tray,storage));
    }

}
