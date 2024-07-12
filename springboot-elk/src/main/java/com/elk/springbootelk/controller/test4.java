package com.elk.springbootelk.controller;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName test4
 * @Author dell-pc
 * @create 2023/3/8 9:49  54.6875 59.375
 * 以这个为准，
 */
public class test4 extends JFrame {
    static int[] average1 = new int[65];
    static int[] average2 = new int[65];

    public static void main(String[] args) {

        int[][] readImagePixArray1 = readImagePix ("E:\\Temp\\list\\FILE14765.JPG");
        int[][]readImagePixArray2 = readImagePix ( "E:\\Temp\\list\\FILE14766.JPG");
        zhiwen(true ,readImagePixArray1);
        System.out.println(" ");
        zhiwen(false ,readImagePixArray2);
        xiangsidu();
    }


    public static int[][] readImagePix(String path){
        // 文件对象
        File file = new File (path);
        BufferedImage readimg = null;
        try {
            readimg = ImageIO.read (file);
            int width = readimg.getWidth();
            int height = readimg.getHeight();
            System.out.println("Image width: " + width);
            System.out.println("Image height: " + height);
            System.out.println("合集"+width+height);
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
                    average[m][n] = sum/(a1 * b1);

                }

                if(m == (width-1) && n != (height-1)) {
                    for(int i = 0 + m*a1; i < a2 + m*a1; i++){
                        for(int j = 0 + n*b1; j < b1 + n*b1; j++){
                            int num1 = imgArray[i][j];
                            int gray1 = getRgbGray (num1);
                            sum += gray1;
                        }
                    }
                    average[m][n] = sum/(a2 * b1);

                }

                if(m != (width-1) && n == (height-1)) {
                    for(int i = 0 + m*a1; i < a1 + m*a1; i++){
                        for(int j = 0 + n*b1; j < b2 + n*b1; j++){
                            int num1 = imgArray[i][j];
                            int gray1 = getRgbGray (num1);
                            sum += gray1;
                        }
                    }
                    average[m][n] = sum/(a1 * b2);

                }

                if(m == (width-1) && n == (height-1)) {
                    for(int i = 0 + m*a1; i < a2 + m*a1; i++){
                        for(int j = 0 + n*b1; j < b2 + n*b1; j++){
                            int num1 = imgArray[i][j];
                            int gray1 = getRgbGray (num1);
                            sum += gray1;
                        }
                    }
                    average[m][n] = sum/(a2 * b2);

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

                System.out.print(average[m][n]);

            }
        }

    }

    public static void xiangsidu(){
        double a=0;

        for(int i=0;i<64;i++) {
            if(average1[i] == average2[i])a=a+1.5625;
        }

        System.out.println(" ");
        System.out.print("两张图像的相似度为："+a);
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
}

