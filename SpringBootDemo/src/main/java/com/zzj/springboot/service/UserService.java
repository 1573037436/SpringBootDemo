package com.zzj.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzj.springboot.mapper.UserMapper;
import com.zzj.springboot.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zzj on 2020/5/29.
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public IPage<SysUser> selectAll(Page page){
        page.setTotal(userMapper.selectCount(null));
        return userMapper.selectAll(page);
    }
    public IPage<SysUser> selectPage(Page page){
        IPage pages=userMapper.selectPage(page,null);
        return pages;
    }

    public SysUser findUser(SysUser user){
        return userMapper.findUser(user);
    }

    public SysUser findByName(String name){
        return userMapper.findByName(name);
    }

    public SysUser getByPrimaryKey(long id){
        return userMapper.selectById(id);
    }

    public int updateUser(SysUser user){
        return userMapper.updateById(user);
    }

    public int addUser(SysUser user){
        return userMapper.insert(user);
    }

    public int deleteById(long id){
        return userMapper.deleteById(id);
    }

    public boolean updateUserRole(long userId,Integer[] rolIds){
        return userMapper.updateUserRole(userId,rolIds);
    }
    public void deleteBatchIds(List ids){
        userMapper.deleteBatchListIds(ids);
    }

    public void deleteBatchMapIds(Map ids){
        userMapper.deleteBatchMapIds(ids);
    }
}
