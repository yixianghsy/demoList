package com.elk.springbootelk;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author:wangbl
 * Date:2022/2/8
 * Time:13:45
 * Description:使用字节流实现复制图片功能
 **/
public class CopyPictureTest {
    public static void main(String[] args) {
        //图片资源
        FileInputStream fileInputStream = null;
        //需要粘贴的图片资源
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream("E:\\Temp\\photo\\激情\\42259.jpg");
            fileOutputStream = new FileOutputStream("E:\\Temp\\photo\\bak\\c.jpg");
            //定义一个字节数组，用来充当缓存区
            byte[] bytes = new byte[1024];
            int length = 0;
            //通过while循环将读取的字节写入到字节数组中，如果== -1则表示已经读取完毕
            while ((length = fileInputStream.read(bytes)) != -1){
                //将字节数组写入文件
                fileOutputStream.write(bytes,0,length);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭输入流和输出流
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }
}
