package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.entity.Tree;
import com.simple.manage.system.service.OrgService;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 组织信息controller
 * Author chen
 * CreateTime 2018-12-19 9:18
 **/
@RestController
@RequestMapping(value = "/sys/org")
public class OrgController extends BaseController implements TokenController {
    @Autowired
    private OrgService orgService;

    /**
     * 新增或更新组织信息
     *
     * @param id       主键
     * @param name     组织名称
     * @param parentId 父节点id
     * @param order    排序
     * @param note     备注
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/addOrUpd")
    public Result addOrUpdOrg(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam("name") String name,
                              @RequestParam(value = "parentId", required = false) Integer parentId,
                              @RequestParam("order") Integer order,
                              @RequestParam(value = "note", required = false) String note) throws Exception {
        Map<String, Object> org = new HashMap<>();
        org.put("org_id", id);
        org.put("org_name", name);
        if (parentId == null) {
            parentId = CommonUtil.TREE_ROOT_PARENT_ID;
        }
        org.put("parent_id", parentId);
        org.put("org_order", order);
        org.put("org_note", note);
        org.put("create_id", getLoginInfo().getUser().getId());
        org.put("create_time", LocalDateTime.now());
        org.put("update_id", getLoginInfo().getUser().getId());
        org.put("update_time", LocalDateTime.now());
        this.orgService.addOrUpdOrg(org);
        return success();
    }

    /**
     * 查询组织
     *
     * @param id 主键
     * @return
     */
    @GetMapping(value = "/queryOne")
    public Result queryOrg(@RequestParam("id") Integer id) throws Exception {
        return this.success(this.orgService.queryOrg(id));
    }

    /**
     * 查询组织列表
     *
     * @param parentId 父节点id
     * @param name     名称
     * @param page     页码
     * @param size     页数
     * @return
     */
    @GetMapping(value = "/queryList")
    public Result queryOrgList(@RequestParam(value = "parentId", required = false) Integer parentId,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam("page") Integer page,
                               @RequestParam("size") Integer size) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("org_name", name);
        if (parentId == null) {
            parentId = CommonUtil.TREE_ROOT_PARENT_ID;
        }
        params.put("parent_id", parentId);
        PageInfo pageInfo = this.orgService.queryOrgList(params, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);
    }

    /**
     * 查询组织树
     *
     * @param parentId 父节点id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/queryTree")
    public Result queryOrgTree(@RequestParam(value = "parentId", required = false) Integer parentId) throws Exception {
        Map<String, Object> params = new HashMap<>();

        if (parentId == null) {
            parentId = CommonUtil.TREE_ROOT_PARENT_ID;
        }

        params.put("org_id", parentId);

        List<Tree> tree = this.orgService.queryOrgRecursion(params);
        return this.success(tree);
    }

    /**
     * 删除组织
     *
     * @param id 主键
     * @return
     */
    @DeleteMapping(value = "/del")
    public Result delOrg(@RequestParam("id") Integer id) throws Exception {
        return success();
    }

    /**
     * 查询一选择的
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/querySelectTree")
    public Result querySelectedOrg(@RequestParam("userId") Integer userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("parent_id", CommonUtil.TREE_ROOT_PARENT_ID);

        int rootId = this.orgService.queryRootOrgId(param);

        param.clear();
        param.put("user_id", userId);
        param.put("parent_id", rootId);

        return success(this.orgService.querySelectedOrg(param));
    }
}
