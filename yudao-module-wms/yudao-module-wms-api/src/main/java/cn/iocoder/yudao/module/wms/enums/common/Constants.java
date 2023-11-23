package cn.iocoder.yudao.module.wms.enums.common;

/**
 * @author jiangfeng
 * @date 2023/6/20
 */
public class Constants {
    //使用批次
    public final static int USE_BATCH = 2;

    public enum trayType {
        FREE,
        BUSY
    }

    public enum storageStatus {
        FREE,
        PREOCCUPY,
        OCCUPY
    }

    /**
     * 0静置中 1静置完成 2已发布调度任务 3已出库 4WCS调度异常
     */
    public enum restingTaskStatus {
        SETTING,
        OVET_TIME,
        PUBLISHED_SCHEDULING_TASK,
        OUTBOUND,
        ERROR
    }

    public enum trayLogType {
        //入库
        WAREHOUSING,
        //入库冲销
        WAREHOUSINGCANCEL,
        //出库
        OUTBOUND,
        //出库冲销
        OUTBOUNDCANCEL,
        //组盘
        GROUPTRAY,
        //拆盘
        REMOVETRAY,
        //拆盘冲销
        REMOVETRAYCANCEL,
        //空托盘入库
        EMPTYTRAYWAREHOUSING,
        //下料
        BLANKING
    }

    public enum barcodeStatus {
        NOTINSTOCK,
        INSTOCK,
        LEAVESTOCK
    }

    //扫码组盘
    public final static byte SCAN_AND_COMBINE_TRAY = 1;
    //条码表前缀
    public final static String BARCODE_TABLE_PREFIX = "wms_barcode_";
    //非特殊库位
    public final static int NOT_SPECIAL_STORAGE = 0;
    //扫码区域
    public final static String SCAN_AREA = "TL";
    //扫码库位
    public final static String SCAN_STORAGE = "TL-01";
    //成功
    public final static String SUCCESSS = "SUCCESSS";
    //失败
    public final static String ERROR = "ERROR";

    public static final String TRAY_HASHKEY = "tray:trayList";

    public static final String STORAGE_HASHKEY = "storage:storageList";

    public static final String TRAY_BARCODES_HASHKEY = "trayBarcode:trayBarcodeList";

    public enum dataSourceType {
        PDA("PDA"),
        WMS("WMS"),
        MES("MES"),
        WCS("WCS"),
        DEVICE("DEVICE");

        private String value;

        dataSourceType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
