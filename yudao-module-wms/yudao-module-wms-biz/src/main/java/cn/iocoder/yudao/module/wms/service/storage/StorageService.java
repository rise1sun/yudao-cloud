package cn.iocoder.yudao.module.wms.service.storage;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wms.controller.admin.storage.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.storage.StorageDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.module.wms.enums.api.storage.dto.StorageDTO;

/**
 * 库位 Service 接口
 *
 * @author 管理员
 */
public interface StorageService {

    /**
     * 根据code获取redis中storage信息
     * @param startStorage
     * @return
     */
    StorageDTO getStorageInfoByStorageCode(String startStorage);

    /**
     * 创建库位
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStorage(@Valid StorageCreateReqVO createReqVO);

    /**
     * 更新库位
     *
     * @param updateReqVO 更新信息
     */
    void updateStorage(@Valid StorageUpdateReqVO updateReqVO);

    /**
     * 删除库位
     *
     * @param id 编号
     */
    void deleteStorage(Long id);

    /**
     * 获得库位
     *
     * @param id 编号
     * @return 库位
     */
    StorageDO getStorage(Long id);

    /**
     * 获得库位列表
     *
     * @param ids 编号
     * @return 库位列表
     */
    List<StorageDO> getStorageList(Collection<Long> ids);

    /**
     * 获得库位分页
     *
     * @param pageReqVO 分页查询
     * @return 库位分页
     */
    PageResult<StorageDO> getStoragePage(StoragePageReqVO pageReqVO);

    /**
     * 获得库位列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 库位列表
     */
    List<StorageDO> getStorageList(StorageExportReqVO exportReqVO);

    /**
     * 获取库位信息
     *
     * @param device
     * @return
     */
    List<StorageDTO> getStorageInfoList(String device);

    /**
     * 根据库位号查询库位信息(不包含特殊库位)
     * @param enddevice
     * @return
     */
    StorageDO getStorageDOByStorage(String enddevice);

    /**
     * 根据任务订单获取推荐的目标库位
     * @param orderTaskId
     * @return
     */
    String getRecommendStorageByOrderTaskId(Integer orderTaskId);

    /**
     * 库位锁定
     * @param code
     */
    void lockStorage(String code);

    /**
     * 库位预锁定
     * @param code
     */
    void preLockStorage(String code);

    /**
     * 库位释放
     * @param code
     */
    void unLockStorage(String code);

    /**
     * 根据托盘号获取TrayLog信息
     *
     * @param tray
     * @return
     */
    TrayLogDO getTrayLogByTray(String tray);

    /**
     * 校验起始点是否存在
     * @param storage
     * @return
     */
    Boolean checkStorage(String storage);

    Boolean vaildEndStorage(String storage);
}
