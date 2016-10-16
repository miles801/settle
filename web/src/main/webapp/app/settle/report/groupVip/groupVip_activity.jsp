<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>会员活跃度</title>
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
<div class="main condition-row-1" ng-app="settle.report.groupVip.activity" ng-controller="Ctrl">
    <div class="dn">
        <input type="hidden" id="year" value="${param.year}">
        <input type="hidden" id="month" value="${param.month}">
        <input type="hidden" id="company" value="${param.company}">
    </div>
    <div class="list-condition dn">
        <div class="block">
            <div class="block-header">
                    <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="reset();"> 重置 </a>
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
                                    ng-change="query();"></select>
                        </div>
                        <div class="item w300 dn">
                            <div class="form-label w100">
                                <label>统计时间:</label>
                            </div>
                            <div class="w200 pr">
                                <input type="text" class="w200" ng-model="condition.occurDate"
                                       eccrm-my97="{dateFmt:'yyyy-MM'}" readonly placeholder="点击选择日期"
                                       ng-change="query();"/>
                                <span class="add-on">
                                    <i class="icons icon clock" ng-click="condition.occurDate=null"
                                       title="点击清除日期"></i>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="list-result" style="padding-top: 0;margin-top: 0;">
        <div class="block">
            <div class="block-header">
                <div class="header-text">
                </div>
                <span class="header-button">
                    <a type="button" class="btn btn-green btn-min" ng-click="remove();" ng-disabled="!anyone" ng-cloak>
                        删除
                        <span ng-show="anyone">({{items.length}})</span>
                    </a>
                    <a type="button" class="btn btn-green btn-min" ng-click="exportData();"> 导出 </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td class="width-min">
                                    <div select-all-checkbox checkboxes="beans.data" selected-items="items"
                                         anyone-selected="anyone"></div>
                                </td>
                                <td class="width-min">序号</td>
                                <td>文交所</td>
                                <td class="cp" ng-click="order('groupName');">团队名称
                                    <span ng-show="condition.orderBy=='groupName'" ng-cloak>
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('vipCounts');">会员数
                                    <span ng-show="condition.orderBy=='vipCounts'" ng-cloak>
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('businessCounts');">有交易会员数
                                    <span ng-show="condition.orderBy=='businessCounts'" ng-cloak>
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('totalMoney');">成交额
                                    <span ng-show="condition.orderBy=='totalMoney'" ng-cloak>
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td>人均成交额</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="8" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td><input type="checkbox" ng-model="foo.isSelected"/></td>
                                <td bo-text="pager.start+$index+1"></td>
                                <td bo-text="foo.companyName"></td>
                                <td>
                                    <a ng-click="view(foo.id)" bo-text="foo.groupName" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.vipCounts|number"></td>
                                <td bo-text="foo.businessCounts|number"></td>
                                <td bo-text="foo.totalMoney|number:2" style="width: 180px;"></td>
                                <td bo-text="foo.totalMoney/(foo.businessCounts||1)|number:2"
                                    style="width: 180px;"></td>
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
<script type="text/javascript" src="<%=contextPath%>/app/settle/report/groupVip/groupVip.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/settle/report/groupVip/groupVip_activity.js"></script>
</html>