package com.simple.manage.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.simple.manage.system.config.AlipayConfig;
import com.simple.manage.system.config.FtpConfig;
import com.simple.manage.system.ftp.FtpOperation;
import com.simple.manage.system.service.AlipayService;
import com.simple.manage.system.util.LogUtil;
import com.simple.manage.system.util.RandomNumUtil;
import com.simple.manage.system.util.ZxingUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

/**
 * Description 支付宝调用接口实现类
 * Author chen
 * CreateTime 2018-12-07 14:07
 **/

@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private FtpConfig ftpConfig;

    @Autowired
    private FtpOperation ftpOperation;

    private static final String MODEL_TIMEOUT_EXPRESS = "30m";

    private static final String MODEL_PRODUCT_CODE = "QUICK_MSECURITY_PAY";

    private static final String IMG_FOLDER = "qrCodeImg";

    /**
     * app生成订单
     *
     * @param userId      用户ID
     * @param detail      明细
     * @param totalAmount 金额
     * @return
     * @throws AlipayApiException
     */
    public String generateOrders(int userId, String detail, String totalAmount) throws AlipayApiException {
        String orderStr = null;
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getServeUrl(), alipayConfig.getAppId(), alipayConfig.getAppPrivateKey(), alipayConfig.getDataFormat(), alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(getPayModel(false, getOutTradeNo(userId), detail, totalAmount));
        request.setNotifyUrl(alipayConfig.getNotifyUrl());

        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);

        //就是orderString 可以直接给客户端请求，无需再做处理。
        orderStr = response.getBody();

        return orderStr;
    }

    /**
     * 生成支付二维码
     *
     * @param userId
     * @param detail
     * @param totalAmount
     * @return
     */
    public JSONObject generateQRCode(int userId, String detail, String totalAmount) throws Exception {
        JSONObject obj = new JSONObject();
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getServeUrl(), alipayConfig.getAppId(), alipayConfig.getAppPrivateKey(), alipayConfig.getDataFormat(), alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        String outTradeNo = getOutTradeNo(userId);
        request.setBizModel(getPayModel(true, outTradeNo, detail, totalAmount));
        request.setNotifyUrl(alipayConfig.getNotifyUrl());

        AlipayTradePrecreateResponse response = alipayClient.execute(request);

        if (response != null && "10000".equals(response.getCode())) {
            LogUtil.info(AlipayServiceImpl.class, "支付宝预下单成功: )");
            ZxingUtils.getQRCodeImge(response.getQrCode(), 256, alipayConfig.getQrCodeImgUrl());
            String newFileName = upload(alipayConfig.getQrCodeImgUrl());
            if (!StringUtil.isNullOrEmpty(newFileName)) {
                obj.put("url", newFileName);
                obj.put("tradeNo", outTradeNo);
            }
        } else if (response == null || "20000".equals(response.getCode())) {
            LogUtil.error(AlipayServiceImpl.class, "系统异常，预下单状态未知!!!");
        } else {
            LogUtil.error(AlipayServiceImpl.class, "支付宝预下单失败!!!");
        }

        return obj;
    }

    /**
     * 校验异步返回信息
     *
     * @param params 支付宝服务器返回参数
     * @return
     * @throws AlipayApiException
     */
    public boolean receiveNotify(Map<String, String> params) throws AlipayApiException {
        //切记alipaypublickey是支付宝的公钥,不是应用公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        return AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
    }

    /**
     * 获取交易码
     *
     * @param userId
     * @return
     */
    private String getOutTradeNo(int userId) {
        return Integer.toString(userId) + "_" + System.currentTimeMillis() + "_" + RandomNumUtil.getRandNum(6);
    }

    /**
     * 获取支付参数
     *
     * @param isF2F       是否是当面付
     * @param outTradeNo  支付码
     * @param detail      明细
     * @param totalAmount 支付总额
     * @return
     */
    private AlipayTradeAppPayModel getPayModel(boolean isF2F, String outTradeNo, String detail, String totalAmount) {
        //SDK已经封装掉了公共参数(即alipayClient)，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下request取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(detail);  //明细
        model.setSubject("0");  //缴纳方式 0：支付宝 1：微信 2：其他
        model.setOutTradeNo(outTradeNo);  //交易号码
        model.setTimeoutExpress(MODEL_TIMEOUT_EXPRESS);
        model.setTotalAmount(totalAmount);  //金额
        if (isF2F) {
            //当面付
            model.setStoreId(alipayConfig.getStoreId());
        } else {
            //app转支付宝支付
            model.setProductCode(MODEL_PRODUCT_CODE);
        }

        return model;
    }

    /**
     * 上传文件
     *
     * @param filePath
     * @return
     */
    private String upload(String filePath) throws RuntimeException {
        String newFileName = null;
        LocalDate today = LocalDate.now();
        String todayStr = "" + today.getYear() + today.getMonthValue() + today.getDayOfMonth();
        try {
            File file = new File(filePath);
            newFileName = IMG_FOLDER + "/" + todayStr + "/" + UUID.randomUUID().toString().replace("-", "") + ".png";
            this.ftpOperation.upload(Files.readAllBytes(file.toPath()), newFileName);
            if (ftpConfig.getUrl().endsWith("/")) {
                newFileName = ftpConfig.getUrl() + newFileName;
            } else {
                newFileName = ftpConfig.getUrl() + "/" + newFileName;
            }
        } catch (IOException e) {
            LogUtil.error(AlipayServiceImpl.class, "二维码图片上传失败" + e.toString());
            throw new RuntimeException();
        }

        return newFileName;
    }
}
