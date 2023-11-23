package cn.iocoder.yudao.module.wms.api.barcode;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.wms.enums.api.barcode.BarocdeApi;
import cn.iocoder.yudao.module.wms.enums.api.barcode.dto.BarcodeCreateDTO;
import cn.iocoder.yudao.module.wms.service.barcode.BarcodeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.wms.enums.ApiConstants.VERSION;

/**
 * @author jiangfeng
 * @date 2023/6/13
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class BarcodeApiImpl implements BarocdeApi {

    @Resource
    private BarcodeService barcodeService;


    @Override
    public CommonResult<Long> barcodeCreateByThirdParty(BarcodeCreateDTO reqDTO) {
        return success(barcodeService.barcodeCreateByThirdParty(reqDTO.getTray(),reqDTO.getBarcodeLists(), reqDTO.getDataSource()));
    }
}
