<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>编辑业务应用设置</title>
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
<div class="main" ng-app="itsm.appSetting.edit" ng-controller="Ctrl">
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
                        <div class="form-label col-2">
                            <label>系统名称:</label>
                        </div>
                        <input class="col-2-half" type="text" ng-model="beans.name"
                               validate validate-required maxlength="20"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-2">
                            <label>系统说明:</label>
                        </div>
                        <textarea class="col-6-half" rows="3" ng-model="beans.description" maxlength="500"
                                  validate validate-required></textarea>
                    </div>
                    <div class="row">
                        <div class="form-label col-2">
                            <label validate-error="form.important">重要性:</label>
                        </div>
                        <select ng-model="beans.important" class="col-2-half" name="important"
                                ng-options="foo.value as foo.name for foo in importants"
                                validate validate-required></select>
                        <div class="form-label col-1-half">
                            <label validate-error="form.level">等保级别:</label>
                        </div>
                        <select ng-model="beans.level" class="col-2-half" name="level"
                                ng-options="foo.value as foo.name for foo in levels"
                                validate validate-required></select>
                    </div>
                    <div class="row">
                        <div class="form-label col-2">
                            <label>可用性要求:</label>
                        </div>
                        <textarea class="col-6-half" rows="3" ng-model="beans.required" maxlength="500"></textarea>
                    </div>
                    <c:if test="${pageType ne 'add'}">
                        <div class="row">
                            <div class="form-label col-2">
                                <label>创建人:</label>
                            </div>
                            <span class="col-2-half">{{beans.creatorName}}</span>
                            <div class="form-label col-2">
                                <label>创建时间:</label>
                            </div>
                            <div class="col-2-half">{{beans.createdDatetime|eccrmDatetime}}</div>
                        </div>
                        <div class="row">
                            <div class="form-label col-2">
                                <label>最后更新人:</label>
                            </div>
                            <span class="col-2-half">{{beans.modifierName}}</span>
                            <div class="form-label col-2">
                                <label>最后时间:</label>
                            </div>
                            <div class="col-2-half">{{beans.modifiedDatetime|eccrmDatetime}}</div>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/appSetting/appSetting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/appSetting/edit/appSetting_edit.js"></script>
</html>