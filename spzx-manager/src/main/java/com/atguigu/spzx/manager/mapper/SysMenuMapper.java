package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    //1 查询所有菜单，返回List集合
    List<SysMenu> findAll();
    //菜单添加
    void save(SysMenu sysMenu);
}
