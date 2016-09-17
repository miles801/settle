<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<html lang="en">
<head>
    <title>资产管理风险列表</title>
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
<div class="main condition-row-1" ng-app="itsm.asset.riskManage.list" ng-controller="Ctrl">
    <div class="dn">
        <input type="hidden" id="assetId" value="${assetId}"/>
    </div>
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
                                <label>风险等级:</label>
                            </div>
                            <select class="w120" ng-model="condition.fxLevel"
                                    ng-options="foo.value as foo.name for foo in values" ng-change="query();">
                            </select>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>处置策略:</label>
                            </div>
                            <select class="w120" ng-model="condition.handlePolicy"
                                    ng-options="foo.value as foo.name for foo in handlePolicys" ng-change="query();">
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
                    <span>资产管理风险列表</span>
                </div>
            <span class="header-button">
                <a type="button" class="btn btn-green btn-min" ng-click="add();"> 新建 </a>
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
                                <td>资产名称</td>
                                <td>资产组名称</td>
                                <td>威胁</td>
                                <td>弱点</td>
                                <td>已有控制</td>
                                <td>风险描述</td>
                                <td>资产值A</td>
                                <td>威胁值T</td>
                                <td>弱点值V</td>
                                <td>风险值</td>
                                <td>管理风险等级</td>
                                <td>处置策略</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="14" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td><input type="checkbox" ng-model="foo.isSelected"/></td>
                                <td >
                                    <a class="cp" ng-click="view(foo.id);" bo-text="foo.assetName" title="点击查看"></a>
                                </td>
                                <td bo-text="foo.assetGroupName"></td>
                                <td bo-text="foo.wxName"></td>
                                <td bo-text="foo.rdName"></td>
                                <td bo-text="foo.ctrl"></td>
                                <td bo-text="foo.fxmsName"></td>
                                <td bo-text="foo.assetValue"></td>
                                <td bo-text="foo.wxValue"></td>
                                <td bo-text="foo.rdValue"></td>
                                <td bo-text="foo.fxValue"></td>
                                <td bo-text="foo.fxLevel"></td>
                                <td bo-text="foo.handlePolicyName"></td>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/riskManage/riskManage.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/asset/asset.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/assetGroup/assetGroup.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/otherSetting/otherSetting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/riskManage/riskManage_list.js"></script>
</html>