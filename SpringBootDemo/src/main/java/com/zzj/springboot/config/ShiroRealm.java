package com.zzj.springboot.config;

import com.zzj.springboot.enums.UserStatusEnum;
import com.zzj.springboot.enums.UserTypeEnum;
import com.zzj.springboot.model.SysResources;
import com.zzj.springboot.model.SysRole;
import com.zzj.springboot.model.SysUser;
import com.zzj.springboot.service.ResourcesService;
import com.zzj.springboot.service.RoleService;
import com.zzj.springboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zzj on 2020/6/8.
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;
    @Resource
    private ResourcesService resourcesService;
    @Resource
    private RoleService roleService;

    /**
     * 提供账户信息返回认证信息（用户的角色信息集合）
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        //查询用户信息
        SysUser user = userService.findByName(username);
        if (user == null) {
            throw new UnknownAccountException("账号不存在！");
        }
        if (user.getStatus() != null && UserStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            throw new LockedAccountException("帐号已被锁定，禁止登录！");
        }
        //传入用户名和密码进行身份认证，并返回认证信息，
        //principal参数使用用户Id，方便动态刷新用户权限
        return new SimpleAuthenticationInfo(
                user.getId(),
                user.getPassword(),
                ByteSource.Util.bytes(username),
                getName()
        );
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限（角色的权限信息集合）
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Long userId = (Long) SecurityUtils.getSubject().getPrincipal();
        SysUser user = userService.getByPrimaryKey(userId);
        if (null == user) {
            return info;
        }

        // 赋予角色
        List<SysRole> roleList = roleService.listRolesByUserId(userId);
        if (!CollectionUtils.isEmpty(roleList)) {
            Set<String> roleNames = new HashSet<>();
            roleList.forEach(role->{
                roleNames.add(role.getName());
            });
            info.setRoles(roleNames);
        }

        // 赋予权限
        List<SysResources> resourcesList = null;
        // ROOT用户默认拥有所有权限
        if (UserTypeEnum.ROOT.toString().equalsIgnoreCase(user.getUserType())) {
            resourcesList = resourcesService.listAll();
        } else {
            resourcesList = resourcesService.listByUserId(userId);
        }

        if (!CollectionUtils.isEmpty(resourcesList)) {
            Set<String> permissionSet = new HashSet<>();
            resourcesList.forEach(resource->{
                permissionSet.add(resource.getPermission());
            });
            info.setStringPermissions(permissionSet);
        }
        return info;
    }
}