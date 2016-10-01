package com.michael.settle.conf.service;

import com.alidayu.utils.SMSSender;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author Michael
 */
public class BonusUtils {

    private static String templateId = null;

    static {
        InputStream stream = BonusUtils.class.getClassLoader().getResourceAsStream("config.properties");
        Assert.notNull(stream, "短信接口初始化失败!未获取到配置文件[config.properties]!");
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        templateId = properties.getProperty("settle.sms.bonus");
        Assert.hasText(templateId, "短信接口初始化失败!未正确获取参数:settle.sms.bonus");
    }

    /**
     * 发送佣金提醒短信：
     *
     * @param mobile 电话号码
     * @param params 参数
     * @return 结果
     */
    public static String sendSms(String mobile, Map<String, String> params) {
        String errorMsg = null;
        String result = SMSSender.getInstance().send(templateId, mobile, params);
        Gson gson = new Gson();
        Map map = gson.fromJson(result, Map.class);
        Object error_response = map.get("error_response");
        if (error_response != null) {
            if (error_response instanceof LinkedTreeMap) {
                LinkedTreeMap treeMap = (LinkedTreeMap) error_response;
                errorMsg = (String) treeMap.get("sub_msg");
            }
        }
        return errorMsg;
    }

}
