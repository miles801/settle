package com.michael.settle.vip.bo;

import com.michael.core.hibernate.criteria.BO;
import com.michael.core.hibernate.criteria.Condition;
import com.michael.core.hibernate.criteria.LikeModel;
import com.michael.core.hibernate.criteria.MatchModel;

/**
 * @author Michael
 */
public class VipBo implements BO {
    // 编号
    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String code;

    // 名称
    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String name;

    // 所属团队
    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String groupId;

    // 签约状态
    @Condition
    private String assignStatus;

    // 状态
    @Condition
    private String status;

    // 所属文交所
    @Condition
    private String company;

    // 推荐人
    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String recommend;

    @Condition
    private String creatorId;

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public String getAssignStatus() {
        return this.assignStatus;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRecommend() {
        return this.recommend;
    }
}
