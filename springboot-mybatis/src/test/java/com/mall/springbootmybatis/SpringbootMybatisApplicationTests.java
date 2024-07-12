package com.mall.springbootmybatis;

import com.mall.springbootmybatis.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringbootMybatisApplicationTests {

    @Test
    void contextLoads() {
        // 指定源文件夹路径和目标文件夹路径
        String sourceFolderPath = "F:\\Moments";
        String destinationFolderPath = "F:\\list";

        // 调用方法复制视频文件
        copyVideos(sourceFolderPath, destinationFolderPath);
    }

    private static void copyVideos(String sourceFolderPath, String destinationFolderPath) {
        // 创建源文件夹对象
        File sourceFolder = new File(sourceFolderPath);

        // 获取源文件夹下的所有文件和子文件夹
        File[] files = sourceFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是子文件夹，则递归调用该方法处理子文件夹
                    copyVideos(file.getAbsolutePath(), destinationFolderPath);
                } else {
                    // 如果是视频文件，则复制到目标文件夹中
                    if (isVideoFile(file)) {
                        try {
                            Files.move(file.toPath(), new File(destinationFolderPath, file.getName()).toPath(),
                                    StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("成功剪切照片：" + file.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static boolean isVideoFile(File file) {
        // 判断是否为视频文件，这里简单判断后缀名为常见视频格式即可，你可以根据实际需求进行修改
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return extension.equals("jpg") || extension.equals("png") || extension.equals("mov")|| extension.equals("mp4");
    }
//    @Test
//    public void testBatchInsertUser() throws IOException {
//        InputStream resourceAsStream =
//                Resources.getResourceAsStream("sqlMapConfig.xml");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        SqlSession session = sqlSessionFactory.openSession();
//        System.out.println("===== 开始插入数据 =====");
//        long startTime = System.currentTimeMillis();
//        try {
//            List<User> userList = new ArrayList<>();
//            for (int i = 1; i <= 300000; i++) {
//                User user = new User();
//                user.setId(i);
//                user.setUsername("共饮一杯无 " + i);
//                user.setAge((int) (Math.random() * 100));
//                userList.add(user);
//            }
//            session.insert("batchInsertUser", userList); // 最后插入剩余的数据
//            session.commit();
//
//            long spendTime = System.currentTimeMillis() - startTime;
//            System.out.println("成功插入 30 万条数据,耗时：" + spendTime + "毫秒");
//        } finally {
//            session.close();
//        }
//    }


    @Test
    public void testCirculateInsertUser() throws IOException {
        InputStream resourceAsStream =
                Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession session = sqlSessionFactory.openSession();
        System.out.println("===== 开始插入数据 =====");
        long startTime = System.currentTimeMillis();
        try {
            for (int i = 1; i <= 300000; i++) {
                User user = new User();
                user.setId(i);
                user.setUsername("共饮一杯无 " + i);
                user.setAge((int) (Math.random() * 100));
                // 一条一条新增
                session.insert("insertUser", user);
                session.commit();
            }

            long spendTime = System.currentTimeMillis()-startTime;
            System.out.println("成功插入 30 万条数据,耗时："+spendTime+"毫秒");
        } finally {
            session.close();
        }
    }
    /**
     * 分批次批量插入
     * @throws IOException
     */
//    @Test
//    public void testBatchInsertUser() throws IOException {
//        InputStream resourceAsStream =
//                Resources.getResourceAsStream("sqlMapConfig.xml");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        SqlSession session = sqlSessionFactory.openSession();
//        System.out.println("===== 开始插入数据 =====");
//        long startTime = System.currentTimeMillis();
//        int waitTime = 10;
//        try {
//            List<User> userList = new ArrayList<>();
//            for (int i = 1; i <= 300000; i++) {
//                User user = new User();
//                user.setId(i);
//                user.setUsername("共饮一杯无 " + i);
//                user.setAge((int) (Math.random() * 100));
//                userList.add(user);
//                if (i % 1000 == 0) {
//                    session.insert("batchInsertUser", userList);
//                    // 每 1000 条数据提交一次事务
//                    session.commit();
//                    userList.clear();
//
//                    // 等待一段时间
//                    Thread.sleep(waitTime * 1000);
//                }
//            }
//            // 最后插入剩余的数据
//            if(!CollectionUtils.isEmpty(userList)) {
//                session.insert("batchInsertUser", userList);
//                session.commit();
//            }
//
//            long spendTime = System.currentTimeMillis()-startTime;
//            System.out.println("成功插入 30 万条数据,耗时："+spendTime+"毫秒");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//    }
    /**
     * 分批次批量插入
     * @throws IOException
     */
    @Test
    public void testBatchInsertUser() throws IOException {
        InputStream resourceAsStream =
                Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession session = sqlSessionFactory.openSession();
        System.out.println("===== 开始插入数据 =====");
        long startTime = System.currentTimeMillis();
        int waitTime = 10;
        try {
            List<User> userList = new ArrayList<>();
            for (int i = 1; i <= 300000; i++) {
                User user = new User();
                user.setId(i);
                user.setUsername("共饮一杯无 " + i);
                user.setAge((int) (Math.random() * 10));
                userList.add(user);
                if (i % 5000 == 0) {
                    session.insert("batchInsertUser", userList);
                    // 每 1000 条数据提交一次事务
                    session.commit();
                    userList.clear();
                }
            }
            // 最后插入剩余的数据
            if(!CollectionUtils.isEmpty(userList)) {
                session.insert("batchInsertUser", userList);
                session.commit();
            }

            long spendTime = System.currentTimeMillis()-startTime;
            System.out.println("成功插入 30 万条数据,耗时："+spendTime+"毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}


