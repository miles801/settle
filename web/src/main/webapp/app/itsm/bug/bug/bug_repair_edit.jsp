<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>漏洞整改提交</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <%-- 富文本 --%>
    <script type="text/javascript" src="<%=contextPath%>/vendor/kindeditor-4.1.10/kindeditor-min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/kindeditor-4.1.10/lang/zh_CN.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-upload.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="itsm.bug.bug.repair2" ng-controller="Ctrl" style="overflow: auto;">
    <div class="block">
        <div class="block-header">
                <span class="header-button">
                    <a class="btn btn-green btn-min" ng-click="save()" ng-disabled="!form.$valid">提交 </a>
                    <a class="btn btn-green btn-min" ng-click="back();">返回 </a>
                </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <form name="form" class="form-horizontal" role="form">
                    <div style="display: none;">
                        <input type="hidden" id="id" value="${param.id}"/>
                    </div>
                    <div class="row float">
                        <div class="item w600">
                            <div class="form-label w100">
                                <label validate-error="form.repair">修复结果:</label>
                            </div>
                            <div class="w500">
                                <label class="w80"><input type="checkbox" name="repair" ng-model="beans.repair"
                                                          ng-true-value="true">
                                    <span style="margin-left: 3px;">已修复</span>
                                </label>
                                <label class="w80" style="margin-left:20px;">
                                    <input type="checkbox" name="repair" ng-model="beans.repair" ng-true-value="false">
                                    <span style="margin-left: 3px;">无法修复</span>
                                </label>
                            </div>
                        </div>
                        <div class="item w300 break">
                            <div class="form-label w100">
                                <label validate-error="form.vendor">协助厂商:</label>
                            </div>
                            <select class="w200" ng-model="beans.vendor" name="vendor"
                                    ng-options="foo.value as foo.name for foo in vendor" validate validate-required>
                            </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.repairRiskLevel">整改后等级:</label>
                            </div>
                            <select class="w200" ng-model="beans.repairRiskLevel" name="repairRiskLevel"
                                    ng-options="foo.value as foo.name for foo in riskLevel" validate validate-required>
                            </select>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label validate-error="form.certify">修复证据:</label>
                            </div>
                            <textarea rows="24" class="w800" id="certify" name="certify"></textarea>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug_repair_edit.js"></script>
</html>