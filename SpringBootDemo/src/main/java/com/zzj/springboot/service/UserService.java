package com.zzj.springboot.service;

import com.zzj.springboot.mapper.UserMapper;
import com.zzj.springboot.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zzj on 2020/5/29.
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public SysUser findUser(SysUser user){
        return userMapper.findUser(user);
    }

    public SysUser findByName(String name){
        return userMapper.findByName(name);
    }

    public SysUser getByPrimaryKey(long id){
        return userMapper.selectById(id);
    }
}
