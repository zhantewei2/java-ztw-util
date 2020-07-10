package org.cm.pro.util;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.PutObjectResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;

/**
 * @author hht
 * @description 百度存储桶utils
 * @date 2019/12/11
 */

@Slf4j
public class BosClientUtils {

    public static String uploadObject(BosClient  bosClient, String bucketName, String key, File file) {
        PutObjectResponse response =  bosClient.putObject(bucketName, key, file);
        return response.getETag(); // 文件标识
    }

    public static String uploadObject(BosClient  bosClient, String bucketName, String key, byte[] value) {
        PutObjectResponse response = null;
        try {
            bosClient.putObject(bucketName, key, value);
        } finally {
            bosClient.shutdown();
        }
        return response.getETag();
    }

    public static String uploadObject(BosClient  bosClient, String bucketName, String key, InputStream input) {

        PutObjectResponse response = null;
        try {
            bosClient.putObject(bucketName, key, input);
        } finally {
            bosClient.shutdown();
        }
        return response.getETag();
    }

}
