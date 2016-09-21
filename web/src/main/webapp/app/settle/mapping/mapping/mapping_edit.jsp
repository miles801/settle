<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>映射编辑</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
    <style>
        .red {
            border: 1px solid red;
        }
    </style>
</head>
<body>
<div class="main" ng-app="settle.mapping.mapping.edit" ng-controller="Ctrl" style="overflow: auto;">
    <div class="block">
        <div class="block-header">
                <span class="header-button">
                    <c:if test="${pageType eq 'add'}">
                        <a class="btn btn-green btn-min" ng-click="save()" ng-disabled="!form.$valid">保存 </a>
                        <a class="btn btn-green btn-min" ng-click="save(true)" ng-disabled="!form.$valid">保存并新建 </a>
                    </c:if>
                    <c:if test="${pageType eq 'modify'}">
                        <a type="button" class="btn btn-green btn-min" ng-click="update()" ng-disabled="!form.$valid"> 更新 </a>
                    </c:if>
                    <a class="btn btn-green btn-min" ng-click="back();">返回 </a>
                </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <form name="form" class="form-horizontal" role="form">
                    <div style="display: none;">
                        <input type="hidden" id="pageType" value="${pageType}"/>
                        <input type="hidden" id="id" value="${id}"/>
                    </div>
                    <div class="row float">
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.company">文交所:</label>
                            </div>
                            <select class="w200" ng-model="beans.company" name="company" validate validate-required
                                    ng-options="foo.value as foo.name for foo in companys">
                            </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.name">表名称:</label>
                            </div>
                            <select class="w200" ng-model="beans.name" name="name" validate validate-required
                                    ng-options="foo.value as foo.name for foo in names"
                                    ng-change="onNameChange();">
                            </select>
                        </div>
                        <div class="ycrl split" style="border-width: 1px;margin: 10px auto;"></div>
                        <h4 style="width: 100%;padding-left: 100px;color: #f55d09;font-size: 14px;">
                            **请输入各属性在Excel中的列号，从1开始</h4>
                        <div ng-repeat="foo in tables" ng-cloak class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.name">{{foo.name}}:</label>
                            </div>
                            <input type="number" class="w200" ng-model="foo.index" ng-class="{red:foo.description==1}"/>
                        </div>
                    </div>
                    <c:if test="${pageType ne 'add'}">
                        <div class="row float break" ng-cloak>
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>创建人:</label>
                                </div>
                                <span class="w200 sl">{{beans.creatorName}}</span>
                            </div>
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>创建时间:</label>
                                </div>
                                <div class="w200 sl">{{beans.createdDatetime|eccrmDatetime}}</div>
                            </div>
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>最后更新人:</label>
                                </div>
                                <span class="w200 sl">{{beans.modifierName}}</span>
                            </div>
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>最后时间:</label>
                                </div>
                                <div class="w200 sl">{{beans.modifiedDatetime|eccrmDatetime}}</div>
                            </div>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/settle/mapping/mapping/mapping.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/settle/mapping/mapping/mapping_edit.js"></script>
</html>