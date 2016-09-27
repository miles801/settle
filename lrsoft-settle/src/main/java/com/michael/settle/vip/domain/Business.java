package com.michael.settle.vip.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 交易
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_business")
public class Business extends CommonDomain {
    @ApiField(value = "交易商代码")
    @NotNull
    @Column(name = "vipCode", nullable = false, length = 40)
    private String vipCode;

    @ApiField("会员名称")
    @Column(length = 40)
    private String vipName;

    @ApiField(value = "团队编号")
    @NotNull
    @Column(name = "groupCode", nullable = false, length = 40)
    private String groupCode;

    @ApiField(value = "团队名称")
    @Column(name = "groupName", length = 100)
    private String groupName;

    @ApiField(value = "交易金额")
    @NotNull
    @Column(name = "money", nullable = false)
    private Double money;

    @ApiField(value = "文交所", desc = "参数:VIP_COMPANY")
    @Column(name = "company", length = 40)
    private String company;

    @ApiField(value = "手续费")
    @Column(name = "fee")
    private Double fee;

    @ApiField(value = "交易时间")
    @Column(name = "businessTime")
    private Date businessTime;

    @ApiField("备注")
    @Column(length = 1000)
    private String description;
    @NotNull
    @Column
    private Boolean deleted;

    @ApiField("批次号")
    @Column(length = 20)
    private String batchNo;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

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

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public String getVipCode() {
        return this.vipCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getMoney() {
        return this.money;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public void setBusinessTime(Date businessTime) {
        this.businessTime = businessTime;
    }

    public Date getBusinessTime() {
        return this.businessTime;
    }


}
