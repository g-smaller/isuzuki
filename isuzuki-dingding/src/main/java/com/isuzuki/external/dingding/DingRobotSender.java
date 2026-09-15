package com.isuzuki.external.dingding;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DingRobotSender {

    private Logger logger = LoggerFactory.getLogger(DingRobotSender.class);

    private static final String DING_ROBOT_NOTIFY_URL = "https://oapi.dingtalk.com/robot/send?access_token=";

    private String charset = StandardCharsets.UTF_8.name();
    private final DingRobotClient dingRobotClient;
    public DingRobotSender(DingRobotClient dingRobotClient) {
        this.dingRobotClient = dingRobotClient;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * 发送钉钉机器人消息 markdown形式
     *
     * @param title        标题
     * @param text         文本
     * @param atCellphones 被@手机号列表
     */
    public void sendMarkdownContent(String accessToken, String title, String text, List<String> atCellphones) {
        sendMarkdownContent(accessToken, "", title, text, atCellphones);
    }
    public void sendMarkdownContent(String accessToken, String secret, String title, String text, List<String> atCellphones) {
        if (StringUtils.isNotBlank(accessToken)) {
            Map<String, Object> params = new HashMap<>(8);
            params.put("msgtype", "markdown");

            if (!CollectionUtils.isEmpty(atCellphones)) {
                Map<String, Object> atMap = new HashMap<>(4);
                atMap.put("atMobiles", atCellphones);
                atMap.put("isAtAll", false);
                params.put("at", atMap);
                for (String cellphone : atCellphones) {
                    text += "@" + cellphone + " ";
                }
            }

            Map<String, Object> markdownMap = new HashMap<>(4);
            markdownMap.put("title", title);
            markdownMap.put("text", text);
            params.put("markdown", markdownMap);

            try {
                String requestUrl = buildUrl(accessToken, secret);
                String responseData = dingRobotClient.send(requestUrl, params);
                logger.info("send ding robot notify result...,response:{},title:{},text:{},atCellphones:{}",
                        responseData, title, text, atCellphones);
            }catch (Exception e) {
                logger.info("send ding robot notify result ERROR...,title:{},text:{},atCellphones:{}",
                        title, text, atCellphones);
            }
        }
    }


    private String buildUrl(String accessToken, String secret) throws Exception {
        if (StringUtils.isBlank(secret)) {
            return DING_ROBOT_NOTIFY_URL + accessToken;
        }
        long timestamp = System.currentTimeMillis();
        String sign = sign(timestamp, secret);
        return DING_ROBOT_NOTIFY_URL + accessToken + "&timestamp=" + timestamp + "&sign=" + sign;
    }

    private String sign(Long timestamp, String secret) throws Exception {
        // Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(charset), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(charset));
        return URLEncoder.encode(Base64.getEncoder().encodeToString(signData), charset);
    }

}
