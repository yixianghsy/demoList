package com.elk.springbootelk;

import java.io.*;

public class CopyTest{
    private static void func(File file) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory())    //若是目录，则递归打印该目录下的文件
                func(f);
            if (f.isFile())        //若是文件，直接打印

            //传入路径和名称
            CopyPicture(String.valueOf(f),f.getName());
        }
    }

    /**
     * 复制图片
     * f 图片路径
     */
    public static  void CopyPicture(String f ,String fileNmae){
        System.out.println("图片路径:"+f);
        System.out.println("图片名称:"+fileNmae);
        //图片资源
        FileInputStream fileInputStream = null;
        //需要粘贴的图片资源
        FileOutputStream fileOutputStream = null;

//        String fName = String.valueOf(f);
//        String fileName = fName.substring(fName.lastIndexOf("\\")+1);
//        System.out.println("方法二：fileName = " + fileName);

        try {
            fileInputStream = new FileInputStream(f);
            fileOutputStream = new FileOutputStream("E:\\Temp\\photo\\激情copy\\"+fileNmae);
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
    public static void main(String[] args) {
        String inputPath = "E:\\Temp\\photo\\激情";;		//要遍历的路径
        File file = new File(inputPath);		//获取其file对象
        func(file);
    }
}

