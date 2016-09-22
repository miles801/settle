package com.michael.settle.vip.dto;

import com.michael.poi.annotation.Col;
import com.michael.poi.annotation.ImportConfig;
import com.michael.poi.core.DTO;

/**
 * @author Michael
 */
@ImportConfig(file = "", startRow = 1)
public class ContactDTO implements DTO {
    // 姓名
    @Col(index = 0)
    private String name;
    // 手机
    @Col(index = 1)
    private String mobile;
    // 所属团队
    @Col(index = 2)
    private String groupId;
    // 所属文交所
    private String company;
    // 返佣银行
    @Col(index = 3)
    private String bank;
    // 银行账户
    @Col(index = 4)
    private String bankAccount;

    @Col(index = 5)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }
}
