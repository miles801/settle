package com.michael.settle.report.bo;

import com.michael.core.hibernate.criteria.BO;
import com.michael.core.hibernate.criteria.Condition;
import com.michael.core.hibernate.criteria.MatchModel;

import java.util.Date;

/**
 * @author Michael
 */
public class GroupVipBo implements BO {
    // 团队编号
    @Condition
    private String groupCode;

    // 团队名称
    @Condition
    private String groupName;

    // 文交所
    @Condition
    private String company;

    // 统计时间
    @Condition(matchMode = MatchModel.GE, target = "occurDate")
    private Date occurDate1;
    @Condition(matchMode = MatchModel.LT, target = "occurDate")
    private Date occurDate2;

    // 交易商数量
    @Condition(matchMode = MatchModel.GE)
    private Integer vipCounts;
    @Condition(matchMode = MatchModel.GE)
    private Integer normalCounts;
    @Condition(matchMode = MatchModel.GE)
    private Integer otherCounts;
    @Condition(matchMode = MatchModel.GE)
    private Integer assignCounts;
    @Condition(matchMode = MatchModel.GE)
    private Integer notAssignCounts;



    // 会员交易数量
    @Condition(matchMode = MatchModel.GE)
    private Integer businessCounts;

    @Condition(matchMode = MatchModel.GT)
    private Double totalMoney;
    @Condition(matchMode = MatchModel.GT)
    private Double fee;
    @Condition(matchMode = MatchModel.GT)
    private Double commission;
    @Condition(matchMode = MatchModel.GT)
    private Double stepPercent;
    @Condition(matchMode = MatchModel.GT)
    private Double taxServerFee;
    @Condition(matchMode = MatchModel.GT)
    private Double percent;
    @Condition(matchMode = MatchModel.GT)
    private Double payMoney;
    @Condition(matchMode = MatchModel.GT)
    private Double outofTax;
    @Condition(matchMode = MatchModel.GT)
    private Double tax;

    // 是否返佣
    @Condition
    private Boolean bonus;
    // 是否发送了短信
    @Condition
    private Boolean sendSms;

    public Integer getVipCounts() {
        return vipCounts;
    }

    public void setVipCounts(Integer vipCounts) {
        this.vipCounts = vipCounts;
    }

    public Integer getNormalCounts() {
        return normalCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getOtherCounts() {
        return otherCounts;
    }

    public void setOtherCounts(Integer otherCounts) {
        this.otherCounts = otherCounts;
    }

    public Integer getAssignCounts() {
        return assignCounts;
    }

    public void setAssignCounts(Integer assignCounts) {
        this.assignCounts = assignCounts;
    }

    public Integer getNotAssignCounts() {
        return notAssignCounts;
    }

    public void setNotAssignCounts(Integer notAssignCounts) {
        this.notAssignCounts = notAssignCounts;
    }

    public Boolean getBonus() {
        return bonus;
    }

    public void setBonus(Boolean bonus) {
        this.bonus = bonus;
    }

    public Boolean getSendSms() {
        return sendSms;
    }

    public void setSendSms(Boolean sendSms) {
        this.sendSms = sendSms;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

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

    public Integer getBusinessCounts() {
        return businessCounts;
    }

    public void setBusinessCounts(Integer businessCounts) {
        this.businessCounts = businessCounts;
    }

    public Date getOccurDate1() {
        return occurDate1;
    }

    public void setOccurDate1(Date occurDate1) {
        this.occurDate1 = occurDate1;
    }

    public Date getOccurDate2() {
        return occurDate2;
    }

    public void setOccurDate2(Date occurDate2) {
        this.occurDate2 = occurDate2;
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

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }


}
