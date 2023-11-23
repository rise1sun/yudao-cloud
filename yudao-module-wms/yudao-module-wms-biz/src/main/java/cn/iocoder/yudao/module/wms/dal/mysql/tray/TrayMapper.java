package cn.iocoder.yudao.module.wms.dal.mysql.tray;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.wms.controller.admin.tray.vo.TrayExportReqVO;
import cn.iocoder.yudao.module.wms.controller.admin.tray.vo.TrayPageReqVO;
import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 托盘 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrayMapper extends BaseMapperX<TrayDO> {

    default TrayDO selectByTrayNo(String trayNo) {
        return selectOne(TrayDO::getTrayNo, trayNo);
    }

    default PageResult<TrayDO> selectPage(TrayPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrayDO>()
                .eqIfPresent(TrayDO::getType, reqVO.getType())
                .eqIfPresent(TrayDO::getMaxBindNumber, reqVO.getMaxBindNumber())
                .eqIfPresent(TrayDO::getMaxUseNumber, reqVO.getMaxUseNumber())
                .eqIfPresent(TrayDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrayDO::getIsTestTray,reqVO.getIsTestTray())
                .betweenIfPresent(TrayDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TrayDO::getTrayNo, reqVO.getTrayNo())
                .orderByDesc(TrayDO::getId));
    }

    default List<TrayDO> selectList(TrayExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TrayDO>()
                .eqIfPresent(TrayDO::getType, reqVO.getType())
                .eqIfPresent(TrayDO::getMaxBindNumber, reqVO.getMaxBindNumber())
                .eqIfPresent(TrayDO::getMaxUseNumber, reqVO.getMaxUseNumber())
                .eqIfPresent(TrayDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TrayDO::getIsTestTray,reqVO.getIsTestTray())
                .betweenIfPresent(TrayDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TrayDO::getTrayNo, reqVO.getTrayNo())
                .orderByDesc(TrayDO::getId));
    }

}
