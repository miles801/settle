<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>编辑风评库设置</title>
    <meta content="text/html" charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript">
        window.angular.contextPathURL = "<%=contextPath%>";
    </script>
</head>
<body>
<div class="main" ng-app="itsm.otherSetting.edit" ng-controller="Ctrl">
    <div class="block">
        <div class="block-header">
                <span class="header-text">
                    <span class="glyphicons info-sign"></span>
                </span>
                <span class="header-button">
                    <c:if test="${pageType eq 'add'}">
                    <button type="button" class="btn btn-green btn-min" ng-click="save()" ng-disabled="form.$invalid">
                        <span class="glyphicons disk_save"></span> 保存
                    </button>
                        <button type="button" class="btn btn-green btn-min" ng-click="save(true)"
                                ng-disabled="form.$invalid">
                            <span class="glyphicons disk_open"></span> 保存并新建
                        </button>
                    </c:if>
                    <c:if test="${pageType eq 'modify'}">
                    <button type="button" class="btn btn-green btn-min" ng-click="update()" ng-disabled="form.$invalid">
                        <span class="glyphicons claw_hammer"></span> 更新
                    </button>
                    </c:if>
                    <a type="button" class="btn btn-green btn-min" ng-click="back();">
                        <span class="glyphicons message_forward"></span> 返回
                    </a>
                </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <form name="form" class="form-horizontal" role="form">
                    <div style="display: none;">
                        <input type="hidden" id="pageType" value="${pageType}"/>
                        <input type="hidden" id="id" value="${id}"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-3">
                            <label validate-error="form.code">编号:</label>
                        </div>
                        <input class="col-3" type="text" ng-model="beans.code" name="code"
                               validate validate-required maxlength="40"
                               placeholder="编号需要唯一，建议使用字母+数字的规则"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-3">
                            <label validate-error="form.type">类型:</label>
                        </div>
                        <select ng-model="beans.type" class="col-3" validate validate-required name="type"
                                ng-options="foo.value as foo.name for foo in types">
                        </select>
                    </div>
                    <div class="row">
                        <div class="form-label col-3">
                            <label validate-error="form.name">名称:</label>
                        </div>
                        <input class="col-3" type="text" ng-model="beans.name" name="name"
                               validate validate-required maxlength="40"
                               placeholder="同一类型的设置名称必须唯一"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-3">
                            <label validate-error="form.assetType">影响资产类型:</label>
                        </div>
                        <div class="col-3" ng-cloak>
                            <input type="hidden" validate validate-required ng-model="beans.assetType"
                                   name="assetType"/>
                            <label style="margin-left:{{$index==0?'0':'15px'}};" ng-repeat="foo in assetTypes">
                                <input type="checkbox" ng-model="foo.checked" ng-change="validate();"/><span
                                    style="margin-left: 2px;"> {{foo.name}}</span>
                            </label>
                        </div>
                    </div>
                    <c:if test="${pageType ne 'add'}">
                        <div class="row">
                            <div class="form-label col-3">
                                <label>创建人:</label>
                            </div>
                            <span class="col-3 sl" ng-cloak>{{beans.creatorName}}</span>
                        </div>
                        <div class="row">
                            <div class="form-label col-3">
                                <label>创建时间:</label>
                            </div>
                            <div class="col-3 sl" ng-cloak>{{beans.createdDatetime|eccrmDatetime}}</div>
                        </div>
                        <div class="row">
                            <div class="form-label col-3">
                                <label>最后更新人:</label>
                            </div>
                            <span class="col-3 sl" ng-cloak>{{beans.modifierName}}</span>
                        </div>
                        <div class="row">
                            <div class="form-label col-3">
                                <label>最后时间:</label>
                            </div>
                            <div class="col-3 sl" ng-cloak>{{beans.modifiedDatetime|eccrmDatetime}}</div>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/otherSetting/otherSetting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/otherSetting/edit/otherSetting_edit.js"></script>
</html>