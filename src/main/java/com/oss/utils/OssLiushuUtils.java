package com.oss.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author luozuyi
 */
public class OssLiushuUtils {
    /**
     *  Endpoint以杭州为例，其它Region请按实际情况填写。
     */
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    /**
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，
     * 请登录 https://ram.console.aliyun.com 创建RAM账号。
     */
    private static String accessKeyId = "LTAIDM596DiJOhvw";
    private static String accessKeySecret  = "ARAj3tSod367D5b4kYbewrTQQzU4TI";
    private static String bucketNameUrl  = "https://liushu-book-pic.oss-cn-beijing.aliyuncs.com";

    /**
     * // 创建OSSClient实例。
     * @return
     */
    public static OSSClient getOssClient() {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }


    /**
     * // 创建存储空间。
     * @param bucketName
     * @return
     */
    public static Bucket createBucket(String bucketName){
        return getOssClient().createBucket(bucketName);
    }

    /**
     * // 关闭OSSClient。
     */
    public static void shutdown(OSSClient ossClient){
        ossClient.shutdown();
    }

//    public static void main(String[] args) {
//        createBucket("oss-my-test");
//    }
    public static PutObjectResult inputStream(String bucketName,String url,String objectName) throws IOException {
        OSSClient ossClient = getOssClient();
        InputStream inputStream = new URL(url).openStream();
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);
        shutdown(ossClient);
        return putObjectResult;
    }
    public static void setPrivate(String bucketName,String objectName) throws IOException {
        OSSClient ossClient = getOssClient();
        ossClient.setObjectAcl(bucketName, objectName, CannedAccessControlList.PublicRead);
    }
//    public static void main(String[] args) throws IOException {
//        String bucketName = "liushu-book-pic";
//        String objectName = "s29987422.jpg";
//        setPrivate(bucketName,objectName);
//    }
//    public static void main(String[] args) throws IOException {
//        String url = "https://img3.doubanio.com/view/subject/l/public/s29987422.jpg";
//        String bucketName = "liushu-book-pic";
//        String objectName = "s29987422.jpg";
//        PutObjectResult putObjectResult = inputStream(bucketName,url,objectName);
//    }

    public static String getBucketNameUrl() {
        return bucketNameUrl;
    }
}
