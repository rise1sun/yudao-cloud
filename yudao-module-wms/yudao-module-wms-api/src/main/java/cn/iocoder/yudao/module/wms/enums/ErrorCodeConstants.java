package cn.iocoder.yudao.module.wms.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * wms 错误码枚举类
 *
 * wms 系统，使用 2-000-001-000 段
 */
public interface ErrorCodeConstants {

    //托盘
    ErrorCode TRAY_NOT_EXISTS = new ErrorCode(2000001000, "托盘不存在");
    ErrorCode TRAY_IMPORT_LIST_IS_EMPTY = new ErrorCode(2000001001, "导入托盘数据不能为空！");
    ErrorCode TRAY_TRAYNO_EXISTS = new ErrorCode(2000001002, "托盘号已经存在");
    ErrorCode TRAY_TRAYNO_IS_BLANK = new ErrorCode(2000001003, "托盘号为空");

    //库位
    ErrorCode STORAGE_NOT_EXISTS = new ErrorCode(2000002000, "库位不存在");

    //条码
    ErrorCode BARCODE_NOT_EXISTS = new ErrorCode(2000003000, "条码不存在");
    ErrorCode BARCODE_CREATE_ERROR = new ErrorCode(2000003001, "条码创建失败");

    //工艺流程
    ErrorCode FORMULA_NOT_EXISTS = new ErrorCode(2000004000, "工艺流程不存在");

    ErrorCode FORMULA_ITEM_NOT_EXISTS = new ErrorCode(2000005000, "工艺流程节点不存在");

    ErrorCode BATCH_NOT_EXISTS = new ErrorCode(2000006000, "批次不存在");

    ErrorCode TIME_TABLE_NAME_NOT_EXISTS = new ErrorCode(2000007000, "月份条码不存在");

    ErrorCode TRAY_LOG_NOT_EXISTS = new ErrorCode(2000008000, "wms托盘日志记录不存在");

    ErrorCode TASK_NOT_EXISTS = new ErrorCode(2000009000, "任务管理不存在");

    ErrorCode FORMULA_ORDER_TASK_NOT_EXISTS = new ErrorCode(2000010000, "工艺流程订单任务不存在");

    ErrorCode RESTING_TASK_NOT_EXISTS = new ErrorCode(2000011000, "静置任务信息不存在");

}
