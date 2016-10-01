package com.alidayu.utils;

/**
 * @author Michael
 */
public class SmsSendResponse {
    /**
     * id
     */
    private String id;
    /**
     * 是否成功：一定存在
     */
    private boolean success;
    /**
     * 错误代码：可能为空
     */
    private String errorCode;
    /**
     * 错误消息：一定存在
     */
    private String errorMsg;
    /**
     * 发送的内容
     */
    private String content;
    /**
     * 接收号码
     */
    private String mobile;

    public SmsSendResponse() {
    }

    public SmsSendResponse(boolean success) {
        this.success = success;
    }

    public SmsSendResponse(boolean success, String errorCode, String errorMsg) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public SmsSendResponse(boolean success, String errorCode, String errorMsg, String mobile, String content) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.content = content;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
