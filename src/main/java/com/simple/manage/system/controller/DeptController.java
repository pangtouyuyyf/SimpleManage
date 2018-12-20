package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.entity.Tree;
import com.simple.manage.system.service.DeptService;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 部门信息controller
 * Author chen
 * CreateTime 2018-12-19 9:18
 **/
@RestController
@RequestMapping(value = "/sys/dept")
public class DeptController extends BaseController implements TokenController {
    @Autowired
    private DeptService deptService;

    /**
     * 新增或更新部门信息
     *
     * @param id       主键
     * @param name     部门名称
     * @param parentId 父节点id
     * @param leader   部门领导id
     * @param order    排序
     * @param note     备注
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/addOrUpd")
    public Result addOrUpdDept(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam("name") String name,
                               @RequestParam(value = "parentId", required = false) Integer parentId,
                               @RequestParam(value = "leader", required = false) Integer leader,
                               @RequestParam("order") Integer order,
                               @RequestParam(value = "note", required = false) String note) throws Exception {
        Map<String, Object> dept = new HashMap<>();
        dept.put("dept_id", id);
        dept.put("dept_name", name);
        if (parentId == null) {
            parentId = CommonUtil.TREE_ROOT_PARENT_ID;
        }
        dept.put("parent_dept_id", parentId);
        dept.put("leader_id", leader);
        dept.put("dept_order", order);
        dept.put("dept_note", note);
        dept.put("create_id", getLoginInfo().getUser().getId());
        dept.put("create_time", LocalDateTime.now());
        dept.put("update_id", getLoginInfo().getUser().getId());
        dept.put("update_time", LocalDateTime.now());
        this.deptService.addOrUpdDept(dept);
        return success();
    }

    /**
     * 查询菜单
     *
     * @param id 主键
     * @return
     */
    @GetMapping(value = "/queryOne")
    public Result queryDept(@RequestParam("id") Integer id) throws Exception {
        return this.success(this.deptService.queryDept(id));
    }

    /**
     * 查询菜单列表
     *
     * @param parentId 父节点id
     * @param name     名称
     * @param page     页码
     * @param size     页数
     * @return
     */
    @GetMapping(value = "/queryList")
    public Result queryDeptList(@RequestParam(value = "parentId", required = false) Integer parentId,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam("page") Integer page,
                                @RequestParam("size") Integer size) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("dept_name", name);
        if (parentId == null) {
            parentId = CommonUtil.TREE_ROOT_PARENT_ID;
        }
        params.put("parent_dept_id", parentId);
        PageInfo pageInfo = this.deptService.queryDeptList(params, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);
    }

    /**
     * 查询部门树
     *
     * @param parentId 父节点id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/queryTree")
    public Result queryDeptTree(@RequestParam(value = "parentId", required = false) Integer parentId) throws Exception {
        if (parentId == null) {
            parentId = CommonUtil.TREE_ROOT_PARENT_ID;
        }
        List<Tree> tree = this.deptService.queryDeptRecursion(parentId);
        return this.success(tree);
    }

    /**
     * 删除部门
     *
     * @param id 主键
     * @return
     */
    @DeleteMapping(value = "/del")
    public Result delDept(@RequestParam("id") Integer id) throws Exception {
        return success();
    }
}
