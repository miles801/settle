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
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300">
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
                                <td>文交所</td>
                                <td>团队名称</td>
                                <td class="cp" ng-click="order('vipCounts');">交易数量
                                    <span ng-show="condition.orderBy=='vipCounts'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('normalCounts');">正常交易
                                    <span ng-show="condition.orderBy=='normalCounts'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('assignCounts');">已签约
                                    <span ng-show="condition.orderBy=='assignCounts'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('assignCounts');">有交易
                                    <span ng-show="condition.orderBy=='assignCounts'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('totalMoney');">成交额
                                    <span ng-show="condition.orderBy=='totalMoney'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('fee');">交易手续费
                                    <span ng-show="condition.orderBy=='fee'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('commission');">标准佣金
                                    <span ng-show="condition.orderBy=='commission'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('stepPercent');">阶梯比例
                                    <span ng-show="condition.orderBy=='stepPercent'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('taxServerFee');">含税服务费
                                    <span ng-show="condition.orderBy=='taxServerFee'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('percent');">设定比例
                                    <span ng-show="condition.orderBy=='percent'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('payMoney');">支付金额
                                    <span ng-show="condition.orderBy=='payMoney'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('outofTax');">除税支付金额
                                    <span ng-show="condition.orderBy=='outofTax'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('tax');">税金
                                    <span ng-show="condition.orderBy=='tax'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td>时间</td>
                                <td>备注</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans.length">
                                <td colspan="18" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans" ng-cloak>
                                <td bo-text="pager.start+$index+1"></td>
                                <td bo-text="foo.companyName"></td>
                                <td bo-text="foo.groupName"></td>
                                <td bo-text="foo.vipCounts"></td>
                                <td bo-text="foo.normalCounts"></td>
                                <td bo-text="foo.assignCounts"></td>
                                <td bo-text="foo.assignCounts"></td>
                                <td bo-text="foo.totalMoney|number"></td>
                                <td bo-text="foo.fee|number"></td>
                                <td bo-text="foo.commission"></td>
                                <td bo-text="foo.stepPercent"></td>
                                <td bo-text="foo.taxServerFee"></td>
                                <td bo-text="foo.percent"></td>
                                <td bo-text="foo.payMoney|number"></td>
                                <td bo-text="foo.outofTax|number"></td>
                                <td bo-text="foo.tax|number"></td>
                                <td bo-text="foo.occurDate|date:'yyyy-MM'"></td>
                                <td bo-text="foo.description"></td>
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
<script type="text/javascript" src="<%=contextPath%>/app/settle/vip/vip/vip.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/settle/report/analysis/analysis_list.js"></script>
</html>