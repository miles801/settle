<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>短信列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main condition-row-1" ng-app="settle.sms.sms.list" ng-controller="Ctrl">
    <div class="list-condition">
        <div class="block">
            <div class="block-header">
                    <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="querySms();"> 刷新 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="init();" ng-if="sms.no"
                           ng-cloak> 初始化 </a>
                    </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="row float" ng-if="sms.id" ng-cloak>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>总条数:</label>
                            </div>
                            <span class="w100 bgc"
                                  style="line-height: 28px;padding: 0 8px;color: #FFFFFF;text-align: center" ng-cloak
                                  ng-class="{red:sms.counts<50,green:sms.counts>50}">{{sms.counts||0}}</span>
                        </div>
                        <div class="item w200" ng-cloak>
                            <div class="form-label w100">
                                <label>状态:</label>
                            </div>
                            <span class="w100" style="line-height: 28px;" ng-cloak>{{sms.deleted?'禁用':'启用'}}</span>
                        </div>
                        <div class="item w300">
                            <a type="button" class="btn btn-blue" ng-if="!sms.deleted" ng-click="disable();">
                                禁用 </a>
                            <a type="button" class="btn btn-blue" ng-if="sms.deleted" ng-click="enable();">
                                启用 </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="list-result ">
        <div class="block">
            <div class="block-header">
                <div class="header-text">
                    <span>购买记录</span>
                </div>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="buy();" ng-cloak> 充值 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="query();" ng-cloak> 查询 </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td style="width: 30px;">序号</td>
                                <td style="width: 140px;">充值时间</td>
                                <td style="width: 120px;">充值数量</td>
                                <td style="width: 150px;">充值金额</td>
                                <td>备注</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="5" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="pager.start+$index+1"></td>
                                <td bo-text="foo.occurTime|eccrmDatetime"></td>
                                <td bo-text="foo.counts"></td>
                                <td bo-text="foo.money|number:2"></td>
                                <td bo-text="foo.description"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="list-pagination" eccrm-page="pager"></div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/settle/sms/sms/sms.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/settle/sms/smsBuy/smsBuy.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/settle/sms/sms/sms_list.js"></script>
</html>