package com.michael.settle.report.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 团队会员
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_group_vip")
public class GroupVip extends CommonDomain {
    @ApiField(value = "团队编号")
    @NotNull
    @Column(name = "groupCode", nullable = false, length = 20)
    private String groupCode;

    @ApiField(value = "团队名称")
    @NotNull
    @Column(name = "groupName", nullable = false, length = 20)
    private String groupName;

    @ApiField(value = "交易商数量")
    @NotNull
    @Column(name = "vipCounts", nullable = false)
    private Integer vipCounts;

    @ApiField(value = "正常数量")
    @NotNull
    @Column(name = "normalCounts", nullable = false)
    private Integer normalCounts;

    @ApiField(value = "其他数量")
    @NotNull
    @Column(name = "otherCounts", nullable = false)
    private Integer otherCounts;

    @ApiField(value = "已签约数量")
    @NotNull
    @Column(name = "assignCounts", nullable = false)
    private Integer assignCounts;

    @ApiField(value = "未签约数量")
    @NotNull
    @Column(name = "notAssignCounts", nullable = false)
    private Integer notAssignCounts;

    @ApiField(value = "有交易的会员数量")
    @Column
    private Integer businessCounts;

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
    @Column(name = "totalMoney")
    private Double totalMoney;

    @ApiField(value = "交易手续费")
    @Column(name = "fee")
    private Double fee;

    @ApiField(value = "标准佣金")
    @Column(name = "commission")
    private Double commission;

    @ApiField(value = "阶梯比例")
    @Column(name = "stepPercent")
    private Double stepPercent;

    @ApiField(value = "含税服务费")
    @Column(name = "taxServerFee")
    private Double taxServerFee;

    @ApiField(value = "设定比例")
    @Column(name = "percent")
    private Double percent;

    @ApiField(value = "支付金额")
    @Column(name = "payMoney")
    private Double payMoney;

    @ApiField(value = "除税支付金额")
    @Column(name = "outofTax")
    private Double outofTax;

    @ApiField(value = "税金")
    @Column(name = "tax")
    private Double tax;

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getStepPercent() {
        return stepPercent;
    }

    public void setStepPercent(Double stepPercent) {
        this.stepPercent = stepPercent;
    }

    public Double getTaxServerFee() {
        return taxServerFee;
    }

    public void setTaxServerFee(Double taxServerFee) {
        this.taxServerFee = taxServerFee;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getOutofTax() {
        return outofTax;
    }

    public void setOutofTax(Double outofTax) {
        this.outofTax = outofTax;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getBusinessCounts() {
        return businessCounts;
    }

    public void setBusinessCounts(Integer businessCounts) {
        this.businessCounts = businessCounts;
    }

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

    public void setVipCounts(Integer vipCounts) {
        this.vipCounts = vipCounts;
    }

    public Integer getVipCounts() {
        return this.vipCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getNormalCounts() {
        return this.normalCounts;
    }

    public void setOtherCounts(Integer otherCounts) {
        this.otherCounts = otherCounts;
    }

    public Integer getOtherCounts() {
        return this.otherCounts;
    }

    public void setAssignCounts(Integer assignCounts) {
        this.assignCounts = assignCounts;
    }

    public Integer getAssignCounts() {
        return this.assignCounts;
    }

    public void setNotAssignCounts(Integer notAssignCounts) {
        this.notAssignCounts = notAssignCounts;
    }

    public Integer getNotAssignCounts() {
        return this.notAssignCounts;
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


}
