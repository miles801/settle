package com.michael.settle.vip.dto;

import com.michael.poi.annotation.Col;
import com.michael.poi.annotation.ImportConfig;
import com.michael.poi.core.DTO;

import java.util.Date;

/**
 * @author Michael
 */
@ImportConfig(file = "", startRow = 1)
public class BusinessDTO implements DTO {
    // 交易商代码
    @Col(index = 0)
    private String vipCode;
    private String vipName;
    // 团队编号
    @Col(index = 1)
    private String groupCode;
    // 交易金额
    @Col(index = 2)
    private Double money;
    // 文交所
    @Col(index = 3)
    private String company;
    // 手续费
    @Col(index = 4)
    private Double fee;
    // 交易时间
    @Col(index = 5)
    private Date businessTime;

    // 备注
    private String description;

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
