package cn.iocoder.yudao.module.wms.enums.api.barcode;


import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.wms.enums.ApiConstants;
import cn.iocoder.yudao.module.wms.enums.api.barcode.dto.BarcodeCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 *
 * @author jiangfeng
 * @date 2023/6/13
 */

@FeignClient(name = cn.iocoder.yudao.module.wms.enums.ApiConstants.NAME)
@Tag(name = "RPC 服务 - 条码")
public interface BarocdeApi {

    String PREFIX = ApiConstants.PREFIX + "/barcode";

    @PostMapping(PREFIX + "/barcodeCreateByThirdParty")
    @Operation(description = "创建条码")
    CommonResult<Long> barcodeCreateByThirdParty(@Valid BarcodeCreateDTO reqDTO);
}
