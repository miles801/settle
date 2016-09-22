<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>交易列表</title>
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
<div class="main condition-row-1" ng-app="settle.vip.business.list" ng-controller="Ctrl">
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
                                <label>会员编号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.vipCode"
                                   maxlength="40"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>团队编号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.groupCode"
                                   maxlength="40"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>所属文交所:</label>
                            </div>
                            <select class="w200" ng-model="condition.company"
                                    ng-options="foo.value as foo.name for foo in companys"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>交易时间:</label>
                            </div>
                            <div class="w200 pr">
                                <input type="text" class="w200" ng-model="condition.businessTime"
                                       eccrm-my97="{dateFmt:'yyyy-MM-dd HH:mm:ss'}" readonly
                                       placeholder="点击选择时间"/>
                                <span class="add-on"><i class="icons icon clock"
                                                        ng-click="condition.businessTime=null"
                                                        title="点击清除"></i></span>
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
                    <span>交易列表</span>
                </div>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="exportData();"
                           ng-disabled="!pager.total" ng-cloak> 导出数据 </a>
                    <a type="button" class="btn btn-green btn-min" ng-click="importData();"> 导入数据 </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td class="width-min">序号</td>
                                <td style="width: 120px;">会员编号</td>
                                <td style="width: 150px">团队编号</td>
                                <td style="width: 120px;">所属文交所</td>
                                <td style="width: 120px;">交易金额</td>
                                <td style="width: 120px;">手续费</td>
                                <td style="width: 120px;">交易时间</td>
                                <td>备注</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="8" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="pager.start+$index+1"></td>
                                <td>
                                    <a ng-click="view(foo.id)" bo-text="foo.vipCode" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.groupCode"></td>
                                <td bo-text="foo.companyName"></td>
                                <td bo-text="foo.money"></td>
                                <td bo-text="foo.fee"></td>
                                <td bo-text="foo.businessTime|eccrmDate"></td>
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
<script type="text/javascript"
        src="<%=contextPath%>/app/settle/vip/business/business.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/app/settle/vip/business/business_list.js"></script>
</html>