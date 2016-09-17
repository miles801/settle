<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>批次对比详情</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main condition-row-1" ng-app="itsm.bug.batchCompare.detail" ng-controller="Ctrl">
    <div class="dn">
        <input type="hidden" id="id" value="${param.id}">
    </div>
    <div class="block">
        <div class="block-header">
                <span class="header-button">
                    <a type="button" class="btn btn-green btn-min" ng-click="exportData();" ng-cloak> 导出数据 </a>
                </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <div class="table-responsive panel panel-table">
                    <table class="table table-striped table-hover">
                        <thead class="table-header">
                        <tr>
                            <td style="width: 40px;">序号</td>
                            <td>IP地址</td>
                            <td>整改前</td>
                            <td>原风险等级</td>
                            <td>整改后</td>
                            <td>整改后风险等级</td>
                            <td>整改结果</td>
                        </tr>
                        </thead>
                        <tbody class="table-body">
                        <tr ng-show="!beans.length">
                            <td colspan="7" class="text-center">没有查询到数据！</td>
                        </tr>
                        <tr bindonce ng-repeat="foo in beans" ng-cloak>
                            <td bo-text="$index+1"></td>
                            <td>
                                <a bo-text="foo[0]" class="cp" title="点击查看详情" ng-click="view(foo[0]);"></a>
                            </td>
                            <td bo-text="foo[1]||0"></td>
                            <td bo-text="foo[2]||0"></td>
                            <td bo-text="foo[3]||0"></td>
                            <td>
                                <span class="bgc"
                                      ng-class="{red:foo[4]==5,ori:foo[4]==4,yellow:foo[4]==3,green1:foo[4]==2,green2:foo[4]==1}"
                                      bo-text="foo[4]|riskLevel:'code'"></span>
                            </td>
                            <td>
                                <a ng-class="{'red':foo[3]>0,'green':!foo[3]}" class="btn-op"
                                   bo-text="foo[3]>0?'未修复':'已修复'"></a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">总计：</td>
                            <td colspan="2">{{total1}}</td>
                            <td colspan="3">{{total2}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/batchCompare/batchCompare.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/batchCompare/batchCompare_detail.js"></script>
</html>