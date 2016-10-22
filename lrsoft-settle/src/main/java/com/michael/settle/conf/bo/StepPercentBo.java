package com.michael.settle.conf.bo;

import com.michael.core.hibernate.criteria.BO;
import com.michael.core.hibernate.criteria.Condition;

/**
 * @author Michael
 */
public class StepPercentBo implements BO {
    // 文交所
    @Condition
    private String company;

    @Condition
    private String orgId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }
}
