package com.wjt.ieps.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.wjt.ieps.exception.IepsException;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class OssUtils {

    public static List<String> uploadFileAvatar(MultipartFile file,String type) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        ArrayList<String> list = new ArrayList<>();
        try {
            // 创建OSS实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            list.add(fileName);
            //2 把文件按照日期进行分类
            //获取当前日期
            //   2019/11/12
            String datePath = new DateTime().toString("yyyy");
            //拼接
            //  2019/11/12/ewtqr313401.jpg
            fileName = datePath+"/"+type+"/"+fileName;

            //调用oss方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName,fileName , inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //  https://edu-guli-1010.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            list.add(url);
            return list;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpServletResponse downOSSFile(String fileName, HttpServletResponse response) {
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        int m = fileName.indexOf('m');
        fileName = fileName.substring(m+2);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(bucketName, fileName);
            BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("zz.zip", "utf-8"));
            byte[] car = new byte[1024];
            int L = 0;
            while ((L = in.read(car)) != -1) {
                out.write(car, 0, L);

            }
            if (out != null) {
                out.flush();
                out.close();
            }
            if (in != null) {
                in.close();
            }

            ossClient.shutdown();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
//            while (true) {
//                String line = reader.readLine();
//                if (line == null) break;
//
//                System.out.println("\n" + line);
//            }
//            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
//            reader.close();
//            // ossObject对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
//            ossObject.close();

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (Throwable ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return response;
    }

    public static boolean deleteFile(String fileName){
        try {
            String endpoint = ConstantPropertiesUtils.END_POIND;
            String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
            String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 判断当前文件url 是否存在
            boolean exist = ossClient.doesObjectExist(bucketName, fileName);
            if (!exist) {
                return false;
            } else {
                // 删除文件。
                ossClient.deleteObject(bucketName, fileName);
                // 关闭OSSClient。
                ossClient.shutdown();
                return true;
            }
        } catch (Exception e) {
            throw new IepsException(20001,"删除文件异常");
        }
    }
}
