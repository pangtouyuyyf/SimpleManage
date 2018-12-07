package com.simple.manage.system.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;

import java.util.Map;

/**
 * Description 支付宝调用接口
 * Author chen
 * CreateTime 2018-12-07 14:07
 **/

public interface AlipayService {
    /**
     * app生成订单
     *
     * @param userId 用户ID
     * @param detail 明细
     * @param money  金额
     * @return
     * @throws AlipayApiException
     */
    String generateOrders(int userId, String detail, String money) throws AlipayApiException;

    /**
     * 生成支付二维码
     *
     * @param userId
     * @param detail
     * @param money
     * @return
     */
    JSONObject generateQRCode(int userId, String detail, String money) throws Exception;

    /**
     * 校验异步返回信息
     *
     * @param params 支付宝服务器返回参数
     * @return
     * @throws AlipayApiException
     */
    boolean receiveNotify(Map<String, String> params) throws AlipayApiException;
}
