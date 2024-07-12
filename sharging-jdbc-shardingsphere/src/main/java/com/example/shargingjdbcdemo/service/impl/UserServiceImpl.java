package com.example.shargingjdbcdemo.service.impl;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.shargingjdbcdemo.confg.datasource.dynamic.DataSourceConfig;
import com.example.shargingjdbcdemo.mapper.UserMapper;
import com.example.shargingjdbcdemo.service.IUserService;
import com.example.shargingjdbcdemo.user.User;
import com.example.shargingjdbcdemo.util.IdGeneratorUtil;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    IdGeneratorUtil idGeneratorUtil;

//    @Override
//    public IPage<User> listPage(User params) {
//        IPage<User> result = userMapper.selectDataList(new Page<>(params.getPage(),params.getPageSize()), params);
//        List<User> list = result.getRecords();
//        return result;
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Long addOrUpdateData(User user) {
//        if (user.getUserId() == null) {
//            user.insert();
//        } else {
//            user.updateById();
//        }
//        return user.getUserId();
//    }


    @Override
    public  List<User> select(){
        return userMapper. select();
    }

    @Override
    public void listPageForPrecise() {

    }

    /**
     * 也可以将默认数据源指定为master普通数据源，在需要分表的service方法上加@DS("aml_sharding")，
     * 使用shardingsphere分表数据源。这里可以按照改动量来指定哪种数据源为默认数据源
     * @param user 保存参数
     * @return
     */
    @Override
    public Long addOrUpdateData(User user) {
        return null;
    }

    @Override
    public String addBatchData(int addSum) {
        LocalDateTime saveBeforeDateTime = LocalDateTime.now();
        // 第1次插入
        int page = 1;
        // 每次插入数据条数
        int pageSize = 5000;
        // 累计插入数量
        int total = 0;
        // 循环插入数据
        for (int index = 1; index <= addSum; ) {
            total = page * pageSize;
            log.info("page:[{}] pageSize:[{}] total:[{}] index:[{}]", page, pageSize, total, index);
            if (total > addSum) {
                int finalNum = addSum - ((page - 1) * pageSize);
                log.info("最后一页新增数：[{}]", finalNum);
                this.insertData(finalNum);
            } else {
                this.insertData(pageSize);
            }
            page += 1;
            index = total + 1;
        }

        LocalDateTime saveAfterDateTime = LocalDateTime.now();
        Duration duration = Duration.between(saveBeforeDateTime, saveAfterDateTime);
        long millis = duration.toMillis();
        String msg = String.format("测试插入%s条数据用时: [%s ms]  [%s s]", addSum, millis, millis / 1000);
        log.info(msg);
        return msg;
    }

    private void insertData(int addSum) {
        List<User> demoList = Lists.newLinkedList();
        for (int i = 1; i <= addSum; i++) {
            User item = new User();
            item.setUserId(idGeneratorUtil.snowflakeId());
            item.setUsername("username - " + i);
            item.setPassword("123456");
            item.setSex((byte)(i % 2));
            item.setRemark("this is username:" + i);
            demoList.add(item);
        }
        this.userMapper.insertBatch(demoList);
    }

}
