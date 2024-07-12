package com.elk.springbootelk.controller;

import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileTest extends JFrame {

    static int[] average1 = new int[65];
    static int[] average2 = new int[65];


    /**
     * 思路：
     * 1.先把文件夹内文件路径存到list，
     * 2.从list取第一张和剩余全部照片进行对比，相似度100，存入list
     * 3.在从list取出，获取像素相加最大的那张，删除不合格的
     * Files.delete(dir);删除文件
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {

        String  srt4 = "";

        for(int j=0;j<10000;j++){
            System.out.println(srt4+"................");
            if ((srt4 != null && srt4.length() != 0)){
                String startPath = srt4;
                String endPath = "G:\\zhuan\\qingsebak\\";
                File startFile = new File(startPath);
                File tmpFile = new File(endPath);//获取文件夹路径
                if(!tmpFile.exists()){//判断文件夹是否创建，没有创建则创建新文件夹
                    tmpFile.mkdirs();
                }
                System.out.println(endPath + startFile.getName());
                if (startFile.renameTo(new File(endPath + startFile.getName()))) {
                    System.out.println("File is moved successful!");
                } else {
                    System.out.println("File is failed to move!");
                }
            }
            //1.先遍历文件里获取全部文件路径到list
            File dir = new File("G:\\zhuan\\qingse");

            List list = listAllFile(dir);
            //2.取第一张照片和剩余照片进行对比，返回第一张照片路径以及相似度100照片路径
            // 第一张图片路径
            Object o = list.get(0);
            String srt = o.toString();
            List list1  =new ArrayList<>();
            list1.add(srt);
            // 遍历从第二张开始
            for(int i = 1;i < 10; i ++){
                // 找出找出图片相同的放到list
                /**
                 * srt  第一张照片
                 * srt2 第二张照片
                 */
                String srt2 = list.get(i).toString();
                //  存放找到的相同的照片路径；
                if (ishuai(srt)&&ishuai(srt2)){
                    int[][] readImagePixArray1 = readImagePix (srt);
                    System.out.println("srt2:"+srt2);
                    int[][] readImagePixArray2 = readImagePix (srt2);
                    zhiwen(true ,readImagePixArray1);
                    zhiwen(false ,readImagePixArray2);
                }else {
                    break;
                }
                //返回true表示相似度很高，获取传入的路径，并添加到list
                boolean xiangsidu = xiangsidu();
                if (xiangsidu){
                    System.out.println("srt2路径为:"+srt2);
                    list1.add(srt2);
                }
            }
            for(int i = 0;i < list1.size(); i ++){
                //  获取图片像素，相加，保留最大，删除其他
                System.out.println("相同照片路径:"+list1.get(i));
                //路径传进去得到相加的值
                // 先获取第一张路径的值
                int one =0;
                String string = list1.get(0).toString();
                if (string != null && string.length() != 0){
                    one = all(string);
                }
                String srr = list1.get(0).toString();
                // 获取第二张值和路径
                int two ;
                two = all(list1.get(i).toString());
                String sre = list1.get(i).toString();
                if (one > two){
                    // 如果第一张大于第二张 就删除第二张
                    System.out.println("第一张比第二张大");
                    File file = new File(sre);
                    file.delete();//删除第二张
//                //  如果是最后一张照片 推出
                    if (i==list1.size()){
                        return;
                    }
                }else if(one <  two) {
                    System.out.println("第二张比第一张大");
                    // 如果第二张大于第一张 就删除第一张
                    File file = new File(srr);
                    file.delete();
//                //替换引用
                    one = two;
                    srr = sre;
                    if (i==list1.size()){
                        return;
                    }
                }else if (one == two){
                    // 如果值相同删除第二张
                    File file = new File(sre);
                    //判断路径是否相同
                    if (!sre.equals(srr)){
                        file.delete();
                    }
                    srt4 = srr;
                }else {
                    System.out.println("// 到这里表示没有可以对比的照片了");
                    // 把第一张照片复制走
                    System.out.println("留下来的照片:"+srr);
                    srt4 = srr;


                }

            }

        }



    }

    /**
     * 获取文件夹全部文件路径
     * @param f 文件夹路径
     * @return
     */
    public  static List listAllFile(File f){
        File[]  files  = f.listFiles();
        List list = new ArrayList();
        for (File file : files) {
//            System.out.println("listAllFile方法:"+file);
            list.add(file);

        }
        return list;
    }
    //删除指定路径文件
    public  static  void  delete(File file){
        file.delete();
    }
    public static int[][] readImagePix(String path){
        // 文件对象
        File file = new File (path);
        BufferedImage readimg = null;
        try {
            readimg = ImageIO.read (file);
            int width = readimg.getWidth();
            int height = readimg.getHeight();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        int width = readimg.getWidth ();
        int height = readimg.getHeight ();
        int[][] imgArray = new int[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                imgArray[i][j] = readimg.getRGB (i, j);
            }
        }
        return imgArray;
    }




    public static void zhiwen(Boolean b, int[][] imgArray){
        int width = 8;
        int height = 8;
        int a1 = imgArray.length/width;
        int b1 = imgArray[0].length/height;
        int a2 = imgArray.length - a1 * (width-1);
        int b2 = imgArray[0].length - b1 * (height-1);
        int[][] average = new int[width][height];
        int sum0 = 0;

        for(int m = 0; m < width; m++){
            for(int n = 0; n < height; n++){
                int sum = 0;
                if(m != (width-1) && n != (height-1)) {
                    for(int i = 0 + m*a1; i < a1 + m*a1; i++){
                        for(int j = 0 + n*b1; j < b1 + n*b1; j++){
                            int num1 = imgArray[i][j];
                            int gray1 = getRgbGray (num1);
                            sum += gray1;
                        }
                    }
                    if (a1!=0 && b1!=0){
                        average[m][n] = sum/(a1 * b1);
                    }


                }

                if(m == (width-1) && n != (height-1)) {
                    for(int i = 0 + m*a1; i < a2 + m*a1; i++){
                        for(int j = 0 + n*b1; j < b1 + n*b1; j++){
                            int num1 = imgArray[i][j];
                            int gray1 = getRgbGray (num1);
                            sum += gray1;
                        }
                    }
                    if (a2!=0 && b1!=0) {
                        average[m][n] = sum/(a2 * b1);
                    }


                }

                if(m != (width-1) && n == (height-1)) {
                    for(int i = 0 + m*a1; i < a1 + m*a1; i++){
                        for(int j = 0 + n*b1; j < b2 + n*b1; j++){
                            int num1 = imgArray[i][j];
                            int gray1 = getRgbGray (num1);
                            sum += gray1;
                        }
                    }
                    if (a1!=0 && b2!=0){
                        average[m][n] = sum/(a1 * b2);
                    }


                }

                if(m == (width-1) && n == (height-1)) {
                    for(int i = 0 + m*a1; i < a2 + m*a1; i++){
                        for(int j = 0 + n*b1; j < b2 + n*b1; j++){
                            int num1 = imgArray[i][j];
                            int gray1 = getRgbGray (num1);
                            sum += gray1;
                        }
                    }
                    if (a2!=0 && b2!=0){
                        average[m][n] = sum/(a2 * b2);
                    }


                }

                sum0 += average[m][n];

            }
        }

        int average0 = sum0/(width*height);

        for(int n = 0; n < height; n++){
            for(int m = 0; m < width; m++){
                if(average[m][n] > average0)average[m][n] = 1;
                else average[m][n] = 0;

                if(b) {

                    average1[m+width*n] = average[m][n];
                }else {

                    average2[m+width*n] = average[m][n];
                }

//                System.out.print(average[m][n]);

            }
        }

    }

    public static boolean xiangsidu(){
        double a=0;
        for(int i=0;i<64;i++) {
            if(average1[i] == average2[i])a=a+1.5625;
        }

        System.out.println(" ");
        System.out.print("两张图像的相似度为："+a);
        System.out.println(" ");
        if (a >=90.0){
            return true;
        }else{
            return false;
        }
    }

    public static int getRgbGray(int numPixels){
        // byte -128 127
        int red = (numPixels>>16)&0xFF;
        int green = (numPixels>>8)&255;
        int blue = (numPixels>>0)&255;
        // 灰度 -- 减少计算量 以及 更方便计算
        int gray = (red + green + blue) / 3;
        return gray;
    }

    public  static  int all(String path){
        File file = new File (path);
        BufferedImage readimg = null;
        try {
            if (file.exists() && file.length() == 0){
                readimg = ImageIO.read (file);
                int width = readimg.getWidth();
                int height = readimg.getHeight();
                return  readimg.getWidth()+readimg.getHeight();
            }

        } catch (IOException e) {
            e.printStackTrace ();
        }
        return 0;

    }

    public static  boolean ishuai(String path) throws IOException {
        //判断该路径文件是否损坏
        File f = new File(path);
        FileInputStream fi = new FileInputStream(f);
        try {
            BufferedImage sourceImg = ImageIO.read(fi);//判断图片是否损坏
            int picWidth = sourceImg.getWidth(); //确保图片是正确的（正确的图片可以取得宽度）
        } catch (Exception e) {
            // TODO: handle exception
            fi.close();//关闭IO流才能操作图片
            f.delete();
            return false;
        } finally {
            fi.close();//关闭IO流才能操作图片
        }
        return true;
    }
}
