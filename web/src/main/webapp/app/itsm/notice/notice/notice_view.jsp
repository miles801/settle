<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>公告编辑</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-upload.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="itsm.notice.notice.view" ng-controller="Ctrl" style="overflow: auto;">
    <div class="block">
        <div class="block-header">
                <span class="header-button">
                    <a class="btn btn-green btn-min" ng-click="back();">返回 </a>
                </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <form name="form" class="form-horizontal" role="form" style="width: 1000px;margin: 0 auto;">
                    <div style="display: none;">
                        <input type="hidden" id="id" value="${id}"/>
                    </div>
                    <div class="row float">
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>编号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.code" maxlength="40" disabled
                                   placeholder="由后台自动生成"/>
                        </div>
                        <div class="item w600">
                            <div class="form-label w100">
                                <label>标题:</label>
                            </div>
                            <input type="text" class="w500" ng-model="beans.title" validate validate-required
                                   maxlength="100"/>
                        </div>
                        <div class="item w300 break">
                            <div class="form-label w100">
                                <label validate-error="form.category">栏目:</label>
                            </div>
                            <select class="w200" ng-model="beans.category" name="category" validate validate-required
                                    ng-options="foo.value as foo.name for foo in categorys">
                            </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.important">重要性:</label>
                            </div>
                            <select class="w200" ng-model="beans.important" name="important"
                                    ng-options="foo.value as foo.name for foo in importants">
                            </select>
                        </div>
                        <div class="item w150">
                            <div class="form-label w100">
                                <label>是否顶置:</label>
                            </div>
                            <input type="checkbox" style="width: 14px;" ng-model="beans.top"/>
                        </div>
                        <div class="item w150">
                            <div class="form-label w100">
                                <label>是否提醒:</label>
                            </div>
                            <input type="checkbox" style="width: 14px;" ng-model="beans.notice"/>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>关键字:</label>
                            </div>
                            <input type="text" class="w800" ng-model="beans.keywords" maxlength="100"
                                   placeholder="多个关键字请使用逗号或者空格进行分隔"/>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>内容:</label>
                            </div>
                            <div class="w800" rows="16" id="content"></div>
                        </div>
                    </div>
                    <div class="row float break">
                        <div class="item w900">
                            <div class="form-label w100">
                                <label>附件:</label>
                            </div>
                            <div class="w800">
                                <div eccrm-upload="uploadOptions"></div>
                            </div>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/notice/notice/notice.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/notice/notice/notice_view.js"></script>
</html>