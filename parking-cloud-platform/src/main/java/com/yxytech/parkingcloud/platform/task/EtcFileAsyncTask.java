package com.yxytech.parkingcloud.platform.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.EtcHistory;
import com.yxytech.parkingcloud.core.entity.EtcInfo;
import com.yxytech.parkingcloud.core.entity.EtcInfoBak;
import com.yxytech.parkingcloud.core.entity.EtcVersion;
import com.yxytech.parkingcloud.core.service.IEtcHistoryService;
import com.yxytech.parkingcloud.core.service.IEtcInfoBakService;
import com.yxytech.parkingcloud.core.service.IEtcInfoService;
import com.yxytech.parkingcloud.core.service.IEtcVersionService;
import com.yxytech.parkingcloud.core.service.impl.EtcInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;



@Component
public class EtcFileAsyncTask {
    @Autowired
    private IEtcVersionService etcVersionService;

    @Autowired
    private IEtcHistoryService etcHistoryService;

    @Autowired
    private IEtcInfoService etcInfoService;

    @Autowired
    private IEtcInfoBakService etcInfoBakService;

    @Value("${parkingcloud.upload-path}")
    private String uploadPath;

    private static final Integer batchTimes = 10240;

    // 解压文件
    private String GZipUnArchive(String filepath) throws Exception {
        if (! filepath.endsWith(".gz")) {
            throw new Exception("Invalid file type.");
        } else {
            GZIPInputStream inputStream = null;
            File file = new File(filepath);
            String outFileName = "/tmp/" + file.getName().replace(".gz", "");

            try {
                inputStream = new GZIPInputStream(new FileInputStream(filepath));
            } catch (FileNotFoundException e) {
                throw new Exception("File not found.");
            }

            FileOutputStream outputStream = null;

            try {
                outputStream = new FileOutputStream(outFileName);
            } catch (FileNotFoundException e) {
                throw new Exception("File not found.");
            }

            byte[] buff = new byte[1024];
            int length;

            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }

            inputStream.close();
            outputStream.close();

            return outFileName;
        }
    }

    @Async
    public void parserFile(String filepath, Integer etcVersion) throws Exception {
        filepath = this.GZipUnArchive(filepath);

        EtcVersion version = this.getVersion(filepath);
        version.setVersion(etcVersion);

        if (! version.getValid()) {
            return;
        }

        this.insertEtcInfo(filepath, version);
        this.insertEtcHistory(filepath, version);
    }

    // 判断版本，并进行写库的操作
    @Async
    public void parserFile(String filepath) throws Exception {
        filepath = this.GZipUnArchive(filepath);

        EtcVersion version = this.getVersion(filepath);

        if (! version.getValid()) {
            return;
        }

        this.insertEtcInfo(filepath, version);
        this.insertEtcHistory(filepath, version);
    }

    /**
     * 历史表
     *
     * @param filepath
     * @param version
     * @throws Exception
     */
    @Async
    public void insertEtcHistory(String filepath, EtcVersion version) throws Exception {
        // 读取文件开始
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(filepath));
        } catch (FileNotFoundException e) {
            throw new Exception("File not found.");
        }

        // 每次读取10M
        BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);
        List<EtcHistory> etcHistoryList = new ArrayList<>();

        while (in.ready()) {
            String line = in.readLine().trim();

            // 判断是否符合格式
            if (line.length() == 21) {
                String etcNetId = line.substring(1, 5);
                String etcNumber = line.substring(1);

                EtcHistory etcHistory = new EtcHistory();

                etcHistory.setEtcNetId(etcNetId);
                etcHistory.setEtcNumber(etcNumber);
                etcHistory.setVersion(version.getVersion());

                etcHistoryList.add(etcHistory);

                if (etcHistoryList.size() == batchTimes) {
                    etcHistoryService.insertOrUpdateBatch(etcHistoryList);

                    etcHistoryList.clear();
                }
            }
        }

        if (etcHistoryList.size() != 0) {
            etcHistoryService.insertOrUpdateBatch(etcHistoryList);

            etcHistoryList.clear();
        }

        in.close();

        // 更新启用时间
        this.startCurrentVersion(version);
    }

    @Async
    public void insertEtcInfo(String filepath, EtcVersion version) throws Exception {
        // 那个表
        IService service = this.getNextTableService();
        if (service.getClass().isInstance(EtcInfoServiceImpl.class)) {
            Wrapper<EtcInfo> wrapper = new EntityWrapper<>();
            wrapper.gt("version", 0);
            service.delete(wrapper);
        } else {
            Wrapper<EtcInfoBak> wrapper = new EntityWrapper<>();
            wrapper.gt("version", 0);
            service.delete(wrapper);
        }

        // 读取文件开始
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(filepath));
        } catch (FileNotFoundException e) {
            throw new Exception("File not found.");
        }

        // 每次读取10M
        BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);
        List<EtcInfo> etcInfoList = new ArrayList<>();

        while (in.ready()) {
            String line = in.readLine().trim();

            // 判断是否符合格式
            if (line.length() == 21) {
                String etcNetId = line.substring(1, 5);
                String etcNumber = line.substring(1);

                EtcInfo info = new EtcInfo();

                info.setEtcNumber(etcNumber);
                info.setEtcNetId(etcNetId);
                info.setVersion(version.getVersion());

                etcInfoList.add(info);

                if (etcInfoList.size() == batchTimes) {
                    service.insertBatch(etcInfoList, batchTimes);

                    etcInfoList.clear();
                }
            }
        }

        if (etcInfoList.size() != 0) {
            service.insertBatch(etcInfoList, batchTimes);

            etcInfoList.clear();
        }

        in.close();

        // 记录更新结束时间
        Date date = new Date();
        version.setUpdatedAt(date);

        etcVersionService.updateAllColumnById(version);
    }

    /**
     * 获取当前使用的表名
     * @return
     */
    private String getCurrentTableName() {
        return etcVersionService.getValidTableName();
    }

    private IService getNextTableService() {
        String tableName = this.getCurrentTableName();

        if (tableName == null) {
            return etcInfoService;
        } else {
            if (tableName.equals("yxy_etc_info")) {
                return etcInfoBakService;
            } else {
                return etcInfoService;
            }
        }
    }

    public IService getCurrentTableService() {
        String tableName = this.getCurrentTableName();

        if (tableName == null) {
            tableName = "yxy_etc_info";
        }

        if (tableName.equals("yxy_etc_info")) {
            return etcInfoService;
        } else {
            return etcInfoBakService;
        }
    }

    // 生成版本表
    private EtcVersion getVersion(String filename) throws Exception {
        Pattern pattern = Pattern.compile("(\\d{6}-\\d{2})");
        Matcher matcher = pattern.matcher(filename);
        String version = "";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");

        if (matcher.find()) {
            version =  matcher.group(0).replace("-", "");
        } else {
            version = format.format(date) + "10";
        }

        EtcVersion etcVersion = new EtcVersion();
        Integer maxVersion = etcVersionService.getMaxVersion();
        Boolean isValid = true;

        if (maxVersion != null) {
            isValid = maxVersion < Integer.valueOf(version);
        }

        String tableName = this.getCurrentTableName();

        if (tableName == null) {
            tableName = "yxy_etc_info";
        } else {
            if (tableName.equals("yxy_etc_info")) {
                tableName = "yxy_etc_info_bak";
            } else {
                tableName = "yxy_etc_info";
            }
        }

        etcVersion.setIsValid(isValid);
        etcVersion.setVersion(Integer.valueOf(version));
        etcVersion.setReceivedAt(date);
        etcVersion.setUpdatedAt(date);
        etcVersion.setStartedAt(null);
        etcVersion.setTableName(tableName);

        boolean ret = etcVersionService.insert(etcVersion);

        if (! ret) {
            throw new Exception("Create etc_version failed.");
        }

        return etcVersion;
    }

    // 更新启用时间
    private void startCurrentVersion(EtcVersion etcVersion) {
        Date date = new Date();

        etcVersion.setStartedAt(date);

        etcVersionService.updateAllColumnById(etcVersion);
    }
}
