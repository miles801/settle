package com.michael.settle.vip.bo;

import com.michael.core.hibernate.criteria.BO;
import com.michael.core.hibernate.criteria.Condition;
import com.michael.core.hibernate.criteria.LikeModel;
import com.michael.core.hibernate.criteria.MatchModel;

import java.util.Date;

/**
 * @author Michael
 */
public class BusinessBo implements BO {
    // 交易商代码
    @Condition
    private String vipCode;

    // 团队编号
    @Condition
    private String groupCode;

    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String groupName;

    // 交易金额
    @Condition
    private Double money;

    // 文交所
    @Condition
    private String company;

    // 手续费
    @Condition
    private Date fee;

    // 交易时间
    @Condition
    private Date businessTime;


    @Condition
    private Boolean deleted;

    @Condition
    private String creatorId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public void setFee(Date fee) {
        this.fee = fee;
    }

    public Date getFee() {
        return this.fee;
    }

    public void setBusinessTime(Date businessTime) {
        this.businessTime = businessTime;
    }

    public Date getBusinessTime() {
        return this.businessTime;
    }
}
