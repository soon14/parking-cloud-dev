package com.yxytech.parkingcloud.core.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;

@Component
public class UploadFile<T> {

    private String uploadPath = null;

    private final static String defaultPath = "/var/data/parkingCloud";

    public String upload(MultipartFile file, T controller) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传的内容不能为空");
        }

        Field uploadPathField = null;

        try {
            uploadPathField = controller.getClass().getDeclaredField("uploadPath");
            uploadPathField.setAccessible(true);

            try {
                uploadPath = (String) uploadPathField.get(controller);
            } catch (IllegalAccessException e) {
                uploadPath = defaultPath;
            }
        } catch (NoSuchFieldException e) {
            uploadPath = defaultPath;
        }

        if (uploadPath == null) {
            uploadPath = defaultPath;
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件上传后的路径
        // 解决中文问题，liunx下中文路径，图片显示问题
        String datePath = DateFormatUtils.format(new Date(), "yyyy/MM/dd");
        datePath = datePath.replace("/", File.separator);
        String newFileName = datePath + File.separator + UUID.randomUUID() + suffixName;
        File dest = new File(uploadPath + File.separator + newFileName);

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            Boolean ret = dest.getParentFile().mkdirs();

            if (! ret) {
                throw new IllegalArgumentException("目录创建失败");
            }
        }

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return uploadPath + File.separator + newFileName;
    }
}
