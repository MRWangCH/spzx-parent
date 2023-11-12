package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    //查询角色列表
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //设置分页相关参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有数据
        List<SysRole> list = sysRoleMapper.findByPage(sysRoleDto);
        //封装pageInfo对象
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //添加角色
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }
    //角色修改的方法
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }
    //角色删除的方法
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.delete(roleId);
    }

    //查询所有角色
    @Override
    public Map<String, Object> findAll() {
        //1 查询所有角色
        List<SysRole> roleList = sysRoleMapper.findAll();
        //2 分配过的角色列表
        Map<String, Object> map = new HashMap<>();
        map.put("allRolesList", roleList);
        return map;
    }
}
