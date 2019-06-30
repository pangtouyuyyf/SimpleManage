package com.simple.manage.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.simple.manage.system.config.SmsConfig;
import com.simple.manage.system.service.SmsService;
import com.simple.manage.system.util.LogUtil;
import com.simple.manage.system.util.RandomNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description 短信服务接口实现
 * Author chen
 * CreateTime 2019-06-30 15:32
 **/
@Service
public class SmsServiceImpl implements SmsService {
    //产品名称:云通信短信API产品,开发者无需替换
    private static final String PRODUCT = "Dysmsapi";

    //产品域名,开发者无需替换
    private static final String SYS_DOMAIN = "dysmsapi.aliyuncs.com";

    private static final String SYS_VERSION = "2017-05-25";

    private static final String SYS_ACTION = "SendSms";

    //outId为提供给业务方扩展字段
    private static final String OUT_ID = "10086";

    //节点
    private static final String REGION_ID = "cn-hangzhou";

    //短信发送成功返回码
    private static final String MSG_CODE = "OK";

    @Autowired
    private SmsConfig smsConfig;

    /**
     * 发送短信服务
     *
     * @param phoneNums    手机号码
     * @param signName     签名
     * @param templateCode 模板ID
     * @param template     模板替换内容(json)
     */
    public CommonResponse sendSms(String phoneNums, String signName, String templateCode, String template) {
        //可自助调整超时时间
//        System.setProperty("sun.net.client.defaultConnectTimeout", this.smsConfig.getTimeOut());
//        System.setProperty("sun.net.client.defaultReadTimeout", this.smsConfig.getTimeOut());

        IAcsClient acsClient = null;
        CommonRequest request = null;
        CommonResponse response = null;

        try {
            //初始化acsClient,暂不支持region化
//            DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);
            IClientProfile profile = DefaultProfile.getProfile(REGION_ID, this.smsConfig.getAccessKeyId(), this.smsConfig.getAccessKeySecret());

            acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            request = new CommonRequest();

            request.setSysMethod(MethodType.POST);
            request.setSysDomain(SYS_DOMAIN);
            request.setSysVersion(SYS_VERSION);
            request.setSysAction(SYS_ACTION);

            request.putQueryParameter("RegionId", REGION_ID);

            //必填:待发送手机号支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,
            //批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.putQueryParameter("PhoneNumbers", phoneNums);

            //必填:短信签名-可在短信控制台中找到
            request.putQueryParameter("SignName", signName);

            //必填:短信模板-可在短信控制台中找到
            request.putQueryParameter("TemplateCode", templateCode);

            //String message = "{system:\"" + system + "\", task:\"" + task + "\",tasktype:\"" + taskType + "\",action:\"" + action + "\"}";
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.putQueryParameter("TemplateParam", template);

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
//            request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//            request.setOutId(OUT_ID);

            //hint 此处可能会抛出异常，注意catch
            response = acsClient.getCommonResponse(request);
        } catch (ServerException e) {
            LogUtil.error(SmsServiceImpl.class, e.toString());
        } catch (ClientException e) {
            LogUtil.error(SmsServiceImpl.class, e.toString());
        }
        return response;
    }

    /**
     * 发送短信验证码
     *
     * @param phoneNum 手机号码
     * @return
     */
    public String sendVerifySms(String phoneNum) {
        //获取验证码
        String verifyCode = RandomNumUtil.getRandNum(5);

        //您正在登录验证，验证码${verify}，请在1分钟内按规范提交验证码，切勿将验证码泄露于他人。
        //String message = "{verify:\"" + verifyCode + "\", limit:\"" + Integer.toString(this.smsConfig.getVerifyTimeOut() / 60) + "\",notice:\"" + "提示" + "\"}";

        JSONObject message = new JSONObject();
        message.put("verify", verifyCode);

        //发送短信
        CommonResponse response = sendSms(phoneNum, this.smsConfig.getSignName(), "SMS_119077853", message.toString());

        //检测response
        if (!MSG_CODE.equals(response.getData())) {
            //发送失败
            verifyCode = null;
            String errMsg = "号码为" + phoneNum + "的手机短信发送异常:" + response.getData();
            LogUtil.error(SmsServiceImpl.class, errMsg);
        }

        return verifyCode;
    }
}
