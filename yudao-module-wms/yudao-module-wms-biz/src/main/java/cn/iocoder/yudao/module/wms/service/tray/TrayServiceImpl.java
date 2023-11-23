package cn.iocoder.yudao.module.wms.service.tray;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.datapermission.core.util.DataPermissionUtils;
import cn.iocoder.yudao.module.wms.common.RedissonLock;
import cn.iocoder.yudao.module.wms.controller.admin.tray.vo.*;
import cn.iocoder.yudao.module.wms.convert.tray.TrayConvert;
import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import cn.iocoder.yudao.module.wms.dal.mysql.tray.TrayMapper;
import cn.iocoder.yudao.module.wms.enums.api.storage.dto.StorageDTO;
import cn.iocoder.yudao.module.wms.enums.common.Constants;
import cn.iocoder.yudao.module.wms.service.aspect.TrayAspect;
import cn.iocoder.yudao.module.wms.service.storage.StorageService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.*;

/**
 * 托盘 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class TrayServiceImpl implements TrayService {

    @Resource
    private TrayMapper trayMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedissonLock redissonLock;

    @Resource
    private StorageService storageService;

    @Resource
    private TrayAspect aspect;

    private static final String LOCK_KEY = "tray_lock";

    @Override
    public Long createTray(TrayCreateReqVO createReqVO) {
        // 插入
        TrayDO tray = TrayConvert.INSTANCE.convert(createReqVO);
        trayMapper.insert(tray);
        // 更新缓存
        insertRedisTrayInfo(tray);
        return tray.getId();
    }

    /**
     * 插入缓存
     * @param tray
     */
    private void insertRedisTrayInfo(TrayDO tray) {
        RLock lock = redissonLock.getLock(tray.getTrayNo());
        try {
            boolean locked = redissonLock.tryLock(lock, 1, TimeUnit.SECONDS);
            if (locked) {
                //插入操作
                String val = JSON.toJSONString(tray);
                redisTemplate.opsForHash().put(Constants.TRAY_HASHKEY, tray.getTrayNo(), val);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // 记录日志或者抛出异常
            log.info("托盘更新缓存失败！");
        } finally {
            //是否持有锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }


    /**
     * 更新缓存
     * @param tray
     */
    public void updateRedisTrayInfo(TrayDO tray) {
        RLock lock = redissonLock.getLock(tray.getTrayNo());
        try {
            boolean locked = redissonLock.tryLock(lock, 1, TimeUnit.SECONDS);
            if (locked) {
                //删除 插入操作
                redisTemplate.opsForHash().delete(Constants.TRAY_HASHKEY,tray.getTrayNo());
                String val = JSON.toJSONString(tray);
                redisTemplate.opsForHash().put(Constants.TRAY_HASHKEY, tray.getTrayNo(), val);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // 记录日志或者抛出异常
            log.info("托盘更新缓存失败！");
        } finally {
            //是否持有锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public TrayDO getTrayDO(String trayNO) {
        String value = getRedisTrayInfo(trayNO);
        TrayDO trayDO = JSON.parseObject(value, TrayDO.class);
        return trayDO;
    }

    @Override
    public TrayDO getTrayDOByDB(String trayNO) {
        TrayDO trayDO = trayMapper.selectByTrayNo(trayNO);
        return trayDO;
    }

    /**
     * 删除缓存
     * @param tray
     */
    private void deleteRedisTrayInfo(TrayDO tray) {
        RLock lock = redissonLock.getLock(tray.getTrayNo());
        try {
            boolean locked = redissonLock.tryLock(lock, 1, TimeUnit.SECONDS);
            if (locked) {
                //删除 插入操作
                redisTemplate.opsForHash().delete(Constants.TRAY_HASHKEY,tray.getTrayNo());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // 记录日志或者抛出异常
            log.info("托盘更新缓存失败！");
        } finally {
            //是否持有锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 读取缓存
     * @param trayNo
     */
    public String getRedisTrayInfo(String trayNo) {
        RLock lock = redissonLock.getLock(trayNo);
        String value = null;
        try {
            boolean locked = redissonLock.tryLock(lock, 1, TimeUnit.SECONDS);
            if (locked) {
                //读取操作
                value = (String) redisTemplate.opsForHash().get(Constants.TRAY_HASHKEY, trayNo);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // 记录日志或者抛出异常
            log.info(trayNo + "托盘缓存查询失败！");
        } finally {
            //是否持有锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return value;
    }

    @Override
    public void updateTray(TrayUpdateReqVO updateReqVO) {
        // 校验存在
        validateTrayExists(updateReqVO.getId());
        // 更新
        TrayDO updateObj = TrayConvert.INSTANCE.convert(updateReqVO);
        updateRedisTrayInfo(updateObj);
        trayMapper.updateById(updateObj);
    }

    @Override
    public void deleteTray(Long id) {
        // 校验存在
        validateTrayExists(id);
        // 删除
        TrayDO trayDO = trayMapper.selectOne(TrayDO::getId, id);
        deleteRedisTrayInfo(trayDO);
        trayMapper.deleteById(id);
    }



    private void validateTrayExists(Long id) {
        if (trayMapper.selectById(id) == null) {
            throw exception(TRAY_NOT_EXISTS);
        }
    }

    @Override
    public TrayDO getTray(Long id) {
        return trayMapper.selectById(id);
    }

    @Override
    public List<TrayDO> getTrayList(Collection<Long> ids) {
        return trayMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TrayDO> getTrayPage(TrayPageReqVO pageReqVO) {
        return trayMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TrayDO> getTrayList(TrayExportReqVO exportReqVO) {
        return trayMapper.selectList(exportReqVO);
    }

    @Override
    public List<TrayDO> getTrayList() {
        return trayMapper.selectList();
    }


    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public TrayImportRespVO importTrayList(List<TrayImportExcelVO> importTrays, Boolean updateSupport) {
        if (CollUtil.isEmpty(importTrays)) {
            throw exception(TRAY_IMPORT_LIST_IS_EMPTY);
        }
        TrayImportRespVO respVO = TrayImportRespVO.builder().createTrayNo(new ArrayList<>())
                .updateTrayNo(new ArrayList<>()).failureTaryNo(new LinkedHashMap<>()).build();
        importTrays.forEach(importTray -> {
            // 校验，判断是否有不符合的原因
            try {
                validateTrayForCreateOrUpdate(importTray.getTrayNo(), importTray.getStatus(), importTray.getType(),importTray.getMaxBindNumber(),importTray.getMaxUseNumber());
            } catch (ServiceException ex) {
                respVO.getFailureTaryNo().put(importTray.getTrayNo(), ex.getMessage());
                return;
            }
            // 判断如果不存在，在进行插入
            TrayDO existTray = trayMapper.selectByTrayNo(importTray.getTrayNo());
            if (existTray == null) {
                trayMapper.insert(TrayConvert.INSTANCE.convert(importTray)); // 设置默认密码及空岗位编号数组
                respVO.getCreateTrayNo().add(importTray.getTrayNo());
                return;
            }
            // 如果存在，判断是否允许更新
            if (!updateSupport) {
                respVO.getFailureTaryNo().put(importTray.getTrayNo(), TRAY_TRAYNO_EXISTS.getMsg());
                return;
            }
            TrayDO updateTray = TrayConvert.INSTANCE.convert(importTray);
            updateTray.setId(existTray.getId());
            trayMapper.updateById(updateTray);
            respVO.getUpdateTrayNo().add(importTray.getTrayNo());
        });
        return respVO;
    }

    @Override
    public void update(LambdaUpdateWrapper<TrayDO> updateWrapper) {
        trayMapper.update(null, updateWrapper);
    }

    @Override
    public void trayOccupy(TrayDO trayDO, String startStorage) {
        StorageDTO storageDTO =storageService.getStorageInfoByStorageCode(startStorage);
        trayMapper.update(trayDO,new LambdaUpdateWrapper<TrayDO>()
                .eq(TrayDO::getTrayNo,trayDO.getTrayNo())
                .set(TrayDO::getStatus, Constants.trayType.BUSY.ordinal())
                .set(TrayDO::getUseNumber,trayDO.getUseNumber()+1)
                .set(TrayDO::getBarcodeTableName,trayDO.getBarcodeTableName())
                .set(TrayDO::getOrderTaskId,trayDO.getOrderTaskId())
                .set(TrayDO::getStorageId,storageDTO.getStorageId())
                .set(TrayDO::getStorageName,startStorage));
        //更新缓存
        updateRedisTrayInfo(trayDO.setStatus((byte) Constants.trayType.BUSY.ordinal())
                .setUseNumber(trayDO.getUseNumber()+1)
                .setBarcodeTableName(trayDO.getBarcodeTableName())
                .setOrderTaskId(trayDO.getOrderTaskId())
                .setStorageId(Integer.valueOf(storageDTO.getStorageId()))
                .setStorageName(startStorage));
    }

    @Override
    public void taryRelease(String tray) {
        String value = (String) redisTemplate.opsForHash().get(Constants.TRAY_HASHKEY, tray);
        TrayDO trayDO = JSON.parseObject(value, TrayDO.class);
        trayMapper.update(new TrayDO(),new LambdaUpdateWrapper<TrayDO>()
                .eq(TrayDO::getTrayNo,tray)
                .set(TrayDO::getStatus, Constants.trayType.FREE.ordinal())
                .set(TrayDO::getBarcodeTableName,null));
        //更新缓存
        updateRedisTrayInfo(trayDO.setStatus((byte) Constants.trayType.FREE.ordinal())
                .setUseNumber(trayDO.getUseNumber()+1)
                .setBarcodeTableName(trayDO.getBarcodeTableName()));
    }

    @Override
    public Map<Boolean, String> checkTray(String tray) {
        Map<Boolean, String> map = new HashMap<>();
        TrayDO trayDO = trayMapper.selectByTrayNo(tray);
        if(trayDO == null){
            map.put(false, "托盘不存在！");
            return map;
        }
        // 校验状态和绑定次数
        Byte status = trayDO.getStatus();
        StringBuilder errorMessage = new StringBuilder();
        if (Constants.trayType.BUSY.ordinal() == status) {
            errorMessage.append("托盘当前状态无法被使用,");
        }
        if (trayDO.getMaxUseNumber() <= trayDO.getUseNumber()) {
            errorMessage.append("托盘使用次数超过设置上线,");
        }

        if(StrUtil.isBlankIfStr(errorMessage)){
            map.put(true, "托盘可正常使用");
            return map;
        }else{
            map.put(false, errorMessage.toString());
            return map;
        }
    }

    /**
     * 数据校验
     * @param trayNo
     * @param status
     * @param type
     * @param maxBindNumber
     * @param maxUseNumber
     */
    private void validateTrayForCreateOrUpdate(String trayNo, Integer status, Integer type, Integer maxBindNumber, Integer maxUseNumber) {
        // 关闭数据权限，避免因为没有数据权限，查询不到数据，进而导致唯一校验不正确
        DataPermissionUtils.executeIgnore(() -> {
            // 校验托盘编号唯一
            validateTrayUnique(trayNo);

        });
    }

    private void validateTrayUnique(String trayNo) {
        if (StrUtil.isBlank(trayNo)) {
            throw exception(TRAY_TRAYNO_IS_BLANK);
        }
    }

    @PostConstruct
    private void trayInfoCache(){
        List<TrayDO> trayList = getTrayList();
        for (TrayDO tray : trayList) {
            String field = tray.getTrayNo();
            // 将对象序列化成字符串作为 Hash 的字段值
            String value = JSON.toJSONString(tray);
            // 将 Hash 的字段添加到 Redis 中
            redisTemplate.opsForHash().put(Constants.TRAY_HASHKEY, field, value);
        }
    }

}
