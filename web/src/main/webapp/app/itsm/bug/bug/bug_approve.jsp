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
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
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
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>批次号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.batchId" maxlength="40"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w120">
                                <label>整改后风险等级:</label>
                            </div>
                            <select class="w180" ng-model="condition.repairRiskLevel" name="riskLevel"
                                    ng-options="foo.value as foo.name for foo in riskLevels"
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
                    <span>漏洞列表</span>
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
                                <td style="width: 120px;">发现时间</td>
                                <td style="width: 60px;">漏洞编号</td>
                                <td style="width: 80px;">风险等级</td>
                                <td>漏洞名称</td>
                                <td>整改人</td>
                                <%--<td>修复建议</td>--%>
                                <td>协助厂商</td>
                                <%--<td>修复证据</td>--%>
                                <td>整改后等级</td>
                                <td>状态</td>
                                <td>评审意见</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="10" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="foo.createdDatetime|eccrmDate"></td>
                                <td bo-text="foo.code"></td>
                                <td>
                                    <span class="bgc"
                                          ng-class="{red:foo.riskLevel==5,ori:foo.riskLevel==4,yellow:foo.riskLevel==3,green1:foo.riskLevel==2,green2:foo.riskLevel==1}"
                                          bo-text="foo.riskLevel|riskLevel:'code'"></span>
                                </td>
                                <td class="text-left">
                                    <a class="cp" ng-click="view(foo.id);" bo-text="foo.name|substr:50"
                                       bo-title="foo.name" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.masterName"></td>
                                <%--<td class="text-left" bo-text="foo.solution|substr:50" bo-title="foo.solution"></td>--%>
                                <td bo-text="foo.vendorName"></td>
                                <%--<td bo-text="foo.certify|substr:50"></td>--%>
                                <td bo-text="foo.repairRiskLevel|riskLevel"></td>
                                <td bo-text="foo.statusName"></td>
                                <td class="text-left"
                                    bo-text="foo.approveDate?((foo.approveDate|eccrmDatetime) +'('+(foo.approveName)+') : '+(foo.approveDesc|substr:15)):''"></td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-if="!foo.finish && foo.status=='APPROVING'"
                                       ng-click="approve(foo.id);">评审</a>
                                    <a class="btn-op blue" ng-if="foo.approveDate"
                                       ng-click="approve(foo.id,true);">评审详情</a>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug_approve.js"></script>
</html>