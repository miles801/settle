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
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="itsm.bug.bug.commit" ng-controller="Ctrl" style="overflow: auto;">
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
                        <input type="hidden" id="id" value="${id}"/>
                    </div>
                    <div class="row float">
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>整改人:</label>
                            </div>
                            <div class="w200 pr">
                                <input type="text" class="w200" ng-model="beans.masterName" readonly
                                       ng-click="pickMaster();" validate validate-required
                                       placeholder="点击选择"/>
                                <span class="add-on"><i class="icons icon fork" ng-click="clearMaster();"></i></span>
                            </div>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.priority">优先级:</label>
                            </div>
                            <select class="w200" ng-model="beans.priority" name="priority"
                                    ng-options="foo.value as foo.name for foo in priority" validate validate-required>
                            </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>整改时限:</label>
                            </div>
                            <div class="w200 pr">
                                <input type="text" class="w200" ng-model="beans.endDate" readonly placeholder="点击选择"
                                       eccrm-my97="{dateFmt:'yyyy-MM-dd HH:mm:00'}" validate validate-required/>
                                <span class="add-on"><i class="icons icon clock"
                                                        ng-click="beans.endDate=null"></i></span>
                            </div>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>说明:</label>
                            </div>
                            <textarea rows="6" class="w800" ng-model="beans.repairDesc" maxlength="1000"></textarea>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/emp/emp.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug_commit.js"></script>
</html>