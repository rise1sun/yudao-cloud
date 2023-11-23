package cn.iocoder.yudao.module.wms.api.tray;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.wms.enums.api.tray.WmsApi;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.GroupTrayReqDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.OutboundDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.RemoveTrayReqDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.TrayDTO;
import cn.iocoder.yudao.module.wms.service.common.WmsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.module.wms.enums.ApiConstants.VERSION;

/**
 * @author jiangfeng
 * @date 2023/8/1
 */
@RestController
@Validated
public class WmsApiImpl implements WmsApi {

    @Resource
    private WmsService wmsService;

    @Override
    public CommonResult<String> taryArrive(TrayDTO trayDTO) {
        return CommonResult.success(wmsService.taryArrival(trayDTO));
    }

    @Override
    public CommonResult<String> taryStartMove(TrayDTO trayDTO) {
        return CommonResult.success(wmsService.taryStartMove(trayDTO));
    }

    @Override
    public CommonResult<String> outbound(OutboundDTO outboundDTO) {
        return CommonResult.success(wmsService.outbound(outboundDTO));
    }

    @Override
    public CommonResult<String> groupTray(GroupTrayReqDTO groupTrayReqDTO) {
        return CommonResult.success(wmsService.groupTray(groupTrayReqDTO));
    }

    @Override
    public CommonResult<String> removeTray(RemoveTrayReqDTO removeTrayReqDTO) {
        return CommonResult.success(wmsService.removeTray(removeTrayReqDTO));
    }
}
