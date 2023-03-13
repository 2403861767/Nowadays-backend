package com.seeleaf.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.seeleaf.config.OssUtil;
import com.seeleaf.service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Transactional
public class OssServiceImpl implements OssService {

    @Override
    @Transactional
    public String uploadFile(MultipartFile file) {
        String url = null;
        String endpoint = OssUtil.END_POINT;
        String accessKeyId = OssUtil.ACCESS_KEY_ID;
        String accessKeySecret = OssUtil.ACCESS_KEY_SECRET;
        String bucketName = OssUtil.BUCKET_NAME;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = file.getInputStream();
            //获取上传的文件的名字
            String filename = file.getOriginalFilename();
            //随机uuid是为了拼接文件名，防止用户上传两个名字相同的文件后覆盖掉前一个
            UUID uuid = UUID.randomUUID();
            //这里是为了按上传时间分配目录。精确到月
            String s = DateTime.now().toString("yyyy/MM/");
            //拼接成完整的文件名。
            filename = s + uuid + filename;
            //传入三个参数
            ossClient.putObject(bucketName, filename, inputStream);
            String middle = endpoint.replaceAll("^h.*/","");
            //拼接url
            url = "https://" + bucketName + "." + middle + "/" + filename;

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }
        return url;
    }

}
