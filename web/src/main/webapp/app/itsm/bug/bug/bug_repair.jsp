<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>漏洞整改列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main condition-row-1" ng-app="itsm.bug.bug.repair" ng-controller="Ctrl">
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
                        <div class="item w200">
                            <div class="form-label w80">
                                <label validate-error="form.riskLevel">风险等级:</label>
                            </div>
                            <select class="w120" ng-model="condition.riskLevel" name="riskLevel"
                                    ng-options="foo.value as foo.name for foo in riskLevels"
                                    ng-change="query();">
                            </select>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>状态:</label>
                            </div>
                            <select class="w120" ng-model="condition.status"
                                    ng-options="foo.value as foo.name for foo in status"
                                    ng-change="query();">
                            </select>
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
                    <span>整改任务列表</span>
                </div>
                <span class="header-button">
                    <a type="button" class="btn btn-green btn-min" ng-click="exportData();" ng-disabled="!pager.total"
                       ng-cloak> 导出数据 </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td style="width: 100px;">IP地址</td>
                                <td style="width: 100px;">发现时间</td>
                                <td style="width: 60px;">漏洞编号</td>
                                <td style="width: 80px;">风险等级</td>
                                <td style="width: 80px;">整改后等级</td>
                                <td style="width: 80px;">端口</td>
                                <td>漏洞名称</td>
                                <%--<td>修复建议</td>--%>
                                <td style="width: 80px;">整改人</td>
                                <td style="width: 100px;">整改时限</td>
                                <td>状态</td>
                                <td>协助厂商</td>
                                <td>评审人</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="13" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="foo.ip"></td>
                                <td bo-text="foo.createdDatetime|eccrmDate"></td>
                                <td bo-text="foo.code"></td>
                                <td>
                                    <span class="bgc"
                                          ng-class="{red:foo.riskLevel==5,ori:foo.riskLevel==4,yellow:foo.riskLevel==3,green1:foo.riskLevel==2,green2:foo.riskLevel==1}"
                                          bo-text="foo.riskLevel|riskLevel:'code'"></span>
                                </td>
                                <td>
                                    <span class="bgc"
                                          ng-class="{red:foo.repairRiskLevel==5,ori:foo.repairRiskLevel==4,yellow:foo.repairRiskLevel==3,green1:foo.repairRiskLevel==2,green2:foo.repairRiskLevel==1}"
                                          bo-text="foo.repairRiskLevel|riskLevel:'code'"></span>
                                </td>
                                <td bo-text="foo.port"></td>
                                <td class="text-left">
                                    <a ng-click="view(foo.id);" class="cp" bo-text="foo.name|substr:50"
                                       bo-title="foo.name"></a>
                                </td>
                                <%--<td class="text-left" bo-text="foo.output|substr:30" bo-title="foo.output"></td>--%>
                                <%--<td class="text-left" bo-text="foo.solution|substr:50" bo-title="foo.solution"></td>--%>
                                <td bo-text="foo.masterName"></td>
                                <td bo-text="foo.endDate|eccrmDate" ng-class="{'text-danger':foo.endDate<now}"></td>
                                <td>
                                    <a ng-class="{green:foo.status=='REPAIRED',yellow:foo.status!=='REPAIRED'}"
                                       class="btn-op" bo-text="foo.statusName"></a>
                                </td>
                                <td bo-text="foo.vendorName"></td>
                                <td bo-text="foo.approveName"></td>
                                <%--<td bo-text="foo.certify|substr:50" bo-title="foo.certify"></td>--%>
                                <td class="text-left">
                                    <a class="btn-op yellow" ng-click="repair(foo.id);" ng-if="foo.status=='COMMITTED'">整改</a>
                                    <a class="btn-op red" ng-click="transport(foo);"
                                       ng-if="foo.status=='COMMITTED'">转派</a>
                                </td>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/emp/emp.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug_repair.js"></script>
</html>