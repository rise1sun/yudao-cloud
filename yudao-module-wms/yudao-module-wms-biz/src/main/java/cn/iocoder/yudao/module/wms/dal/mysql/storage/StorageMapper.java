package cn.iocoder.yudao.module.wms.dal.mysql.storage;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.storage.StorageDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.module.wms.enums.api.storage.dto.StorageDTO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.storage.vo.*;

/**
 * 库位 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface StorageMapper extends BaseMapperX<StorageDO> {

    default PageResult<StorageDO> selectPage(StoragePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StorageDO>()
                .eqIfPresent(StorageDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(StorageDO::getCode, reqVO.getCode())
                .likeIfPresent(StorageDO::getName, reqVO.getName())
                .eqIfPresent(StorageDO::getStatus, reqVO.getStatus())
                .eqIfPresent(StorageDO::getHasGood, reqVO.getHasGood())
                .eqIfPresent(StorageDO::getHasFireChannel, reqVO.getHasFireChannel())
                .eqIfPresent(StorageDO::getSpecialStorage, reqVO.getSpecialStorage())
                .eqIfPresent(StorageDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(StorageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(StorageDO::getId));
    }

    default List<StorageDO> selectList(StorageExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<StorageDO>()
                .eqIfPresent(StorageDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(StorageDO::getCode, reqVO.getCode())
                .likeIfPresent(StorageDO::getName, reqVO.getName())
                .eqIfPresent(StorageDO::getStatus, reqVO.getStatus())
                .eqIfPresent(StorageDO::getHasGood, reqVO.getHasGood())
                .eqIfPresent(StorageDO::getHasFireChannel, reqVO.getHasFireChannel())
                .eqIfPresent(StorageDO::getSpecialStorage, reqVO.getSpecialStorage())
                .eqIfPresent(StorageDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(StorageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(StorageDO::getId));
    }

    List<StorageDTO> getStorageInfoList(String storage);

    TrayLogDO getTrayLogByTray(String tray);
}
