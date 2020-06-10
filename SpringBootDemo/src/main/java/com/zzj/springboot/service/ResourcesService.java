package com.zzj.springboot.service;

import com.zzj.springboot.mapper.ResourceMapper;
import com.zzj.springboot.model.SysResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by zzj on 2020/6/8.
 */
@Service
public class ResourcesService {
    @Autowired
    ResourceMapper resourceMapper;
    public List<SysResources> listByUserId(long id){
        return resourceMapper.listByUserId(id);
    }

    public List<SysResources> listAll(){
        return resourceMapper.listAll();
    }

    public List<SysResources> findResourcesByType(String type){
        return resourceMapper.findResourcesByType(type);
    }

    /**
     * 处理菜单
     * @param menus
     * @return
     */
    public List<SysResources> handleMenus(List<SysResources> menus) {
        /*for (SysResources menu : menus) {
            List<SysResources> children = resourceMapper.getAllByParentId(menu.getId());
            menu.setNodes(children);
        }
        Iterator<SysResources> iterator = menus.iterator();
        while (iterator.hasNext()) {
            SysResources menu = iterator.next();
            if (menu.getParentId() != 0) {
                iterator.remove();
            }
        }*/
        menus.forEach(m -> {
            List<SysResources> children = resourceMapper.getAllByParentId(m.getId());
            m.setNodes(children);
        });
        //删除parendId不为根节点的项
        menus.removeIf(m -> m.getParentId() != 0);
        return menus;
    }
}
