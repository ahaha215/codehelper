package com.lt.codehelper.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
/**
 * @ClassName OssUtils
 * @Description Oss工具类
 * @Author lt
 * @Version 1.0
 **/
public class OssUtils {

    /**
     * @return java.lang.String
     * @MethodName upload
     * @Description Oss文件上传
     * @Author lt
     * @Param [multipartFile, objectName]
     **/
    public static String upload(MultipartFile multipartFile, String objectName) throws Exception {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tMHy7eQqJcSyjEdqq9V";
        String accessKeySecret = "61qSL0KDVhIlbeRAbF4HGkF2Vi6Jli";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "codehelper-bucket";

        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。

        OSS ossClient = null;

        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            InputStream inputStream = multipartFile.getInputStream();
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        // 返回文件上传地址  ---- 》》》》 https://codehelper-bucket.oss-cn-beijing.aliyuncs.com/user/head-portrait/951465835371692032-1425192535736.jpg
        return "https://" + bucketName + "." + endpoint + "/" + objectName;
    }

    /**
     * @MethodName delete
     * @Description Oss删除文件
     * @Author lt
     * @Param [objectName]
     * @return void
     **/
    public static void delete(String objectName) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tMHy7eQqJcSyjEdqq9V";
        String accessKeySecret = "61qSL0KDVhIlbeRAbF4HGkF2Vi6Jli";
        // 填写Bucket名称。
        String bucketName = "codehelper-bucket";
        // 填写文件完整路径。文件完整路径中不能包含Bucket名称。

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件或目录。如果要删除目录，目录必须为空。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}