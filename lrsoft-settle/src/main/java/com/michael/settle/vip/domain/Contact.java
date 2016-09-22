package com.michael.settle.vip.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 通讯录
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_contact")
public class Contact extends CommonDomain {
    @ApiField(value = "姓名")
    @NotNull
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @ApiField(value = "手机")
    @NotNull
    @Column(name = "mobile", nullable = false, length = 20)
    private String mobile;

    @ApiField(value = "所属团队")
    @Column(name = "groupId", length = 40)
    private String groupId;

    @ApiField(value = "所属文交所", desc = "参数:VIP_COMPANY")
    @Column(name = "company", length = 40)
    private String company;

    @ApiField(value = "返佣银行", desc = "参数:BANK")
    @Column(name = "bank", length = 40)
    private String bank;

    @ApiField(value = "银行账户")
    @Column(name = "bankAccount", length = 40)
    private String bankAccount;


    @Column(length = 1000)
    private String description;
    @NotNull
    @Column
    private Boolean deleted;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
