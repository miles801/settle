<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>在线资产列表</title>
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
<div class="main condition-row-1" ng-app="itsm.asset.onlineAsset.list" ng-controller="Ctrl">
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
                                <label>IP地址:</label>
                            </div>
                            <input class="w120" type="text" ng-model="condition.ip" maxlength="20"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>风险等级:</label>
                            </div>
                            <select class="w120" ng-options="foo.value as foo.name for foo in riskLevels"
                                    ng-model="condition.riskLevel" ng-change="query();"></select>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>批次号:</label>
                            </div>
                            <input class="w120" type="text" ng-model="condition.batchNo" maxlength="20"/>
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
                    <span>在线资产列表</span>
                </div>
                <span class="header-button">
                    <a type="button" class="btn btn-green btn-min" ng-click="importData();"> 导入 </a>
                    <a type="button" class="btn btn-green btn-min" ng-click="exportData();" ng-disabled="!pager.total"
                       ng-cloak> 导出数据 </a>
                    <a type="button" class="btn btn-green btn-min" ng-click="remove();" ng-disabled="!anyone" ng-cloak>
                        批量删除 <span ng-show="items.length>0" ng-cloak>({{items.length}})</span>
                    </a>
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
                                <td style="width: 80px;">批次号</td>
                                <td style="width: 110px;">IP地址</td>
                                <td style="width:110px;">MAC地址</td>
                                <td>主机名</td>
                                <td>资产名称</td>
                                <td>开放端口</td>
                                <td>SNMP</td>
                                <td style="width: 120px;">技术风险</td>
                                <td>备注</td>
                                <td style="width: 120px;">导入时间</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="12" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td><input type="checkbox" ng-model="foo.isSelected"/></td>
                                <td bo-text="foo.batchNo"></td>
                                <td>
                                    <a ng-click="view(foo.id)" bo-text="foo.ip" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.mac"></td>
                                <td bo-text="foo.hostname"></td>
                                <td bo-text="foo.assetName"></td>
                                <td bo-text="foo.port"></td>
                                <td bo-text="foo.snmp|substr:20" bo-title="foo.snmp"></td>
                                <td>
                                    <a class="cp" ng-click="setTechRisk(foo.id,foo.ip);"
                                       bo-text="foo.techRisk||'未评估'"></a>
                                </td>
                                <td bo-text="foo.description|substr:30" bo-title="foo.description"></td>
                                <td bo-text="foo.createdDatetime|eccrmDatetime"></td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-click="modify(foo.id);">编辑</a>
                                    <a class="btn-op red" ng-click="remove(foo.id);">删除</a>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/onlineAsset/onlineAsset.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/onlineAsset/onlineAsset_list.js"></script>
</html>