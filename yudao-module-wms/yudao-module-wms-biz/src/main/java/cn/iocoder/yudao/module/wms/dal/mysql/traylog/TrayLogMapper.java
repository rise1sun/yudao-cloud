package cn.iocoder.yudao.module.wms.dal.mysql.traylog;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wms.controller.admin.traylog.vo.*;

/**
 * wms托盘日志记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface TrayLogMapper extends BaseMapperX<TrayLogDO> {

    default PageResult<TrayLogDO> selectPage(TrayLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TrayLogDO>()
                .eqIfPresent(TrayLogDO::getTrayNo, reqVO.getTrayNo())
                .eqIfPresent(TrayLogDO::getType, reqVO.getType())
                .eqIfPresent(TrayLogDO::getServiceType, reqVO.getServiceType())
                .eqIfPresent(TrayLogDO::getDispatchTaskNumber, reqVO.getDispatchTaskNumber())
                .eqIfPresent(TrayLogDO::getResult, reqVO.getResult())
                .eqIfPresent(TrayLogDO::getCaller, reqVO.getCaller())
                .eqIfPresent(TrayLogDO::getCallee, reqVO.getCallee())
                .likeIfPresent(TrayLogDO::getMethodName, reqVO.getMethodName())
                .eqIfPresent(TrayLogDO::getRequestParameters, reqVO.getRequestParameters())
                .eqIfPresent(TrayLogDO::getResponseResults, reqVO.getResponseResults())
                .eqIfPresent(TrayLogDO::getStartStorage, reqVO.getStartStorage())
                .eqIfPresent(TrayLogDO::getStartArea, reqVO.getStartArea())
                .eqIfPresent(TrayLogDO::getEndStorage, reqVO.getEndStorage())
                .eqIfPresent(TrayLogDO::getEndArea, reqVO.getEndArea())
                .eqIfPresent(TrayLogDO::getErrorInfo, reqVO.getErrorInfo())
                .eqIfPresent(TrayLogDO::getBarcodes, reqVO.getBarcodes())
                .eqIfPresent(TrayLogDO::getBarcodeNumber, reqVO.getBarcodeNumber())
                .betweenIfPresent(TrayLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrayLogDO::getId));
    }

    default List<TrayLogDO> selectList(TrayLogExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TrayLogDO>()
                .eqIfPresent(TrayLogDO::getTrayNo, reqVO.getTrayNo())
                .eqIfPresent(TrayLogDO::getType, reqVO.getType())
                .eqIfPresent(TrayLogDO::getServiceType, reqVO.getServiceType())
                .eqIfPresent(TrayLogDO::getDispatchTaskNumber, reqVO.getDispatchTaskNumber())
                .eqIfPresent(TrayLogDO::getResult, reqVO.getResult())
                .eqIfPresent(TrayLogDO::getCaller, reqVO.getCaller())
                .eqIfPresent(TrayLogDO::getCallee, reqVO.getCallee())
                .likeIfPresent(TrayLogDO::getMethodName, reqVO.getMethodName())
                .eqIfPresent(TrayLogDO::getRequestParameters, reqVO.getRequestParameters())
                .eqIfPresent(TrayLogDO::getResponseResults, reqVO.getResponseResults())
                .eqIfPresent(TrayLogDO::getStartStorage, reqVO.getStartStorage())
                .eqIfPresent(TrayLogDO::getStartArea, reqVO.getStartArea())
                .eqIfPresent(TrayLogDO::getEndStorage, reqVO.getEndStorage())
                .eqIfPresent(TrayLogDO::getEndArea, reqVO.getEndArea())
                .eqIfPresent(TrayLogDO::getErrorInfo, reqVO.getErrorInfo())
                .eqIfPresent(TrayLogDO::getBarcodes, reqVO.getBarcodes())
                .eqIfPresent(TrayLogDO::getBarcodeNumber, reqVO.getBarcodeNumber())
                .betweenIfPresent(TrayLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TrayLogDO::getId));
    }

    default TrayLogDO getTrayLogByTray(String tray){
        return selectOne(new LambdaQueryWrapperX<TrayLogDO>()
                .eqIfPresent(TrayLogDO::getTrayNo,tray)
                .orderByDesc(TrayLogDO::getCreateTime));
    };
}
