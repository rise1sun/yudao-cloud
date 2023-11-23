package cn.iocoder.yudao.module.wms.service.barcode;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wms.controller.admin.barcode.vo.*;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.WarehousingVO;
import cn.iocoder.yudao.module.wms.dal.dataobject.barcode.BarcodeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.wms.enums.api.barcode.dto.BatteryinfoDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 条码 Service 接口
 *
 * @author 管理员
 */
public interface BarcodeService {

    /**
     * 创建条码
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBarcode(@Valid BarcodeCreateReqVO createReqVO);

    /**
     * 更新条码
     *
     * @param updateReqVO 更新信息
     */
    void updateBarcode(@Valid BarcodeUpdateReqVO updateReqVO);

    /**
     * 删除条码
     *
     * @param id 编号
     */
    void deleteBarcode(Long id,Long createTime);

    /**
     * 获得条码
     *
     * @param id 编号
     * @return 条码
     */
    BarcodeDO getBarcode(Long id,Long createTime);

    /**
     * 获得条码列表
     *
     * @param ids 编号
     * @return 条码列表
     */
    List<BarcodeDO> getBarcodeList(Collection<Long> ids);

    /**
     * 获得条码分页
     *
     * @param pageReqVO 分页查询
     * @return 条码分页
     */
    PageResult<BarcodeDO> getBarcodePage(BarcodePageReqVO pageReqVO);

    /**
     * 获得条码列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 条码列表
     */
    List<BarcodeDO> getBarcodeList(BarcodeExportReqVO exportReqVO);

    /**
     * 第三方创建条码
     *
     * @param tray
     * @param barcodeLists
     * @param dataSource
     * @return
     */
    @Transactional
    Long barcodeCreateByThirdParty(String tray, List<String> barcodeLists, Integer dataSource);

    /**
     * 分表校验
     * @param month
     * @return
     */
    Boolean createBarcodeTableValid(String month);

    /**
     * PDA删除条码
     * @param barcode
     * @return
     */
    Integer deleteBarcode(String barcode);

    /**
     * PDA创建条码
     * @param tray
     * @param barcodeLists
     * @return
     */
    Long barcodeCreateByPDA(String tray, List<String> barcodeLists,Integer channleIndex);

    /**
     * 获取条码表名用，分割
     * @param barcodes
     * @return
     */
    String getTableName(List<BatteryinfoDTO> barcodes);

    /**
     * 更新条码
     *
     * @param warehousingVO
     * @return
     */
    List<String> updateBarcodeByCode(WarehousingVO warehousingVO);

    /**
     * 更新条码的托盘信息
     * @param barcodes
     */
    void updateBarcodeTrayInfoByCode(List<String> barcodes, Long trayid);
}
