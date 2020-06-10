package com.zzj.springboot.mapper;

import com.zzj.springboot.model.SysResources;

import java.util.List;

/**
 * Created by zzj on 2020/6/8.
 */
public interface ResourceMapper {
    List<SysResources> listByUserId(long userId);

    List<SysResources> listAll();

    List<SysResources> findResourcesByType(String type);

    List<SysResources> getAllByParentId(long id);
}
