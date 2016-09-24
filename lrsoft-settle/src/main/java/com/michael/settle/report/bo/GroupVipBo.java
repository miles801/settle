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

    // 所属文交所
    @Condition
    private String company;

    // 统计时间
    @Condition(matchMode = MatchModel.GE, target = "occurDate")
    private Date occurDate1;
    @Condition(matchMode = MatchModel.LT, target = "occurDate")
    private Date occurDate2;

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
