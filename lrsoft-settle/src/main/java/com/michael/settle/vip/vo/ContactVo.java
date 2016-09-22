package com.michael.settle.vip.vo;

import com.michael.settle.vip.domain.Contact;

/**
 * @author Michael
 */
public class ContactVo extends Contact {
    // 所属文交所--参数名称
    private String companyName;
    // 返佣银行--参数名称
    private String bankName;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return this.bankName;
    }
}
