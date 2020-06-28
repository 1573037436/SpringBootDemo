package com.zzj.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzj.springboot.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * Created by zzj on 2020/5/29.
 */
public interface UserMapper extends BaseMapper<SysUser> {

    SysUser findUser(SysUser user);

    SysUser findByName(String name);

    boolean updateUserRole(long userId,Integer[] rolIds);

    void deleteBatchMapIds(Map ids);

    void deleteBatchListIds(@Param("ids") List ids);

    IPage<SysUser> selectAll(Page<?> page);
}