package com.michael.settle.conf.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 比例配置
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_company_conf")
public class CompanyConf extends CommonDomain {
    @ApiField(value = "文交所", desc = "参数:VIP_COMPANY")
    @NotNull
    @Column(name = "company", nullable = false, length = 40, updatable = false)
    private String company;

    @ApiField(value = "标准佣金")
    @NotNull
    @Column(name = "commission", nullable = false, length = 10)
    private String commission;

    @ApiField(value = "设定比例")
    @NotNull
    @Column(name = "percent", nullable = false)
    private Double percent;

    @ApiField(value = "税率")
    @NotNull
    @Column(name = "tax", nullable = false)
    private Double tax;


    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Double getPercent() {
        return this.percent;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTax() {
        return this.tax;
    }


}
