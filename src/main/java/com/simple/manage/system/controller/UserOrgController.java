package com.simple.manage.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.OrgService;
import com.simple.manage.system.service.UserOrgService;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Description 用户组织关联controller
 * Author chen
 * CreateTime 2019-01-11 10:10
 **/
@RestController
@RequestMapping(value = "/sys/userOrg")
public class UserOrgController extends BaseController implements TokenController {
    @Autowired
    private UserOrgService userOrgService;

    @Autowired
    private OrgService orgService;

    /**
     * 查询用户组织
     *
     * @return
     */
    @GetMapping(value = "/queryList")
    public Result queryUserOrgList(@RequestParam("userId") Integer userId) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("parent_id", CommonUtil.TREE_ROOT_PARENT_ID);
        param.put("corp_id", getLoginInfo().getUser().getCorpId());

        int rootId = this.orgService.queryRootOrgId(param);

        param.clear();
        param.put("user_id", userId);
        param.put("parent_id", rootId);

        return this.success(this.userOrgService.queryUserOrgList(param));
    }

    /**
     * 添加用户组织
     *
     * @param body
     * @return
     */
    @PostMapping(value = "/add")
    public Result addUserRole(@RequestBody JSONObject body) throws Exception {
        int currentUserId = getLoginInfo().getUser().getId();
        return success(this.userOrgService.saveUserOrg(currentUserId, body));
    }
}
