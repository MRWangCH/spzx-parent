package com.atguigu.spzx.manager.utils;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    //通过递归实现封装过程
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        //1 sysMenuList 所有的菜单集合
        List<SysMenu> trees = new ArrayList<>();
        //遍历菜单集合
        for (SysMenu sysMenu : sysMenuList) {
            //找到递归操作的入口，第一层的菜单，parentid为0就是第一层
            if (sysMenu.getParentId().longValue() == 0) {
                //根据第一层找到下一层的数据，使用递归来完成
                trees.add(findChildren(sysMenu, sysMenuList));
            }
        }
        return trees;
    }

    //递归查找下一层菜单数据
    public static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        //SysMenu 有属性List<SysMenu> children，封装下一层的数据
        sysMenu.setChildren(new ArrayList<>());
        //递归查询
        //sysmenu的id和sysMenuList中的parentId相同
        for (SysMenu it : sysMenuList) {
            //判断id和parentid是否相同
            if (sysMenu.getId().longValue() == it.getParentId().longValue()) {
                //it就是下层数据，进行封装
                sysMenu.getChildren().add(findChildren(it, sysMenuList));
            }
        }
        return sysMenu;
    }
}
