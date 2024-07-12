package com.elk.springbootelk.controller;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BMPLoader2 {
    public static void main(String[] args) {
        String img1 = md5(getByte("E:\\Temp\\list\\FILE14759.JPG"));
        String img2 = md5(getByte("E:\\Temp\\list\\FILE14760.JPG"));
        if (img1.equals(img2))
            System.out.println("两图片是一样的");
        else
            System.out.println("两图片是不一样的");
    }

    public static byte[] getByte(String name) {
        // 得到文件长度
        File file = new File(name);
        byte[] b = new byte[(int) file.length()];
        System.out.println(file.length());
        try {
            InputStream in = new FileInputStream(file);
            try {
                in.read(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }

    public static String md5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            return new String(md.digest(data));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
