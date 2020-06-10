package com.zzj.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzj.springboot.model.SysRole;

import java.util.List;

/**
 * Created by zzj on 2020/6/8.
 */
public interface RoleMapper extends BaseMapper<SysRole> {
    List<SysRole> listRolesByUserId(long id);
}
