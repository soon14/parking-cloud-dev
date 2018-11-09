package com.yxytech.parkingcloud.app.controller;

import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RestController
public class UploadController extends BaseController{

    @Value("${parkingcloud.upload-path}")
    private String uploadPath;

    @Value("${parkingcloud.upload-context}")
    private String uploadContext;

    @PostMapping("/upload")
    public ApiResponse<Object> upload(@RequestParam("fileName") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return this.apiFail("上传的内容不能为空");
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件上传后的路径
        // 解决中文问题，liunx下中文路径，图片显示问题
        String datePath = DateFormatUtils.format(new Date(), "yyyy/MM/dd");
        datePath.replace("/", File.separator);
        String newfileName = datePath + File.separator + UUID.randomUUID() + suffixName;
        File dest = new File(uploadPath + File.separator + newfileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            return this.apiFail(e.getMessage());
        }

        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        String path = basePath + uploadContext + File.separator + newfileName;
        return this.apiSuccess(path);
    }
}
