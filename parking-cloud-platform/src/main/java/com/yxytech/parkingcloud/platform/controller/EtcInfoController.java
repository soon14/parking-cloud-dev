package com.yxytech.parkingcloud.platform.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.EtcVersion;
import com.yxytech.parkingcloud.core.utils.UploadFile;
import com.yxytech.parkingcloud.platform.form.EtcBlacklistFileUrlForm;
import com.yxytech.parkingcloud.platform.task.EtcFileAsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
@RestController
@RequestMapping("/etcInfo")
public class EtcInfoController extends BaseController {
    @Autowired
    private EtcFileAsyncTask asyncTask;

    @Value("${parkingcloud.upload-path}")
    private String uploadPath;

    @PostMapping("/etcNotify")
    public ApiResponse asyncNotifyEtcFile(@Valid @RequestBody EtcBlacklistFileUrlForm form, BindingResult br) throws BindException {
        validate(br);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");

        String filePath = uploadPath + "/etc_files/";
        File file = new File(filePath + dateFormat.format(date) + ".gz");

        if (! file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        Path target = file.toPath();

        try {
            URL website = new URL(form.getDownloadUrl());
            InputStream in = website.openStream();
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);

            asyncTask.parserFile(file.getAbsolutePath(), form.getVersion());
        } catch (IOException e) {
            return this.apiFail("下载文件失败!");
        } catch (Exception e) {
            return this.apiFail("文件格式错误!");
        }

        return this.apiSuccess(null);
    }

    @PostMapping("/upload")
    public ApiResponse uploadFile(@RequestParam("fileName") MultipartFile file) {
        // 向版本表中插入数据库
        UploadFile uploadFile = new UploadFile();
        String compressedFilePath = "";

        try {
            compressedFilePath = uploadFile.upload(file, this);
        } catch (Exception e) {
            return this.apiFail(500, e.getMessage());
        }

        try {
            asyncTask.parserFile(compressedFilePath);
        } catch (Exception e) {
            return this.apiFail(500, e.getMessage());
        }

        return this.apiSuccess("");
    }

    // 查询是否在ETC黑名单中
    @GetMapping("/exists")
    public ApiResponse isInEtcBlacklist(@RequestParam(value = "etc_number", defaultValue = "") String etcNumber) {
        IService service = asyncTask.getCurrentTableService();
        Map<String, String> search = new HashMap<>();

        search.put("etc_number", etcNumber);

        List result = service.selectByMap(search);

        if (result.size() == 0) {
            return this.apiSuccess("no");
        } else {
            return this.apiSuccess("yes");
        }
    }

    @PostMapping("/changeVersion")
    public ApiResponse rebuildCurrentVersion(@RequestParam(value = "version", defaultValue = "0") Integer version) {
        if (version == 0) {
            return this.apiFail(400, "Invalid version.");
        } else {
            Wrapper<EtcVersion> versionWrapper = new EntityWrapper<>();
            EtcVersion etcVersion = versionWrapper.eq("etc_version", version).getEntity();

            etcVersion.setIsValid(false);
            Boolean ret = etcVersion.updateAllColumnById();

            if (! ret) {
                return this.apiFail(400, "");
            }

            return this.apiSuccess("ok");
        }
    }
}
