package com.elk.springbootelk.controller;

import java.io.*;

public class CopyDirectory {
    /**
     * @Description: 整合两个操作，一个是文件夹的所有目录，然后复制目录下的所有文件到相应的目录
     * @param srcPath   需要复制的目录
     * @param targetPath  复制到哪里
     * @author cxh
     */
    public static void copyFileOrDir(String srcPath, String targetPath){
        parseDir(srcPath,targetPath);
        copyAllFile(srcPath, targetPath);
    }

    public static void copyAllFile(String srcPath, String targetPath){
        File f = new File(srcPath);
        File[] fileList = f.listFiles();
        for(File f1 : fileList){
            if(f1.isFile()) {
                CopyDirectory.copyFile(srcPath + "//" + f1.getName(), targetPath + "//" + f1.getName());
            }
            //判断是否是目录
            if(f1.isDirectory()) {
                copyAllFile(f1.getPath().toString(), targetPath + "//" + f1.getName());
            }
        }
    }
    /**
     * @Description: 通过字节流复制一个文件
     * @param src      源文件的路径
     * @param target   目标文件的路径
     * @author cxh
     */
    public static void copyFile(String src, String target){
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(target);
            byte[] buff = new byte[1024];
            int len = 0;
            while((len = is.read(buff, 0, buff.length)) != -1) {
                os.write(buff, 0, len);
            }
            System.out.println("文件拷贝成功！");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if(is!=null){
                        try {
                            is.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    /**
     * @Description: 复制一个目录下的所有目录文件（只复制目录结构）
     * @param pathName   需要复制的目标目录
     * @param target     生成的目标文件目录
     * @author cxh
     */
    public static void parseDir(String pathName, String target){
        //创建一个新的目录
        File targetFile = new File(target);
        if(!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //创建一个抽象路径
        File file = new File(pathName);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                if(f.isDirectory()) {
                    parseDir(f.getPath(), target + "//" + f.getName());
                }
            }
        }
    }
    //删除指定文件夹下所有文件
//param path 文件夹完整绝对路径
    public static boolean DeleteFile(String pathName){
        boolean flag = false;
        File file = new File(pathName);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (pathName.endsWith(File.separator)) {
                temp = new File(pathName + tempList[i]);
            } else {
                temp = new File(pathName + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                DeleteFile(pathName + "/" + tempList[i]);//先删除文件夹里面的文件
                flag = true;
            }
        }
        return flag;
    }
    //删除文件夹
    //param folderPath 文件夹完整绝对路径
    public static void DeleteFolder(String folderPath) {
        try {
            DeleteFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
