package com.simple.manage.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.dao.ModuleDao;
import com.simple.manage.system.dao.RoleMenuDao;
import com.simple.manage.system.service.RoleMenuService;
import com.simple.manage.system.util.BinaryUtil;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description 角色菜单服务接口实现
 * Author chen
 * CreateTime 2018-09-12 14:40
 **/
@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private ModuleDao moduleDao;

    /**
     * 批量添加角色菜单关系
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    @Transactional
    public int saveRoleMenu(int currentUserId, JSONObject obj) {
        int result = 0;
        if (!obj.containsKey("roleId") || !obj.containsKey("list")) {
            return result;
        }
        int roleId = obj.getInteger("roleId");
        JSONArray menuArr = obj.getJSONArray("list");

        Map<String, Object> param = new HashMap<>();
        param.put("role_id", roleId);

        //查询现有角色菜单
        List<Map<String, Integer>> savedList = Optional.of(this.roleMenuDao.queryAll(param)).orElseGet(() -> {
            return new ArrayList<>();
        });

        //生成前台保存角色菜单
        Map<String, Integer> roleMenu = null;
        List<Map<String, Integer>> roleMenus = new ArrayList<>();

        List<Integer> menuIds = JSONArray.parseArray(JSONObject.toJSONString(menuArr), Integer.class);
        if (menuIds != null && !menuIds.isEmpty()) {
            for (int menuId : menuIds) {
                roleMenu = new HashMap<>();
                roleMenu.put("role_id", roleId);
                roleMenu.put("menu_id", menuId);
                roleMenus.add(roleMenu);
            }
        }

        //获取待删除的集合并删除
        List<Map<String, Integer>> delList = savedList.stream().filter(item -> !roleMenus.contains(item)).collect(Collectors.toList());
        if (delList != null && !delList.isEmpty()) {
            result += this.roleMenuDao.delRoleMenuBatch(delList);
        }

        //获取待保存集合(去重)并保存
        List<Map<String, Integer>> tempAddList = roleMenus.stream().distinct().filter(item -> !savedList.contains(item)).collect(Collectors.toList());
        if (tempAddList != null && !tempAddList.isEmpty()) {
            List<Map<String, Object>> addList = new ArrayList<>();
            String operateCode = BinaryUtil.reverseBinary(CommonUtil.OPERATE_CODE_DEFAULT, CommonUtil.OPERATE_CODE_LENGTH);
            Map<String, Object> tempAdd = null;

            for (Map<String, Integer> temp : tempAddList) {
                tempAdd = new HashMap<>();
                tempAdd.putAll(temp);
                tempAdd.put("operate_code", operateCode);
                tempAdd.put("create_id", currentUserId);
                tempAdd.put("create_time", LocalDateTime.now());
                addList.add(tempAdd);
            }
            result += this.roleMenuDao.addRoleMenu(addList);
        }

        return result;
    }

    /**
     * 查询角色菜单关系
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryRoleMenuList(Map<String, Object> params) {
        return this.roleMenuDao.queryRoleMenuList(params);
    }

    /**
     * 查询当前用户可用菜单
     *
     * @param rIdList
     * @return
     */
    public List<Map<String, Object>> queryAvlRoleMenuList(List<Integer> rIdList) {
        List<Map<String, Object>> moduleList = this.moduleDao.queryAvlMenuModuleList(rIdList);
        if (moduleList != null && !moduleList.isEmpty()) {
            Map<String, Object> params = null;
            for (Map<String, Object> temp : moduleList) {
                params = new HashMap<>();
                int moduleId = Integer.valueOf(temp.get("id").toString());
                params.put("module_id", moduleId);
                params.put("roleIds", rIdList);
                temp.put("menus", this.roleMenuDao.queryAvlRoleMenuList(params));
            }
        }
        return moduleList;
    }

    /**
     * 查询角色菜单操作
     *
     * @param roleId
     * @param page
     * @param size
     * @return
     */
    public PageInfo queryRoleMenuOperate(int roleId, int page, int size) {
        PageInfo pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> roleMenuDao.queryRoleMenuOperate(roleId));
        String allCheckCode = BinaryUtil.toBinary((1 << CommonUtil.OPERATE_CODE_LENGTH) - 1, CommonUtil.OPERATE_CODE_LENGTH);
        List<Map<String, Object>> list = pageInfo.getList();
        if (list != null && !list.isEmpty()) {
            list.stream().peek(temp -> {
                String code = temp.get("code").toString();
                if (allCheckCode.equals(code)) {
                    temp.put("checked", true);
                } else {
                    temp.put("checked", false);
                }
                temp.put("options", analyzeOperateCode(code));
                temp.remove("code");
            }).collect(Collectors.toList());
            pageInfo.setList(list);
        }

        return pageInfo;
    }

    /**
     * 批量保存角色菜单操作
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    @Transactional
    public int saveRoleMenuOperate(int currentUserId, JSONObject obj) {
        int result = 0;
        if (!obj.containsKey("roleId") || !obj.containsKey("list")) {
            return result;
        }
        int roleId = obj.getInteger("roleId");
        JSONArray operaArr = obj.getJSONArray("list");
        if (operaArr.isEmpty()) {
            return result;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("role_id", roleId);

        //查询现有角色菜单操作
        List<Map<String, Object>> savedList = Optional.of(this.roleMenuDao.queryAllOperate(param)).orElseGet(() -> {
            return new ArrayList<>();
        });

        //生成前台保存角色菜单操作
        Map<String, Object> roleMenu = null;
        List<Map<String, Object>> roleOperas = new ArrayList<>();
        for (int i = 0; i < operaArr.size(); i++) {
            roleMenu = new HashMap<>();
            JSONObject temp = operaArr.getJSONObject(i);
            roleMenu.put("id", temp.getInteger("id"));
            roleMenu.put("operate_code", BinaryUtil.reverseBinary(temp.getInteger("value"), CommonUtil.OPERATE_CODE_LENGTH));
            roleOperas.add(roleMenu);
        }

        //取待更新差集
        //获取待保存集合(去重)并保存
        List<Map<String, Object>> tempAddList = roleOperas.stream().distinct().filter(item -> !savedList.contains(item)).collect(Collectors.toList());
        if (tempAddList != null && !tempAddList.isEmpty()) {
            List<Map<String, Object>> addList = new ArrayList<>();
            Map<String, Object> tempAdd = null;

            for (Map<String, Object> temp : tempAddList) {
                tempAdd = new HashMap<>();
                tempAdd.putAll(temp);
                tempAdd.put("update_id", currentUserId);
                tempAdd.put("update_time", LocalDateTime.now());
                addList.add(tempAdd);
            }
            result += this.roleMenuDao.updRoleMenuOperate(addList);
        }


        return result;
    }

    /**
     * 根据当前路由匹配菜单中url获取当前角色菜单操作码
     *
     * @param params
     * @return
     */
    public String queryMenuOperateCode(Map<String, Object> params) {
        return this.roleMenuDao.queryMenuOperateCode(params);
    }

    /**
     * 解析操作码
     *
     * @param code
     * @return
     */
    private List<Map<String, Object>> analyzeOperateCode(String code) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> temp = null;
        String judge = null;
        for (int i = 0; i < CommonUtil.OPERATE_CODE_LENGTH; i++) {
            temp = new HashMap<>();
            temp.put("label", getOperateName(i));
            temp.put("value", 1 << i);
            judge = String.valueOf(code.charAt(i));
            if (CommonUtil.SIGN_YES.equals(judge)) {
                temp.put("checked", false);
            } else {
                temp.put("checked", true);
            }
            list.add(temp);
        }
        return list;
    }

    /**
     * 根据操作顺序获取操作名称
     *
     * @param orderNum
     * @return
     */
    private String getOperateName(int orderNum) {
        String operateName = null;
        switch (orderNum) {
            case 0:
                operateName = "新增";
                break;
            case 1:
                operateName = "删除";
                break;
            case 2:
                operateName = "修改";
                break;
            case 4:
                operateName = "上传";
                break;
            case 5:
                operateName = "下载";
                break;
            default:
                operateName = "查看";
                break;
        }
        return operateName;
    }
}
