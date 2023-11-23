package cn.iocoder.yudao.module.wms.enums.api.tray;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.wms.enums.ApiConstants;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.GroupTrayReqDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.OutboundDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.RemoveTrayReqDTO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.TrayDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author jiangfeng
 * @date 2023/8/1
 */
@FeignClient(name = cn.iocoder.yudao.module.wms.enums.ApiConstants.NAME)
@Tag(name = "RPC 服务 - 托盘")
public interface WmsApi {

    String PREFIX = ApiConstants.PREFIX + "/tray";

    @PostMapping(PREFIX + "/arrive")
    @Operation(description = "托盘入库")
    CommonResult<String> taryArrive(@Valid @RequestBody TrayDTO trayDTO);

    @PostMapping(PREFIX + "/startMove")
    @Operation(description = "托盘调度开始移动")
    CommonResult<String> taryStartMove(@Valid @RequestBody TrayDTO trayDTO);

    @PostMapping(PREFIX + "/outbound")
    @Operation(description = "托盘出库")
    CommonResult<String> outbound(@Valid @RequestBody OutboundDTO outboundDTO);

    @PostMapping(PREFIX + "/groupTray")
    @Operation(description = "组托盘")
    CommonResult<String> groupTray(@Valid @RequestBody GroupTrayReqDTO groupTrayReqDTO);

    @PostMapping(PREFIX + "/removeTray")
    @Operation(description = "拆托盘")
    CommonResult<String> removeTray(@Valid @RequestBody RemoveTrayReqDTO removeTrayReqDTO);

}
