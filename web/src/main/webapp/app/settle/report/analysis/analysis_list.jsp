<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>团队佣金列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/moment/moment.min.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main condition-row-1" ng-app="settle.report.vip" ng-controller="Ctrl">
    <div class="list-condition">
        <div class="block">
            <div class="block-header">
                    <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="query();"> 查询 </a>
                    </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="row float">
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>文交所:</label>
                            </div>
                            <select class="w200" ng-model="condition.company"
                                    ng-options="foo.value as foo.name for foo in companys"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>月份1:</label>
                            </div>
                            <input type="text" class="w200" eccrm-my97="{dateFmt:'yyyy-MM'}" readonly
                                   ng-model="condition.m1"
                                   placeholder="点击选择" validate validate-required/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>月份2:</label>
                            </div>
                            <input type="text" class="w200" eccrm-my97="{dateFmt:'yyyy-MM'}" readonly
                                   ng-model="condition.m2"
                                   placeholder="点击选择" validate validate-required/>
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
                    <span>汇总</span>
                </div>
                <span class="header-button">
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td class="width-min">序号</td>
                                <td>团队名称</td>
                                <td class="cp" ng-click="order('assignCounts');">签约数量增幅
                                    <span ng-show="condition.orderBy=='assignCounts'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('businessCounts');">活跃会员增幅
                                    <span ng-show="condition.orderBy=='businessCounts'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('payMoney');">支付佣金增幅
                                    <span ng-show="condition.orderBy=='payMoney'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans.length">
                                <td colspan="5" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans" ng-cloak>
                                <td bo-text="pager.start+$index+1"></td>
                                <td bo-text="foo.groupName"></td>
                                <td>
                                    <span bo-text="foo.assignCounts"></span>
                                    <span style="color: red;margin-left: 3px;" ng-if="foo.assignCounts>0">↑</span>
                                    <span style="color: green;margin-left: 3px;" ng-if="foo.assignCounts<0">↓</span>
                                </td>
                                <td>
                                    <span bo-text="foo.businessCounts"></span>
                                    <span style="color: red;margin-left: 3px;" ng-if="foo.businessCounts>0">↑</span>
                                    <span style="color: green;margin-left: 3px;" ng-if="foo.businessCounts<0">↓</span>
                                </td>
                                <td>
                                    <span bo-text="foo.payMoney"></span>
                                    <span style="color: red;margin-left: 3px;" ng-if="foo.payMoney>0">↑</span>
                                    <span style="color: green;margin-left: 3px;" ng-if="foo.payMoney<0">↓</span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/settle/report/groupVip/groupVip.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/settle/report/analysis/analysis_list.js"></script>
</html>