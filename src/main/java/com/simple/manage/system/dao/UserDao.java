package com.simple.manage.system.dao;

import com.simple.manage.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 用户数据操作接口
 * Author chen
 * CreateTime 2018-07-25 17:09
 **/
@Mapper
public interface UserDao {
    /**
     * 检查数据是否存在
     *
     * @param userId
     * @return
     */
    int checkUser(Integer userId);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    int addUser(Map<String, Object> user);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    int updUser(Map<String, Object> user);

    /**
     * 查询用户对象
     *
     * @param params
     * @return
     */
    User queryUserEntity(Map<String, Object> params);

    /**
     * 查询用户
     *
     * @param userId
     * @return
     */
    Map<String, Object> queryUser(int userId);

    /**
     * 查询用户列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryUserList(Map<String, Object> params);

    /**
     * 逻辑删除用户
     *
     * @param params
     * @return
     */
    int delUser(Map<String, Object> params);

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    int delUserForReal(int userId);
}
