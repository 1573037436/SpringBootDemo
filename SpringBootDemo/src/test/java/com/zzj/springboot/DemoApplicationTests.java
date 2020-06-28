package com.zzj.springboot;

import com.zzj.springboot.mapper.UserMapper;
import com.zzj.springboot.model.SysUser;
import com.zzj.springboot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    @Test
    @Rollback(value=false)//默认值是true，数据会回滚
    public void test() throws Exception {
        SysUser user=userMapper.selectById(1);
        //存储
        redisUtil.set("1:2:3:user",user);
        //事物提交
        redisUtil.getRedisTemplate().exec();
        //获取
        Object obj=redisUtil.get("1:2:3:user");
        log.debug("user from reids "+obj);
    }

}
