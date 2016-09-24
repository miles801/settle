package com.michael.settle.report.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 交易历史
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_business_log")
public class BusinessLog extends CommonDomain {
    @ApiField(value = "文交所", desc = "参数:VIP_COMPANY")
    @NotNull
    @Column(name = "company", nullable = false, length = 40)
    private String company;

    @ApiField(value = "团队编号")
    @NotNull
    @Column(name = "groupCode", nullable = false, length = 40)
    private String groupCode;

    @ApiField(value = "团队名称")
    @NotNull
    @Column(name = "groupName", nullable = false, length = 40)
    private String groupName;

    @ApiField(value = "会员名称")
    @NotNull
    @Column(name = "vipName", nullable = false, length = 40)
    private String vipName;

    @ApiField(value = "交易额")
    @NotNull
    @Column(name = "money", nullable = false)
    private Double money;

    @ApiField(value = "开户时间")
    @NotNull
    @Column(name = "occurDate", nullable = false)
    private Date occurDate;

    @ApiField(value = "推荐人")
    @Column(name = "recommend", length = 40)
    private String recommend;


    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipName() {
        return this.vipName;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getMoney() {
        return this.money;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public Date getOccurDate() {
        return this.occurDate;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRecommend() {
        return this.recommend;
    }


}
