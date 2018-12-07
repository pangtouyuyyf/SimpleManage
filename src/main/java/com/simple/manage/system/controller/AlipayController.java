package com.simple.manage.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.simple.manage.system.annotation.TokenAnnotation;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.AlipayService;
import com.simple.manage.system.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description 支付宝支付
 * Author chen
 * CreateTime 2018-12-07 13:58
 **/
@RestController
@RequestMapping(value = "/sys/alipay")
public class AlipayController extends BaseController {
    @Autowired
    private AlipayService alipayService;

    /**
     * 手机app端生成订单信息
     *
     * @param detail 明细[{time:,money:,type:,note:},{}] time:年月 money:金额 type:费用类型 note:备注
     * @param money  金额(RMB)
     * @return
     */
    @TokenAnnotation
    @PostMapping(value = "/generateOrder")
    public Result generateOrder(@RequestParam("detail") String detail,
                                @RequestParam("money") String money) throws Exception {
        Result result = null;
        String orderStr = this.alipayService.generateOrders(getLoginInfo().getUser().getId(), detail, money);
        if (StringUtil.isNotEmpty(orderStr)) {
            result = success(orderStr);
        } else {
            result = fail("订单生成失败");
        }
        return result;
    }

    /**
     * 服务端生成二维码展示
     *
     * @param detail 明细[{time:,money:,type:,note:},{}]  time:年月 money:金额 type:费用类型 note:备注
     * @param money  金额(RMB)
     * @return
     */
    @PostMapping(value = "/generateQRCode")
    public Result<Object> generateQRCode(@RequestParam("detail") String detail,
                                         @RequestParam("money") String money) throws Exception {
        Result result = null;
        JSONObject obj = this.alipayService.generateQRCode(getLoginInfo().getUser().getId(), detail, money);
        if (obj != null && !obj.isEmpty()) {
            result = success(obj);
        } else {
            result = fail("订单二维码生成失败");
        }
        return result;
    }

    /**
     * 服务端验证异步通知信息
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/receiveAlipayNotify")
    public String receiveAlipayNotify(HttpServletRequest request, HttpServletResponse response) {
        LogUtil.info(AlipayController.class, "支付宝异步回调开始");
        String result = "failure";
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            //验证通过将费用明细写入费用表
            boolean flag = this.alipayService.receiveNotify(params);
            if (flag) {
                //保存费用信息 TODO
//                saveExpenses(params);
                result = "success";
            } else {
                LogUtil.error(AlipayController.class, "支付宝回调信息签名验证失败");
            }
        } catch (Exception e) {
            LogUtil.error(AlipayController.class, e.toString());
        }

        return result;
    }

    /**
     * 保存交易明细
     *
     * @param params
     */
    private void saveExpenses(Map<String, String> params) {
        String tradeNo = params.get("trade_no");  //订单号码
        String outTradeNo = params.get("out_trade_no");  //自定义订单号码
        int userId = Integer.valueOf(outTradeNo.split("_")[0]);  //自定义交易号码保存有用户ID
        JSONArray detail = JSONArray.parseArray(params.get("body"));  //订单明细

        List<Map<String, Object>> existExpense = null;
        if (existExpense != null && existExpense.isEmpty()) {
            //该交易记录没有录入，防止多次回调
        }
    }
}
