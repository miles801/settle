package com.michael.settle.conf.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 阶梯比例
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_step_percent")
public class StepPercent extends CommonDomain {
    @ApiField(value = "文交所", desc = "参数:VIP_COMPANY")
    @NotNull
    @Column(name = "company", nullable = false, length = 40)
    private String company;

    @ApiField(value = "最小值")
    @NotNull
    @Column(name = "min_value", nullable = false)
    private Double minValue;

    @ApiField(value = "最大值")
    @NotNull
    @Column(name = "max_value", nullable = false)
    private Double maxValue;

    @ApiField(value = "比例")
    @NotNull
    @Column(name = "percent", nullable = false)
    private Double percent;


    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMinValue() {
        return this.minValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMaxValue() {
        return this.maxValue;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Double getPercent() {
        return this.percent;
    }


}
