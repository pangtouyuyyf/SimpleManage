package com.simple.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.config.SysConfig;
import com.simple.manage.system.dao.*;
import com.simple.manage.system.service.CorporationService;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description 公司服务接口实现
 * Author chen
 * CreateTime 2019-01-02 10:31
 **/
@Service
public class CorporationServiceImpl implements CorporationService {
    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private CorporationDao corporationDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleAccessDao roleAccessDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Autowired
    private RoleRouteDao roleRouteDao;

    /**
     * 添加或更新公司信息
     *
     * @param id        主键
     * @param name      名称
     * @param code      编码
     * @param note      备注
     * @param curUserId 当前登录人员
     * @return
     */
    public int addOrUpdCorp(Integer id, String name, String code, String note, int curUserId) {
        int result = 0;
        Map<String, Object> corp = new HashMap<>();
        corp.put("corp_id", id);
        corp.put("corp_name", name);
        corp.put("corp_code", code);
        corp.put("count", null);
        corp.put("corp_note", note);
        corp.put("create_id", curUserId);
        corp.put("create_time", LocalDateTime.now());
        corp.put("update_id", curUserId);
        corp.put("update_time", LocalDateTime.now());
        int count = this.corporationDao.checkCorp(id);
        if (count == 0) {
            //新增
            result = this.corporationDao.addCorp(corp);
        } else if (count == 1) {
            //修改
            result = this.corporationDao.updCorp(corp);
        } else {
        }

        //新增公司的同时新增一系列数据
        if (id == null) {
            id = Integer.valueOf(corp.get("corp_id").toString());
            //新增情况同时增加组织树节点以及管理员并附带相应权限
            //添加组织树根节点
            Map<String, Object> root = new HashMap<>();
            root.put("org_name", name);
            root.put("parent_id", CommonUtil.TREE_ROOT_PARENT_ID);
            root.put("org_order", CommonUtil.TREE_DEFAULT_ORDER);
            root.put("org_note", note);
            root.put("create_id", curUserId);
            root.put("create_time", LocalDateTime.now());
            root.put("corp_id", id);
            result = result + this.orgDao.addOrg(root);

            //添加默认组织节点
            int rootId = Integer.valueOf(root.get("org_id").toString());
            Map<String, Object> org = new HashMap<>();
            org.put("org_name", name);
            org.put("parent_id", rootId);
            org.put("org_order", CommonUtil.TREE_DEFAULT_ORDER);
            org.put("org_note", note);
            org.put("create_id", curUserId);
            org.put("create_time", LocalDateTime.now());
            org.put("corp_id", id);
            result = result + this.orgDao.addOrg(org);

            //添加公司管理员
            Map<String, Object> user = new HashMap<>();
            user.put("user_name", name);
            user.put("login_name", code);
            user.put("create_id", curUserId);
            user.put("create_time", LocalDateTime.now());
            user.put("password", sysConfig.getPassword());
            result = result + this.userDao.addUser(user);

            //添加默认角色
            Map<String, Object> role = new HashMap<>();
            role.put("role_code", CommonUtil.SECOND_LEVEL_ROLE_CODE_PREFIX + code);
            role.put("role_name", name + CommonUtil.SECOND_LEVEL_ROLE_NAME_SUFFIX);
            role.put("role_order", CommonUtil.TREE_DEFAULT_ORDER);
            role.put("role_note", note);
            role.put("create_id", curUserId);
            role.put("create_time", LocalDateTime.now());
            role.put("corp_id", id);
            result = result + this.roleDao.addRole(role);

            //关联默认权限
            Integer templetRoleId = this.roleDao.queryRoleIdByCode(CommonUtil.SECOND_LEVEL_ROLE_CODE);
            if (templetRoleId != null) {
                int userId = Integer.valueOf(user.get("user_id").toString());
                int roleId = Integer.valueOf(role.get("role_id").toString());

                //添加中间关联表数据
                //1.用户角色
                List<Map<String, Object>> userRoles = new ArrayList<>();
                Map<String, Object> userRole = new HashMap<>();
                userRole.put("user_id", userId);
                userRole.put("role_id", templetRoleId);
                userRole.put("create_id", curUserId);
                userRole.put("create_time", LocalDateTime.now());
                userRoles.add(userRole);
                result = result + this.userRoleDao.addUserRole(userRoles);

                //2.角色菜单
                //2.1查询角色菜单
                List<Map<String, Object>> roleMenu = this.roleMenuDao.queryAllByRoleId(templetRoleId);
                if (roleMenu != null && !roleMenu.isEmpty()) {
                    roleMenu = roleMenu.stream().peek(node -> {
                        node.put("role_id", roleId);
                        node.put("create_id", curUserId);
                        node.put("create_time", LocalDateTime.now());
                    }).collect(Collectors.toList());
                    //2.2保存复制出的角色菜单
                    result = result + this.roleMenuDao.addRoleMenu(roleMenu);
                }

                //3.角色路由
                //3.1查询角色路由
                List<Map<String, Object>> roleRoute = this.roleRouteDao.queryAllByRoleId(templetRoleId);
                if (roleRoute != null && !roleRoute.isEmpty()) {
                    roleRoute = roleRoute.stream().peek(node -> {
                        node.put("role_id", roleId);
                        node.put("create_id", curUserId);
                        node.put("create_time", LocalDateTime.now());
                    }).collect(Collectors.toList());
                    //3.2保存复制出的角色路由
                    result = result + this.roleRouteDao.addRoleRoute(roleRoute);
                }

                //4.角色请求
                //4.1查询角色请求
                List<Map<String, Object>> roleAccess = this.roleAccessDao.queryAllByRoleId(templetRoleId);
                if (roleAccess != null && !roleAccess.isEmpty()) {
                    roleAccess = roleAccess.stream().peek(node -> {
                        node.put("role_id", roleId);
                        node.put("create_id", curUserId);
                        node.put("create_time", LocalDateTime.now());
                    }).collect(Collectors.toList());
                    //4.2保存复制出的角色请求
                    result = result + this.roleAccessDao.addRoleAccess(roleAccess);
                }
            }
        }
        return result;
    }

    /**
     * 查询公司信息
     *
     * @param corpId
     * @return
     */
    public Map<String, Object> queryCorp(int corpId) {
        return this.corporationDao.queryCorp(corpId);
    }

    /**
     * 查询公司信息列表
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    public PageInfo queryCorpList(Map<String, Object> params, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> corporationDao.queryCorpList(params));
    }

    /**
     * 逻辑删除公司信息
     *
     * @param corpId
     * @param userId
     * @return
     */
    public int delCorp(int corpId, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("corp_id", corpId);
        params.put("update_id", userId);
        params.put("update_time", LocalDateTime.now());
        params.put("is_delete", "1");
        return this.corporationDao.delCorp(params);
    }

    /**
     * 删除公司信息
     *
     * @param corpId
     * @return
     */
    public int delCorpForReal(int corpId) {
        return this.corporationDao.delCorpForReal(corpId);
    }
}
