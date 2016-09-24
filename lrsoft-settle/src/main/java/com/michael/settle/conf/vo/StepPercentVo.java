package com.michael.settle.conf.vo;

import com.michael.settle.conf.domain.StepPercent;

/**
 * @author Michael
 */
public class StepPercentVo extends StepPercent {
    // 文交所--参数名称
    private String companyName;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }
}
