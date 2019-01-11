package com.simple.manage.system.controller;

import com.simple.manage.system.service.UserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description 用户组织关联controller
 * Author chen
 * CreateTime 2019-01-11 10:10
 **/
@RestController
@RequestMapping(value = "/sys/userOrg")
public class UserOrgController extends BaseController implements TokenController{
    @Autowired
    private UserOrgService userOrgService;
}
