package cn.iocoder.yudao.module.wms.service.storage;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.module.wms.enums.api.storage.dto.StorageDTO;
import cn.iocoder.yudao.module.wms.enums.common.Constants;
import cn.iocoder.yudao.module.wms.service.formulaitem.FormulaItemService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.wms.controller.admin.storage.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.storage.StorageDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.convert.storage.StorageConvert;
import cn.iocoder.yudao.module.wms.dal.mysql.storage.StorageMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.*;

/**
 * 库位 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageMapper storageMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private FormulaItemService formulaItemService;

    @Override
    public Long createStorage(StorageCreateReqVO createReqVO) {
        // 插入
        StorageDO storage = StorageConvert.INSTANCE.convert(createReqVO);
        storageMapper.insert(storage);
        // 返回
        return storage.getId();
    }

    @Override
    public void updateStorage(StorageUpdateReqVO updateReqVO) {
        // 校验存在
        validateStorageExists(updateReqVO.getId());
        // 更新
        StorageDO updateObj = StorageConvert.INSTANCE.convert(updateReqVO);
        storageMapper.updateById(updateObj);
    }

    @Override
    public void deleteStorage(Long id) {
        // 校验存在
        validateStorageExists(id);
        // 删除
        storageMapper.deleteById(id);
    }

    private void validateStorageExists(Long id) {
        if (storageMapper.selectById(id) == null) {
            throw exception(STORAGE_NOT_EXISTS);
        }
    }

    @Override
    public StorageDO getStorage(Long id) {
        return storageMapper.selectById(id);
    }

    @Override
    public List<StorageDO> getStorageList(Collection<Long> ids) {
        return storageMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<StorageDO> getStoragePage(StoragePageReqVO pageReqVO) {
        return storageMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StorageDO> getStorageList(StorageExportReqVO exportReqVO) {
        return storageMapper.selectList(exportReqVO);
    }

    @PostConstruct
    private void storageInfoCache(){
       List<StorageDTO> list = getStorageInfoList(null);
        for (StorageDTO storageDTO : list) {
            String field = storageDTO.getCode();
            // 将对象序列化成字符串作为 Hash 的字段值
            String value = JSON.toJSONString(storageDTO);
            // 将 Hash 的字段添加到 Redis 中
            redisTemplate.opsForHash().put(Constants.STORAGE_HASHKEY, field, value);
        }
    }


    @Override
    public StorageDTO getStorageInfoByStorageCode(String storageCode){
        String StorageCacheInfo = (String) redisTemplate.opsForHash().get(Constants.STORAGE_HASHKEY, storageCode);
        StorageDTO storageDTO = JSON.parseObject(StorageCacheInfo, StorageDTO.class);

        if(storageDTO != null){
            return storageDTO;

        }
        this.storageInfoCache();
        String StorageCacheInfo1 = (String) redisTemplate.opsForHash().get(Constants.STORAGE_HASHKEY, storageCode);
        StorageDTO storageDTO1 = JSON.parseObject(StorageCacheInfo, StorageDTO.class);
        return storageDTO1;
    }
    public List<StorageDTO> getStorageInfoList(String storage) {
        List<StorageDTO> list = storageMapper.getStorageInfoList(storage);
        return list;
    }

    @Override
    public StorageDO getStorageDOByStorage(String enddevice) {
        List<StorageDO> list = storageMapper.selectList(new LambdaQueryWrapperX<StorageDO>()
                .eqIfPresent(StorageDO::getCode, enddevice));
        if(list.size() == 0){
            throw new ServiceException(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode()
                    ,"库位未维护，请联系管理员！");
        }
        if(Constants.storageStatus.FREE.ordinal() != list.get(0).getStatus()){
            throw new ServiceException(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode()
                    ,"库位已被占用，请更换目标库位！");
        }

        if(Constants.NOT_SPECIAL_STORAGE != list.get(0).getSpecialStorage()){
            throw new ServiceException(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode()
                    ,"特殊库位,暂不允许使用,请更换目标库位！");
        }

        return list.get(0);
    }

    @Override
    public String getRecommendStorageByOrderTaskId(Integer orderTaskId) {
        //目标工艺
        FormulaItemDO formulaItemDO = formulaItemService.getNewFormulaItemByOldFormulaId(orderTaskId);
        String area = formulaItemDO.getArea();
        StorageDO storageDO = getStorageDOByAreaPre(area);
        return storageDO.getCode();
    }

    @Override
    public void lockStorage(String code) {
        LambdaUpdateWrapper<StorageDO> lockStorage = new LambdaUpdateWrapper<StorageDO>().eq(StorageDO::getCode, code)
                .set(StorageDO::getStatus, Constants.storageStatus.OCCUPY.ordinal());
        storageMapper.update(null,lockStorage);

        //TODO 更新库位缓存
    }

    @Override
    public void preLockStorage(String code) {
        LambdaUpdateWrapper<StorageDO> preLockStorage = new LambdaUpdateWrapper<StorageDO>().eq(StorageDO::getCode, code)
                .set(StorageDO::getStatus, Constants.storageStatus.PREOCCUPY.ordinal());
        storageMapper.update(null,preLockStorage);
        //TODO 更新库位缓存
    }

    @Override
    public void unLockStorage(String code) {
        LambdaUpdateWrapper<StorageDO> preLockStorage = new LambdaUpdateWrapper<StorageDO>().eq(StorageDO::getCode, code)
                .set(StorageDO::getStatus, Constants.storageStatus.FREE.ordinal());
        storageMapper.update(null,preLockStorage);
        //TODO 更新库位缓存
    }

    @Override
    public TrayLogDO getTrayLogByTray(String tray) {
        return storageMapper.getTrayLogByTray(tray);
    }

    @Override
    public Boolean checkStorage(String Storage) {
        StorageDO storageDOByStorage = getStorageDOByStorage(Storage);
        if(storageDOByStorage == null){
            return false;
        }
        if(!storageDOByStorage.getStatus().equals(Constants.storageStatus.FREE.ordinal())){
            return false;
        }
        return true;
    }

    @Override
    public Boolean vaildEndStorage(String storage) {
        LambdaQueryWrapperX<StorageDO> storageDOLambdaQueryWrapperX = new LambdaQueryWrapperX<StorageDO>()
                .eqIfPresent(StorageDO::getCode, storage)
                .eqIfPresent(StorageDO::getStatus, Constants.storageStatus.FREE);
        StorageDO storageDO = storageMapper.selectOne(storageDOLambdaQueryWrapperX);
        if(ObjectUtil.isNotNull(storageDO)){
            return true;
        }
        return false;
    }


    private StorageDO getStorageDOByAreaPre(String area) {
        LambdaQueryWrapperX<StorageDO> storageDOLambdaQueryWrapperX = new LambdaQueryWrapperX<StorageDO>()
                .likeIfPresent(StorageDO::getCode, area)
                .eqIfPresent(StorageDO::getSpecialStorage,Constants.NOT_SPECIAL_STORAGE)
                .eqIfPresent(StorageDO::getStatus,Constants.storageStatus.FREE.ordinal());
        //TODO 推荐规则待定 暂时随机放
        List<StorageDO> list = storageMapper.selectList(storageDOLambdaQueryWrapperX);
        if(list.size() == 0){
            throw new ServiceException(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode()
                    ,area+"区域暂无推荐库位!");
        }
        return list.get(0);
    }

}
