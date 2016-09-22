package com.michael.settle.sms.bo;

import com.michael.core.hibernate.criteria.BO;
import com.michael.core.hibernate.criteria.Condition;
import com.michael.core.hibernate.criteria.MatchModel;

import java.util.Date;

/**
 * @author Michael
 */
public class SmsBuyBo implements BO {
    @Condition(matchMode = MatchModel.GE, target = "occurTime")
    private Date occurTime1;
    @Condition(matchMode = MatchModel.LT, target = "occurTime")
    private Date occurTime2;

    public Date getOccurTime1() {
        return occurTime1;
    }

    public void setOccurTime1(Date occurTime1) {
        this.occurTime1 = occurTime1;
    }

    public Date getOccurTime2() {
        return occurTime2;
    }

    public void setOccurTime2(Date occurTime2) {
        this.occurTime2 = occurTime2;
    }
}
