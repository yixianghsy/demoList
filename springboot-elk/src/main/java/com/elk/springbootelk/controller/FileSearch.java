package com.elk.springbootelk.controller;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileSearch {
    public static void getFileList(String path, String fileExtension) throws IOException {
        File folder = new File(path);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(fileExtension)) {
                // 找到符合条件的文件
                // 复制到其他文件夹
                System.out.println(file.getAbsolutePath());
                File srcFile = new File("G:\\list\\xhs_live_photo_1680880617489.mp4");
                File destFile = new File("G:\\mp4\\");
                Files.copy(srcFile.toPath(),destFile.toPath());
//                FileUtils.copyFile(srcFile , destFile );
//                if (!srcFile.exists()) {
//                    srcFile.delete();
//                }

            } else if (file.isDirectory()) {
                // 递归遍历子目录
                getFileList(file.getAbsolutePath(), fileExtension);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String targetPath = "G:\\list";
        String fileExtension = ".mp4";
        getFileList(targetPath, fileExtension);

    }
}