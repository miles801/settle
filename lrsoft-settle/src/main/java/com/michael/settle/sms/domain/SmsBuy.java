package com.michael.settle.sms.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 短信充值记录
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_sms_buy")
public class SmsBuy extends CommonDomain {
    @ApiField(value = "充值时间")
    @NotNull
    @Column(name = "occurTime", nullable = false)
    private Date occurTime;

    @ApiField(value = "充值数量")
    @NotNull
    @Column(name = "counts", nullable = false)
    private Integer counts;

    @ApiField(value = "充值金额")
    @Column(name = "money")
    private Double money;

    @ApiField(value = "备注")
    @Column(name = "description", length = 1000)
    private String description;


    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public Date getOccurTime() {
        return this.occurTime;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Integer getCounts() {
        return this.counts;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getMoney() {
        return this.money;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }


}
