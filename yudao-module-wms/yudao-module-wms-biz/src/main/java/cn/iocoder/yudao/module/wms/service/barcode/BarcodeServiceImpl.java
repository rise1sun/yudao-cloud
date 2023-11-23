package cn.iocoder.yudao.module.wms.service.barcode;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.module.wms.controller.admin.barcode.vo.BarcodeCreateReqVO;
import cn.iocoder.yudao.module.wms.controller.admin.barcode.vo.BarcodeExportReqVO;
import cn.iocoder.yudao.module.wms.controller.admin.barcode.vo.BarcodePageReqVO;
import cn.iocoder.yudao.module.wms.controller.admin.barcode.vo.BarcodeUpdateReqVO;
import cn.iocoder.yudao.module.wms.controller.admin.timetablename.vo.TimeTableNameBaseVO;
import cn.iocoder.yudao.module.wms.controller.admin.timetablename.vo.TimeTableNameCreateReqVO;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.WarehousingVO;
import cn.iocoder.yudao.module.wms.convert.barcode.BarcodeConvert;
import cn.iocoder.yudao.module.wms.dal.dataobject.barcode.BarcodeDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.timetablename.TimeTableNameDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.tray.TrayDO;
import cn.iocoder.yudao.module.wms.dal.dataobject.traylog.TrayLogDO;
import cn.iocoder.yudao.module.wms.dal.mysql.barcode.BarcodeMapper;
import cn.iocoder.yudao.module.wms.enums.api.barcode.dto.BatteryinfoDTO;
import cn.iocoder.yudao.module.wms.enums.common.Constants;
import cn.iocoder.yudao.module.wms.service.batch.BatchService;
import cn.iocoder.yudao.module.wms.service.timetablename.TimeTableNameService;
import cn.iocoder.yudao.module.wms.service.tray.TrayService;
import cn.iocoder.yudao.module.wms.service.traylog.TrayLogService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.date.DateUtil.date;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.BARCODE_NOT_EXISTS;

/**
 * 条码 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class BarcodeServiceImpl implements BarcodeService {

    @Resource
    private BarcodeMapper barcodeMapper;

    @Resource
    private TimeTableNameService timeTableNameService;

    @Resource
    private TrayService trayService;

    @Resource
    private BatchService batchService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private TrayLogService trayLogService;

    @Resource
    private AdminUserApi userApi;

    @Override
    public Long createBarcode(BarcodeCreateReqVO createReqVO) {
        // 插入 TODO 后续tableName从条码号中截取
        BarcodeDO barcode = BarcodeConvert.INSTANCE.convert(createReqVO);
        String currentYearMonth = getCurrentYearMonth();
        // 校验是否需要创建新表
        String tableName = Constants.BARCODE_TABLE_PREFIX + currentYearMonth;

        vaildAndCreateBarcodeTable(currentYearMonth, tableName);
        barcodeMapper.insert(barcode);
        // 返回
        return barcode.getId();
    }

    @Override
    public void updateBarcode(BarcodeUpdateReqVO updateReqVO) {
        // 校验存在
        //  validateBarcodeExists(updateReqVO.getId());
        // 更新
        BarcodeDO updateObj = BarcodeConvert.INSTANCE.convert(updateReqVO);
        Long createTime = updateReqVO.getCreateTime();
        setTableNameByDateLong(createTime);
        barcodeMapper.updateById(updateObj);
    }

    /**
     * 根据date设置tableName
     *
     * @param createTime
     */
    private static void setTableNameByDate(LocalDateTime createTime) {
        String yearMonth = DateUtil.format(createTime, "yyyyMM");
    }

    /**
     * 根据date设置tableName
     *
     * @param createTime
     */
    private static String setTableNameByDateLong(Long createTime) {
        Date date = DateUtil.date(createTime);
        String yearMonth = DateUtil.format(date, "yyyyMM");
        return yearMonth;
    }

    @Override
    public void deleteBarcode(Long id, Long createTime) {
        // 校验存在
        // validateBarcodeExists(id);
        setTableNameByDateLong(createTime);
        barcodeMapper.deleteById(id);
    }

    private void validateBarcodeExists(Long id) {
        if (barcodeMapper.selectById(id) == null) {
            throw exception(BARCODE_NOT_EXISTS);
        }
    }

    @Override
    public BarcodeDO getBarcode(Long id, Long createTime) {
        setTableNameByDateLong(createTime);
        BarcodeDO barcodeDO = barcodeMapper.selectById(id);
        return barcodeDO;
    }

    @Override
    public List<BarcodeDO> getBarcodeList(Collection<Long> ids) {
        return barcodeMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<BarcodeDO> getBarcodePage(BarcodePageReqVO pageReqVO) {
        Date[] createTime = pageReqVO.getCreateTime();
        int pageNum = pageReqVO.getPageNo();
        int pageSize = pageReqVO.getPageSize();
        List<String> tableNames = new ArrayList<>();
        if (createTime == null) {
            //默认查询最新一月的数据
            setTableName();
            PageResult<BarcodeDO> barcodeDOPageResult = barcodeMapper.selectPage(pageReqVO);
            return barcodeDOPageResult;
        }
        List<String> tableNameAllLists = getTableNameAllLists();
        for (Date date : createTime) {
            //校验日期
            String yearMonth = DateUtil.format(date, "yyyyMM");
            //Boolean validDate = validDate(date);
            String tableName = Constants.BARCODE_TABLE_PREFIX + yearMonth;
            if (tableNameAllLists.contains(tableName)) {
                tableNames.add(tableName);
            }
        }
        // 查询每个分表中满足条件的数据，并将结果合并到一个 List<BarcodeDO> 对象中
        List<BarcodeDO> resultList = new ArrayList<>();
        for (String tableName : tableNames) {
            PageResult<BarcodeDO> barcodeDOPageResult = barcodeMapper.selectPage(pageReqVO);
            resultList.addAll(barcodeDOPageResult.getList());
        }


        // 根据分页参数，从合并后的 List<BarcodeDO> 中截取对应页码的数据，并计算总记录数
        int total = resultList.size();
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(pageNum * pageSize, total);
        List<BarcodeDO> pageList = resultList.subList(fromIndex, toIndex);

        // 封装分页结果和总记录数到 PageResult<BarcodeDO> 对象中，并返回查询结果
        PageResult<BarcodeDO> pageResult = new PageResult<>();
        pageResult.setList(pageList);
        pageResult.setTotal((long) total);
        return pageResult;
    }

    private void setTableName() {
        TimeTableNameDO newestInfo = timeTableNameService.getNewestInfo();
    }

    private static String getCurrentYearMonth() {
        String yearMonth = DateUtil.format(date(), "yyyyMM");
        return yearMonth;
    }

    private List<String> getTableNameAllLists() {
        List<TimeTableNameDO> timeTableNameDOList = timeTableNameService.getTimeTableNameList();
        List<String> tableNameAllList = new ArrayList<>();
        timeTableNameDOList.stream().forEach(timeTableNameDO -> {
            tableNameAllList.add(timeTableNameDO.getTableName());
        });
        return tableNameAllList;
    }


    /**
     * 日期校验
     *
     * @param
     */
    private Boolean validDate(Date date) {
        //日期不能大于当前月份
        boolean dateGreaterThanCurrentMonth = isDateGreaterThanCurrentMonth(date);
        //日期维度不能太大 目前最多查6个月,后面看实际情况调小
        boolean dateWithinSixMonths = isDateWithinSixMonths(date);
        return dateGreaterThanCurrentMonth && dateWithinSixMonths;

    }

    public boolean isDateWithinSixMonths(Date date) {
        Date sixMonthsAgo = DateUtil.offsetMonth(new Date(), -6);
        return DateUtil.betweenMonth(date, sixMonthsAgo, true) <= 6;
    }

    public boolean isDateGreaterThanCurrentMonth(Date date) {
        Date currentMonth = DateUtil.beginOfMonth(new Date());
        return DateUtil.compare(date, currentMonth) > 0;
    }


    @Override
    public List<BarcodeDO> getBarcodeList(BarcodeExportReqVO exportReqVO) {
        return barcodeMapper.selectList(exportReqVO);
    }

    @Override
    public Long barcodeCreateByThirdParty(String tray, List<String> barcodeLists, Integer dataSource) {
        log.info("wms-接受到条码：{}, 数量：{}, 托盘号：{}", barcodeLists, barcodeLists.size(), tray);
        // 托盘信息校验
        TrayDO trayDo = getTaryDo(tray);
        validateTray(trayDo);

        Map<String, List<String>> groupedBarcodes = new HashMap<>();

        // 插入条码数据
        Map<String, List<BarcodeDO>> barcodeMap = new HashMap<>();
        List<BarcodeDO> barcodeDOList = new ArrayList<>();

        for (String barcode : barcodeLists) {
            if (barcode == null) {
                continue;
            }
            // validateBarcode(barcode);
            String prefix = barcode.substring(0, 6);
            // validateBarcode(prefix);
            // 获取该前缀对应的条码列表，如果列表不存在则创建一个新的空列表
            List<String> group = groupedBarcodes.getOrDefault(prefix, new ArrayList<>());
            group.add(barcode);
            groupedBarcodes.put(prefix, group);
        }


        Map<String, Object> resultMap = batchService.getBatchAndFormulaInfo();
        Integer insertNum = 0;
        Long batchId = (Long) resultMap.get("batchId");
        int formulaId = (int) resultMap.get("formulaId");
        int formulaItemId = (int) resultMap.get("formulaItemId");
        String batchName = (String) resultMap.get("batchName");
        String formulaName = (String) resultMap.get("formulaName");
        String formulaItemName = (String) resultMap.get("formulaItemName");
        String ds = Constants.dataSourceType.values()[dataSource].getValue();


        for (Map.Entry<String, List<String>> entry : groupedBarcodes.entrySet()) {
            String yearMonth = entry.getKey();
            String tableName = Constants.BARCODE_TABLE_PREFIX + yearMonth;
            //校验是否需要创建新的条码表
            Boolean vaildAndCreateBarcodeTable = vaildAndCreateBarcodeTable(yearMonth, tableName);
            List<String> barcodes = entry.getValue();

            for (String barcode : barcodes) {
                if (!vaildAndCreateBarcodeTable) {
                    validateBarcode(barcode);
                }
                BarcodeDO barcodeDO = new BarcodeDO()
                        .setBarcode(barcode)
                        .setTrayId(String.valueOf(trayDo.getId()))
                        .setBatchId(Math.toIntExact(batchId))
                        .setFormulaId(formulaId)
                        .setFormulaItemId(formulaItemId)
                        .setBatchName(batchName)
                        .setFormulaName(formulaName)
                        .setFormulaItemName(formulaItemName)
                        .setDataSource(ds)
                        .setBarcodeStatus((byte) Constants.barcodeStatus.INSTOCK.ordinal())
                        .setStorage(Constants.SCAN_AREA)
                        .setArea(Constants.SCAN_STORAGE);
                barcodeDOList.add(barcodeDO);
            }
            barcodeMap.put(tableName, barcodeDOList);
            log.info("wms-批量插入数据准备, tableName: {}, barcodeDOS: {}", tableName, barcodeDOList);
            insertNum = insertNum + batchInsertBarcodes(barcodeMap);
            log.info("wms-{}插入{}条数据：{}", tableName, insertNum);
        }
        //PDA扫描不进行组盘操作
        if (Constants.dataSourceType.PDA.getValue().equals(ds)) {
            return Long.valueOf(insertNum);
        }
        //组盘
        combineTray(tray);
        //新增托盘日志记录
        trayLogService.insertTaryLog(new TrayLogDO()
                //.setId(null)
                .setBarcodes(JSONUtil.toJsonStr(barcodeLists))
                .setBarcodeNumber(barcodeLists.size())
                .setTrayNo(tray)
                .setCaller(Constants.dataSourceType.DEVICE.getValue())
                .setCallee(Constants.dataSourceType.WMS.getValue())
                .setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName())
                .setRequestParameters(tray + "|" + barcodeLists)
                .setResponseResults(String.valueOf(insertNum))
                .setResponseResults(Constants.SUCCESSS)
                .setStartArea(Constants.SCAN_AREA)
                .setStartStorage(Constants.SCAN_STORAGE)
                .setType(Constants.SCAN_AND_COMBINE_TRAY));
        return Long.valueOf(insertNum);
    }

    /**
     * 校验条码规则 校验条码是否已经存在
     *
     * @param barcode
     */
    private Boolean validateBarcode(String barcode) {
        // 匹配6位数字
        String substring = barcode.substring(0, 6);
        String regex = "^\\d{6}$";
        if (!substring.matches(regex)) {
            log.error("条码规则不匹配,请检查扫入的条码号：{}", barcode);
            throw new ServiceException(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode()
                    , "条码规则不匹配,请检查扫入的条码号：" + barcode);
        }
        int year = Integer.parseInt(barcode.substring(0, 4));
        int month = Integer.parseInt(barcode.substring(4, 6));
        if (year < 1900 || year > 9999 || month < 1 || month > 12) {
            log.error("条码规则不匹配,请检查扫入的条码号：{}", barcode);
            throw new ServiceException(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(),
                    "条码规则不匹配,请检查扫入的条码号：" + barcode);
        }

        //校验条码是否存在

        BarcodeDO barcodeDO = barcodeMapper.selectOne(new LambdaQueryWrapperX<BarcodeDO>()
                .eqIfPresent(BarcodeDO::getBarcode, barcode));

        if (barcodeDO != null) {
            String trayId = barcodeDO.getTrayId();
            //未入库条码删除重新插入
            if (Constants.barcodeStatus.NOTINSTOCK.ordinal() == barcodeDO.getBarcodeStatus()) {
                barcodeMapper.deleteByBarcode(barcode, Constants.BARCODE_TABLE_PREFIX + substring);
                return true;
            }
            if (Constants.barcodeStatus.INSTOCK.ordinal() == barcodeDO.getBarcodeStatus()) {
                throw new ServiceException(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode()
                        , "条码：" + barcode + "已入车间库,不支持组盘！");
            }
            if (Constants.barcodeStatus.LEAVESTOCK.ordinal() == barcodeDO.getBarcodeStatus()) {
                throw new ServiceException(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode()
                        , "条码：" + barcode + "已完成车间库工艺,不支持组盘！");
            }
            if (!StrUtil.isBlankIfStr(trayId)) {
                CommonResult<AdminUserRespDTO> user = userApi.getUser(Long.valueOf(barcodeDO.getCreator()));
                AdminUserRespDTO data = user.getData();
                String nickname = data.getNickname();
                throw new ServiceException(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode()
                        , "条码：" + barcode + "已被"+nickname+"扫入无法再次扫入！");
            }
        }
        return barcodeDO == null;
    }


    private void combineTray(String tray) {
        //更新托盘信息
        LambdaUpdateWrapper<TrayDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TrayDO::getTrayNo, tray)
                .set(TrayDO::getStatus, Constants.trayType.BUSY.ordinal());

        trayService.update(updateWrapper);
        log.info("wms-托盘{}数据更新成功！", tray);

        //TODO:更新库位信息 封装入库出库通用方法
    }

    private Boolean vaildAndCreateBarcodeTable(String currentYearMonth, String tableName) {
        // 校验是否需要创建新表
        Boolean barcodeTableValid = createBarcodeTableValid(currentYearMonth);
        log.info("wms-分表：{}", barcodeTableValid);

        if (barcodeTableValid) {
            // 创建条码表
            int barcodeTable = createBarcodeTable(tableName);
            log.info("wms-创建成功, tableName：{}", tableName);
            // 记录创建记录
            Integer id = createTimeTableName(tableName, String.valueOf(currentYearMonth));
            log.info("wms-timeTableName记录插入成功, id：{}", id);
        }
        return barcodeTableValid;
    }

    private Integer createTimeTableName(String tableName, String currentYearMonth) {
        TimeTableNameBaseVO timeTableNameBaseVO = new TimeTableNameCreateReqVO()
                .setTableName(tableName)
                .setMonth(Integer.valueOf(currentYearMonth));
        return timeTableNameService.createTimeTableName((TimeTableNameCreateReqVO) timeTableNameBaseVO);
    }

    /**
     * 根据托盘号从redis获取托盘信息
     *
     * @param tray
     * @return
     */
    private TrayDO getTaryDo(String tray) {
        String value = (String) redisTemplate.opsForHash().get(Constants.TRAY_HASHKEY, tray);
        TrayDO trayDo = JSON.parseObject(value, TrayDO.class);
        return trayDo;
    }

    /**
     * 托盘校验
     *
     * @param tray
     */
    private void validateTray(TrayDO tray) {
        if (Constants.trayType.FREE.ordinal() != tray.getStatus()) {
            throw new RuntimeException("托盘使用中,请确认托盘号：" + tray.getTrayNo() + "是否正确,托盘当前状态:" + tray.getStatus());
        }
    }

    private int batchInsertBarcodes(Map<String, List<BarcodeDO>> barcodesMap) {
        int count = 0;
        for (Map.Entry<String, List<BarcodeDO>> entry : barcodesMap.entrySet()) {
            List<BarcodeDO> barcodeDOList = new ArrayList<>();
            String tableName = entry.getKey();
            List<BarcodeDO> subList = entry.getValue();
            barcodeDOList.addAll(subList);

            barcodeMapper.insertBatch(barcodeDOList);

            count += barcodeDOList.size();
        }
        return count;
    }


    private int createBarcodeTable(String tableName) {
        return barcodeMapper.createBarcodeTable(tableName);
    }

    @Override
    public Boolean createBarcodeTableValid(String currentMonth) {
        TimeTableNameDO newestTimeTableName = timeTableNameService.getTimeTableInfo(currentMonth);
        return newestTimeTableName == null;
    }

    @Override
    public Integer deleteBarcode(String barcode) {
        String tableName = Constants.BARCODE_TABLE_PREFIX + barcode.substring(0, 6);
        //校验条码是否已经入库

        BarcodeDO barcodeDO = barcodeMapper.selectOne(new LambdaQueryWrapperX<BarcodeDO>().eqIfPresent(BarcodeDO::getBarcode, barcode));
        if (barcodeDO == null) {
            return 1;
        }

        if (barcodeDO.getBarcodeStatus() == Constants.barcodeStatus.NOTINSTOCK.ordinal()) {
            return barcodeMapper.deleteByBarcode(barcode, tableName);
        }

        return 0;

    }

    @Override
    public Long barcodeCreateByPDA(String tray, List<String> barcodeLists, Integer channelIndex) {
        //PDA创建条码 每次扫一个 barcodeLists数量始终为1
        log.info("wms-接受到条码：{}, 数量：{}, 托盘号：{}", barcodeLists, barcodeLists.size(), tray);
        // 托盘信息
        TrayDO trayDo = trayService.getTrayDOByDB(tray);
        Map<String, List<String>> groupedBarcodes = new HashMap<>();
        // 插入条码数据
        Map<String, List<BarcodeDO>> barcodeMap = new HashMap<>();
        List<BarcodeDO> barcodeDOList = new ArrayList<>();
        for (String barcode : barcodeLists) {
            if (barcode == null) {
                continue;
            }
            String prefix = barcode.substring(0, 6);
            // 获取该前缀对应的条码列表，如果列表不存在则创建一个新的空列表
            List<String> group = groupedBarcodes.getOrDefault(prefix, new ArrayList<>());
            group.add(barcode);
            groupedBarcodes.put(prefix, group);
        }
        Map<String, Object> resultMap = batchService.getBatchAndFormulaInfo();
        Integer insertNum = 0;
        //TODO 初始条码的工艺节点信息是否需要初始化，等组完盘后在开始流转
        Long batchId = (Long) resultMap.get("batchId");
        int formulaId = (int) resultMap.get("formulaId");
       // int formulaItemId = (int) resultMap.get("formulaItemId");
        String batchName = (String) resultMap.get("batchName");
        String formulaName = (String) resultMap.get("formulaName");
       // String formulaItemName = (String) resultMap.get("formulaItemName");

        for (Map.Entry<String, List<String>> entry : groupedBarcodes.entrySet()) {
            String yearMonth = entry.getKey();
            String tableName = Constants.BARCODE_TABLE_PREFIX + yearMonth;
            //校验是否需要创建新的条码表
            Boolean vaildAndCreateBarcodeTable = vaildAndCreateBarcodeTable(yearMonth, tableName);
            List<String> barcodes = entry.getValue();

            for (String barcode : barcodes) {
                Boolean validateBarcode = false;
                if (!vaildAndCreateBarcodeTable) {
                    //校验条码是否存在
                    validateBarcode = validateBarcode(barcode);
                }
                //建表时插入数据不需要校验
                if (validateBarcode || vaildAndCreateBarcodeTable) {
                    BarcodeDO barcodeDO = new BarcodeDO()
                            .setBarcode(barcode)
                            .setTrayId(String.valueOf(trayDo.getId()))
                            .setBatchId(Math.toIntExact(batchId))
                            .setFormulaId(formulaId)
                          //  .setFormulaItemId(formulaItemId)
                            .setBatchName(batchName)
                            .setFormulaName(formulaName)
                          //  .setFormulaItemName(formulaItemName)
                            .setDataSource(Constants.dataSourceType.PDA.getValue())
                            .setBarcodeStatus((byte) Constants.barcodeStatus.NOTINSTOCK.ordinal())
                            .setStorage(Constants.SCAN_STORAGE)
                            .setArea(Constants.SCAN_AREA)
                            .setChannelIndex(channelIndex);
                    barcodeDOList.add(barcodeDO);
                    barcodeMap.put(tableName, barcodeDOList);
                }
            }
            log.info("wms-批量插入数据准备, tableName: {}, barcodeDOS: {}", tableName, barcodeDOList);
            insertNum = insertNum + batchInsertBarcodes(barcodeMap);
            log.info("wms-{}插入{}条数据：{}", tableName, insertNum);
            return Long.valueOf(insertNum);
        }
        return Long.valueOf(insertNum);
    }

    @Override
    public String getTableName(List<BatteryinfoDTO> barcodes) {
        String collect = barcodes.stream().map(batteryinfoDTO ->
                        batteryinfoDTO.getBatteriesBarcode().substring(0, 6))
                .distinct()
                .collect(Collectors.joining(","));
        return collect;
    }

    @Override
    public List<String> updateBarcodeByCode(WarehousingVO warehousingVO) {
        List<String> allBarcodes = new ArrayList<>();
        List<BatteryinfoDTO> barcodes = warehousingVO.getBarcodes();
        Map<String, List<String>> Barcodes = barcodes.stream()
                .map(BatteryinfoDTO::getBatteriesBarcode)
                .filter(barcode -> barcode != null)
                .collect(Collectors.groupingBy(barcode -> barcode.substring(0, 6), Collectors.toList()));

        for (Map.Entry<String, List<String>> entry : Barcodes.entrySet()) {
            String yearMonth = entry.getKey();
            String tableName = Constants.BARCODE_TABLE_PREFIX + yearMonth;
            List<String> barcodeList = entry.getValue();
            batchUpdateBarcodes(tableName, barcodeList, warehousingVO);
            allBarcodes.addAll(barcodeList);
        }
        return allBarcodes;
    }


    @Override
    public void updateBarcodeTrayInfoByCode(List<String> barcodes, Long trayid) {
        Map<String, List<String>> Barcodes = barcodes.stream().filter(barcode -> barcode != null)
                .collect(Collectors.groupingBy(barcode -> barcode.substring(0, 6), Collectors.toList()));
        for (Map.Entry<String, List<String>> entry : Barcodes.entrySet()) {
            String yearMonth = entry.getKey();
            String tableName = Constants.BARCODE_TABLE_PREFIX + yearMonth;
            List<String> barcodeList = entry.getValue();
            batchUpdateBarcodes(tableName, barcodeList, trayid);
        }
    }

    private void batchUpdateBarcodes(String tableName, List<String> barcodeList, Long trayid) {

        //条码信息在插入条码时已经录入这边只需要更新条码状态即可
        log.info("wms-barcode批量更新, tableName: {}, barcodeList: {}", tableName, barcodeList);
        LambdaUpdateWrapper<BarcodeDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(BarcodeDO::getBarcode, barcodeList);
        wrapper.set(BarcodeDO::getTrayId, trayid);
        int update = barcodeMapper.update(null, wrapper);

    }

    /**
     * 批量更新条码数据
     *
     * @param tableName
     * @param barcodeList
     */
    private void batchUpdateBarcodes(String tableName, List<String> barcodeList, WarehousingVO warehousingVO) {

        //条码信息在插入条码时已经录入这边只需要更新条码状态即可
        log.info("wms-barcode批量更新, tableName: {}, barcodeList: {}", tableName, barcodeList);
        LambdaUpdateWrapper<BarcodeDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(BarcodeDO::getBarcode, barcodeList);
        wrapper.set(BarcodeDO::getBarcodeStatus, warehousingVO.getBarcodeStatus());
        //更新条码托盘数据应该在托盘绑定逻辑中,为了减少一次批量更新 合拼到此处
        wrapper.set(BarcodeDO::getTrayId, warehousingVO.getTrayId());
        wrapper.set(BarcodeDO::getStorage, warehousingVO.getDevice());
        int update = barcodeMapper.update(null, wrapper);

    }

}
