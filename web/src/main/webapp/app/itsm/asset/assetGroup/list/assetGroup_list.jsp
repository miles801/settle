<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<html lang="en">
<head>
    <title>资产组</title>
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
<div class="main condition-row-1" ng-app="asset.assetGroup.list" ng-controller="Ctrl">
    <div class="list-condition">
        <div class="block">
            <div class="block-header">
                <span class="header-text">
                    <span class="glyphicons search"></span>
                </span>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="condition={};">
                                <span class="glyphicons search"></span>
                                重置
                        </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="query();">
                                <span class="glyphicons search"></span>
                                查询
                        </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="row float">

                        <div class="item w200">
                            <div class="form-label w80">
                                <label>资产类型:</label>
                            </div>
                            <select ng-model="condition.assetType" class="w120"
                                    ng-options="foo.value as foo.name for foo in types" ng-change="query();"></select>
                        </div>

                        <div class="item w200">
                            <div class="form-label w80">
                                <label>来源:</label>
                            </div>
                            <select ng-model="condition.source" class="w120"
                                    ng-options="foo.value as foo.name for foo in sources" ng-change="query();"></select>
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
                    <span class="glyphicons list"></span>
                    <span>资产组</span>
                </div>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="add();">新建 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="importData();">导入数据 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="remove();" ng-disabled="!anyone"
                           ng-cloak>删除 </a>
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
                                <td>组编号</td>
                                <td>资产类型</td>
                                <td>资产组名</td>
                                <td>资产组说明</td>
                                <td>资产价值C</td>
                                <td>资产价值I</td>
                                <td>资产价值A</td>
                                <td>来源</td>
                                <td>是否禁用</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="11" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td><input type="checkbox" ng-model="foo.isSelected"/></td>
                                <td title="点击查询明细！" style="cursor: pointer;">
                                    <a ng-click="view(foo.id)" bo-text="foo.code"></a>
                                </td>
                                <td bo-text="foo.typeName"></td>
                                <td bo-text="foo.name"></td>
                                <td bo-text="foo.description|substr:50"></td>
                                <td bo-text="foo.valueC"></td>
                                <td bo-text="foo.valueI"></td>
                                <td bo-text="foo.valueA"></td>
                                <td bo-text="foo.sourceName"></td>
                                <td bo-text="foo.deleted?'是':'否'"></td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-click="modify(foo.id);">编辑</a>
                                    <a class="btn-op red" ng-click="enable(foo.id);" ng-if="foo.deleted">启用</a>
                                    <a class="btn-op red" ng-click="disable(foo.id);" ng-if="!foo.deleted">禁用</a>
                                    <a class="btn-op yellow" ng-click="cloneAssetGroup(foo.id);">克隆 </a>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/assetGroup/assetGroup.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/assetGroup/list/assetGroup_list.js"></script>
</html>