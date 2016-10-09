package com.michael.settle.report.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 团队佣金
 *
 * @author Michael
 */
//@Entity
//@Table(name = "settle_group_bonus")
public class GroupBonus extends CommonDomain {
    @ApiField(value = "团队编号")
    @NotNull
    @Column(name = "groupCode", nullable = false, length = 20)
    private String groupCode;

    @ApiField(value = "团队名称")
    @NotNull
    @Column(name = "groupName", nullable = false, length = 20)
    private String groupName;

    @ApiField(value = "文交所", desc = "参数:VIP_COMPANY")
    @NotNull
    @Column(name = "company", nullable = false, length = 40)
    private String company;

    @ApiField(value = "统计时间")
    @NotNull
    @Column(name = "occurDate", nullable = false)
    private Date occurDate;

    @ApiField(value = "成交额")
    @NotNull
    @Column(name = "totalMoney", nullable = false)
    private Double totalMoney;

    @ApiField(value = "交易手续费")
    @NotNull
    @Column(name = "fee", nullable = false)
    private Double fee;

    @ApiField(value = "标准佣金")
    @NotNull
    @Column(name = "commission", nullable = false)
    private Double commission;

    @ApiField(value = "阶梯比例")
    @NotNull
    @Column(name = "stepPercent", nullable = false)
    private Double stepPercent;

    @ApiField(value = "含税服务费")
    @NotNull
    @Column(name = "taxServerFee", nullable = false)
    private Double taxServerFee;

    @ApiField(value = "设定比例")
    @NotNull
    @Column(name = "percent", nullable = false)
    private Double percent;

    @ApiField(value = "支付金额")
    @Column(name = "payMoney", nullable = false)
    private Double payMoney;

    @ApiField(value = "除税支付金额")
    @NotNull
    @Column(name = "outofTax", nullable = false)
    private Double outofTax;

    @ApiField(value = "税金")
    @Column(name = "tax", nullable = false)
    private Double tax;

    @ApiField(value = "备注")
    @Column(name = "description", length = 1000)
    private String description;


    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public Date getOccurDate() {
        return this.occurDate;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getTotalMoney() {
        return this.totalMoney;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getFee() {
        return this.fee;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getCommission() {
        return this.commission;
    }

    public void setStepPercent(Double stepPercent) {
        this.stepPercent = stepPercent;
    }

    public Double getStepPercent() {
        return this.stepPercent;
    }

    public void setTaxServerFee(Double taxServerFee) {
        this.taxServerFee = taxServerFee;
    }

    public Double getTaxServerFee() {
        return this.taxServerFee;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Double getPercent() {
        return this.percent;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getPayMoney() {
        return this.payMoney;
    }

    public void setOutofTax(Double outofTax) {
        this.outofTax = outofTax;
    }

    public Double getOutofTax() {
        return this.outofTax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTax() {
        return this.tax;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }


}
