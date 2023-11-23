package cn.iocoder.yudao.module.wms.dal.mysql.barcode;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.barcode.BarcodeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.barcode.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 条码 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BarcodeMapper extends BaseMapperX<BarcodeDO> {

    default PageResult<BarcodeDO> selectPage(BarcodePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BarcodeDO>()
                .eqIfPresent(BarcodeDO::getBarcode, reqVO.getBarcode())
                .eqIfPresent(BarcodeDO::getDataSource, reqVO.getDataSource())
                .eqIfPresent(BarcodeDO::getSpec, reqVO.getSpec())
                .eqIfPresent(BarcodeDO::getArea, reqVO.getArea())
                .eqIfPresent(BarcodeDO::getStorage, reqVO.getStorage())
                .eqIfPresent(BarcodeDO::getBarcodeStatus, reqVO.getBarcodeStatus())
                .eqIfPresent(BarcodeDO::getFormulaItemId, reqVO.getFormulaItemId())
                .eqIfPresent(BarcodeDO::getNgSite, reqVO.getNgSite())
                .eqIfPresent(BarcodeDO::getRetest, reqVO.getRetest())
                .eqIfPresent(BarcodeDO::getFormulaId, reqVO.getFormulaId())
                .eqIfPresent(BarcodeDO::getTrayId, reqVO.getTrayId())
                .eqIfPresent(BarcodeDO::getChannelIndex, reqVO.getChannelIndex())
                .eqIfPresent(BarcodeDO::getBatchId, reqVO.getBatchId())
                .eqIfPresent(BarcodeDO::getBatteryId, reqVO.getBatteryId())
                .likeIfPresent(BarcodeDO::getCabName, reqVO.getCabName())
                .eqIfPresent(BarcodeDO::getIsLocked, reqVO.getIsLocked())
                .eqIfPresent(BarcodeDO::getMesProductionNumber, reqVO.getMesProductionNumber())
                .eqIfPresent(BarcodeDO::getCustomerNumber, reqVO.getCustomerNumber())
                .eqIfPresent(BarcodeDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(BarcodeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BarcodeDO::getId));
    }

    default List<BarcodeDO> selectList(BarcodeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<BarcodeDO>()
                .eqIfPresent(BarcodeDO::getBarcode, reqVO.getBarcode())
                .eqIfPresent(BarcodeDO::getDataSource, reqVO.getDataSource())
                .eqIfPresent(BarcodeDO::getSpec, reqVO.getSpec())
                .eqIfPresent(BarcodeDO::getArea, reqVO.getArea())
                .eqIfPresent(BarcodeDO::getStorage, reqVO.getStorage())
                .eqIfPresent(BarcodeDO::getBarcodeStatus, reqVO.getBarcodeStatus())
                .eqIfPresent(BarcodeDO::getFormulaItemId, reqVO.getFormulaItemId())
                .eqIfPresent(BarcodeDO::getNgSite, reqVO.getNgSite())
                .eqIfPresent(BarcodeDO::getRetest, reqVO.getRetest())
                .eqIfPresent(BarcodeDO::getFormulaId, reqVO.getFormulaId())
                .eqIfPresent(BarcodeDO::getTrayId, reqVO.getTray())
                .eqIfPresent(BarcodeDO::getChannelIndex, reqVO.getChannelIndex())
                .eqIfPresent(BarcodeDO::getBatchId, reqVO.getBatchId())
                .eqIfPresent(BarcodeDO::getBatteryId, reqVO.getBatteryId())
                .likeIfPresent(BarcodeDO::getCabName, reqVO.getCabName())
                .eqIfPresent(BarcodeDO::getIsLocked, reqVO.getIsLocked())
                .eqIfPresent(BarcodeDO::getMesProductionNumber, reqVO.getMesProductionNumber())
                .eqIfPresent(BarcodeDO::getCustomerNumber, reqVO.getCustomerNumber())
                .eqIfPresent(BarcodeDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(BarcodeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BarcodeDO::getId));
    }

    int createBarcodeTable(@Param("tableName") String tableName);

    /**
     * 判断表是否存在
     *
     * @param tableName 表名
     * @return true：存在；false：不存在
     */
    int existsTable(@Param("tableName") String tableName);

    /**
     * 条码删除
     *
     * @param barcode
     * @param tableName
     * @return
     */
     int deleteByBarcode(@Param("barcode") String barcode,@Param("tableName") String tableName);
}
