package com.jshy.minio.test;


import com.jshy.file.service.FileStorageService;
import com.jshy.minio.MinIOApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest(classes = MinIOApplication.class)
@RunWith(SpringRunner.class)
public class MinIOTest {

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void testUpdateImgFile() {
        fileStorageService.delete("http://110.40.221.30:9001/jshy/2023/09/08/400.jpg");
        //        try {
//            //上传，注意分清楚是图片还是html
////            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\镜水皓月\\Pictures\\临时\\R.jpg");
////            String filePath = fileStorageService.uploadImgFile("", "400.jpg", fileInputStream);
//            System.out.println(filePath);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
//package com.jshy.minio.test;
//
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//
//import java.io.FileInputStream;
//
//public class MinIOTest {
//
//
//    public static void main(String[] args) {
//
//        FileInputStream fileInputStream = null;
//        try {
//
//            fileInputStream =  new FileInputStream("D:\\404.html");;
//
//            //1.创建minio链接客户端
//            MinioClient minioClient = MinioClient.builder().credentials("kJco4nTSVc6ztCDAoJGJ", "q1jEyK3kD6yU0nrrooGxkUFj45ZqaIZ442PSJYYu").endpoint("http://110.40.221.30:9001").build();
//            //2.上传
//            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
//                    .object("404.html")//文件名
//                    .contentType("text/html")//文件类型
//                    .bucket("jshy")//桶名词  与minio创建的名词一致
//                    .stream(fileInputStream, fileInputStream.available(), -1) //文件流
//                    .build();
//            minioClient.putObject(putObjectArgs);
//
//            System.out.println("http://110.40.221.30:9001/jshy/404.html");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//}