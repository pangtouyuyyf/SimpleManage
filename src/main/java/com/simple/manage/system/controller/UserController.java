package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.annotation.AuthorizationAnnotation;
import com.simple.manage.system.config.SysConfig;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.domain.LoginInfo;
import com.simple.manage.system.entity.User;
import com.simple.manage.system.redis.RedisOperation;
import com.simple.manage.system.service.UserService;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 用户业务controller
 * Author chen
 * CreateTime 2018-08-07 17:13
 **/
@RestController
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController implements TokenController {
    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private UserService userService;

    /**
     * 查询用户
     *
     * @param id 主键
     * @return
     */
    @AuthorizationAnnotation
    @GetMapping(value = "/queryOne")
    public Result queryUser(@RequestParam("id") Integer id) throws Exception {
        return this.success(this.userService.queryUser(id));
    }

    /**
     * 查询用户列表
     *
     * @param name      名称
     * @param loginName 登录名
     * @param phone     电话
     * @param email     邮箱
     * @param page      页码
     * @param size      页数
     * @return
     */
    @AuthorizationAnnotation
    @GetMapping(value = "/queryList")
    public Result queryUserList(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "loginName", required = false) String loginName,
                                @RequestParam(value = "phone", required = false) String phone,
                                @RequestParam(value = "email", required = false) String email,
                                @RequestParam("page") Integer page,
                                @RequestParam("size") Integer size) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("user_name", name);
        params.put("login_name", loginName);
        params.put("phone", phone);
        params.put("email", email);
        PageInfo pageInfo = this.userService.queryUserList(params, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);
    }

    /**
     * 添加更新用户
     *
     * @param id        主键
     * @param name      名称
     * @param loginName 登录名
     * @param phone     电话
     * @param email     邮箱
     * @return
     */
    @AuthorizationAnnotation
    @PostMapping(value = "/addOrUpd")
    public Result addOrUpdUser(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "loginName", required = false) String loginName,
                               @RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "email", required = false) String email) throws Exception {
        Map<String, Object> user = new HashMap<>();
        user.put("user_id", id);
        user.put("user_name", name);
        user.put("login_name", loginName);
        user.put("phone", phone);
        user.put("email", email);
        user.put("create_id", getLoginInfo().getUser().getId());
        user.put("create_time", LocalDateTime.now());
        user.put("update_id", getLoginInfo().getUser().getId());
        user.put("update_time", LocalDateTime.now());
        if (id == null) {
            //新增用户默认密码
            user.put("password", sysConfig.getPassword());
        }

        int count = this.userService.addOrUpdUser(user);

        if (id != null && count > 0) {
            //修改用户信息更新缓存
            Map<String, Object> params = new HashMap<>();
            params.put("user_id", id);
            User userEntity = this.userService.queryUserEntity(params);

            //更新缓存信息
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUser(userEntity);
            loginInfo.setRole(getLoginInfo().getRole());
            List<String> loginInfoKeyParts = Arrays.asList(CommonUtil.LOGIN_INFO_PREFIX, Integer.toString(userEntity.getId()), Integer.toString(getLoginInfo().getRole().getId()));
            this.redisOperation.setObj(String.join(CommonUtil.UNDERLINE, loginInfoKeyParts), loginInfo);
        }

        return success();
    }

    /**
     * 删除用户
     *
     * @param id 主键
     * @return
     */
    @AuthorizationAnnotation
    @DeleteMapping(value = "/del")
    public Result delUser(@RequestParam("id") Integer id) throws Exception {
        this.userService.delUser(id, getLoginInfo().getUser().getId());
        return success();
    }
}
