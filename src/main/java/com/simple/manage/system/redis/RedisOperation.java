package com.simple.manage.system.redis;

import com.simple.manage.system.config.SysConfig;
import com.simple.manage.system.util.CommonUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Description redis 操作组件
 * Author chen
 * CreateTime 2018-07-24 16:58
 **/
@Component
public class RedisOperation {
    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    protected RedisTemplate redisTemplate;

    /**
     * 获取真实key
     *
     * @param key
     * @return
     */
    private String getRealKey(String key) {
        if (!StringUtil.isNullOrEmpty(sysConfig.getCode())) {
            List<String> keyParts = Arrays.asList(sysConfig.getCode(), key);
            key = String.join(CommonUtil.UNDERLINE, keyParts);
        }

        return key;
    }

    /**
     * 根据key获取String类型
     *
     * @param key
     * @return
     */
    public String getStr(String key) {
        String realKey = getRealKey(key);
        return stringRedisTemplate.opsForValue().get(realKey);
    }

    /**
     * String类型存入redis
     *
     * @param key
     * @param value
     */
    public void setStr(String key, String value) {
        String realKey = getRealKey(key);
        stringRedisTemplate.opsForValue().set(realKey, value);
    }

    /**
     * String类型存入redis并设置有效期
     *
     * @param key
     * @param value
     */
    public void setStr(String key, String value, long time) {
        setStr(key, value);
        expireStr(key, time);
    }

    /**
     * 指定string类型缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expireStr(String key, long time) {
        String realKey = getRealKey(key);
        if (time > 0) {
            stringRedisTemplate.expire(realKey, time, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取过期剩余时间
     *
     * @param key
     * @return
     */
    public long getStrExpire(String key) {
        String realKey = getRealKey(key);
        return stringRedisTemplate.getExpire(realKey, TimeUnit.SECONDS);
    }

    /**
     * 从redis中取出对象信息
     *
     * @param key
     * @return
     */
    public Object getObj(String key) {
        String realKey = getRealKey(key);
        return redisTemplate.opsForValue().get(realKey);
    }

    /**
     * 将对象信息放入redis
     *
     * @param key
     * @param obj
     */
    public void setObj(String key, Object obj) {
        String realKey = getRealKey(key);
        redisTemplate.opsForValue().set(realKey, obj);
    }

    /**
     * 将对象信息放入redis并设置有效期
     *
     * @param key
     * @param obj
     */
    public void setObj(String key, Object obj, long time) {
        setObj(key, obj);
        expireObj(key, time);
    }


    /**
     * 指定Obj类型缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expireObj(String key, long time) {
        String realKey = getRealKey(key);
        if (time > 0) {
            redisTemplate.expire(realKey, time, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取过期剩余时间
     *
     * @param key
     * @return
     */
    public long getObjExpire(String key) {
        String realKey = getRealKey(key);
        return redisTemplate.getExpire(realKey, TimeUnit.SECONDS);
    }

    /**
     * 清除redis string缓存
     *
     * @param key
     */
    public void deleteStr(String key) {
        String realKey = getRealKey(key);
        stringRedisTemplate.delete(realKey);
    }

    /**
     * 清除redis obj缓存
     *
     * @param key
     */
    public void deleteObj(String key) {
        String realKey = getRealKey(key);
        redisTemplate.delete(realKey);
    }

    /**
     * 批量清除redis obj缓存
     *
     * @param regex
     */
    public void deleteBatch(String regex) {
        String realKey = getRealKey(regex);
        Set<String> keys = redisTemplate.keys(realKey);
        redisTemplate.delete(keys);
    }
}
