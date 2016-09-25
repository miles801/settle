package com.michael.settle.vip.bo;

import com.michael.core.hibernate.criteria.BO;
import com.michael.core.hibernate.criteria.Condition;
import com.michael.core.hibernate.criteria.LikeModel;
import com.michael.core.hibernate.criteria.MatchModel;

/**
 * @author Michael
 */
public class ContactBo implements BO {
    // 姓名
    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String name;

    // 手机
    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String mobile;

    // 所属团队
    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String groupId;

    // 文交所
    @Condition
    private String company;

    // 返佣银行
    @Condition
    private String bank;

    // 银行账户
    @Condition
    private String bankAccount;


    @Condition
    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }
}
