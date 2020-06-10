package com.zzj.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzj.springboot.model.SysUser;


/**
 * Created by zzj on 2020/5/29.
 */
public interface UserMapper extends BaseMapper<SysUser> {

    SysUser findUser(SysUser user);

    SysUser findByName(String name);

}