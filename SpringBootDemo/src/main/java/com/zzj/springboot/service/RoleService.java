package com.zzj.springboot.service;

import com.zzj.springboot.mapper.RoleMapper;
import com.zzj.springboot.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzj on 2020/6/8.
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;
    public List<SysRole> listRolesByUserId(long id){
        return roleMapper.listRolesByUserId(id);
    }

    public Integer add(SysRole role){
        return roleMapper.insert(role);
    }

    public Integer deleteRoleById(long id){
        return roleMapper.deleteById(id);
    }
}
