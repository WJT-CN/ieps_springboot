package com.wjt.ieps.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjt.ieps.entity.Permission;
import com.wjt.ieps.entity.RolePerm;
import com.wjt.ieps.mapper.PermissionMapper;
import com.wjt.ieps.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjt.ieps.service.RolePermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjt
 * @since 2022-04-25
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermService rolePermissionService;

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("perm_id"));

        //根据角色id获取角色权限
        List<RolePerm> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePerm>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePerm rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermId().equals(permission.getPermId())) {
//                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = bulid(allPermissionList);
        return permissionList;
    }

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    private static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if (treeNode.getParentId()==0) {
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    private static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());

        for (Permission it : treeNodes) {
            System.out.println(treeNode.getPermId());
            System.out.println(it.getParentId());
            if(treeNode.getPermId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }

}
