package cn.iocoder.yudao.module.wms.service.tray;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.wms.controller.admin.tray.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import cn.iocoder.yudao.module.wms.enums.api.tray.dto.TrayDTO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 托盘 Service 接口
 *
 * @author 管理员
 */
public interface TrayService {

    /**
     * 创建托盘
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTray(@Valid TrayCreateReqVO createReqVO);

    /**
     * 更新托盘
     *
     * @param updateReqVO 更新信息
     */
    void updateTray(@Valid TrayUpdateReqVO updateReqVO);

    /**
     * 删除托盘
     *
     * @param id 编号
     */
    void deleteTray(Long id);

    /**
     * 获得托盘
     *
     * @param id 编号
     * @return 托盘
     */
    TrayDO getTray(Long id);

    /**
     * 获得托盘列表
     *
     * @param ids 编号
     * @return 托盘列表
     */
    List<TrayDO> getTrayList(Collection<Long> ids);

    /**
     * 获得托盘分页
     *
     * @param pageReqVO 分页查询
     * @return 托盘分页
     */
    PageResult<TrayDO> getTrayPage(TrayPageReqVO pageReqVO);

    /**
     * 获得托盘列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 托盘列表
     */
    List<TrayDO> getTrayList(TrayExportReqVO exportReqVO);

    /**
     * 获得托盘列表,无参
     * @return
     */
    List<TrayDO> getTrayList();

    TrayImportRespVO importTrayList(List<TrayImportExcelVO> list, Boolean updateSupport);

    void update(LambdaUpdateWrapper<TrayDO> updateWrapper);

    /**
     * 托盘占用
     *
     * @param tray
     * @param startStorage
     */
    void trayOccupy(TrayDO tray, String startStorage);

    /**
     * 托盘释放
     * @param tray
     */
    void taryRelease(String tray);

    /**
     * 托盘校验
     *
     * @param tray
     * @return
     */
    Map<Boolean, String> checkTray(String tray);

    /**
     * 通用获取托盘信息
     * @param trayNO
     * @return
     */
    TrayDO getTrayDO(String trayNO);

    TrayDO getTrayDOByDB(String trayNO);

    String getRedisTrayInfo(String tray);

    void updateRedisTrayInfo(TrayDO trayDO);

}
