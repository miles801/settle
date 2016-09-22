package com.michael.settle.sms.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 短信发送记录
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_sms_send")
public class SmsSend extends CommonDomain {
    @ApiField(value = "手机号")
    @NotNull
    @Column(name = "mobile", nullable = false, length = 20)
    private String mobile;

    @ApiField(value = "发送内容")
    @NotNull
    @Column(name = "content", nullable = false, length = 400)
    private String content;

    @ApiField(value = "是否成功")
    @NotNull
    @Column(name = "success", nullable = false)
    private Boolean success;


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return this.success;
    }


}
