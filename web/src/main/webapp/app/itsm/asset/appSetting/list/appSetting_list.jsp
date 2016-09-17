<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<html lang="en">
<head>
    <title>业务应用设置</title>
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
<div class="main condition-row-1" ng-app="itsm.appSetting.list" ng-controller="Ctrl">
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
                                <label>名称:</label>
                            </div>
                            <input class="w120" type="text" ng-model="condition.name"/>
                        </div>

                        <div class="item w200">
                            <div class="form-label w80">
                                <label>重要性:</label>
                            </div>
                            <select ng-model="condition.important" class="w120"
                                    ng-options="foo.value as foo.name for foo in importants"
                                    ng-change="query();"></select>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>等保级别:</label>
                            </div>
                            <select ng-model="condition.level" class="w120"
                                    ng-options="foo.value as foo.name for foo in levels" ng-change="query();"></select>
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
                    <span>业务应用设置</span>
                </div>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="add();">
                            <span class="glyphicons plus"></span> 新建
                        </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="remove();" ng-cloak
                           ng-disabled="!anyone">
                            <span class="glyphicons plus"></span> 删除
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
                                <td>系统名称</td>
                                <td>系统说明</td>
                                <td>重要性</td>
                                <td>等保级别</td>
                                <td>可用性要求</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="7" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td><input type="checkbox" ng-model="foo.isSelected"/></td>
                                <td title="点击查询明细！" style="cursor: pointer;">
                                    <a ng-click="view(foo.id)" bo-text="foo.name"></a>
                                </td>
                                <td bo-text="foo.description|substr:50"></td>
                                <td bo-text="foo.important"></td>
                                <td bo-text="foo.level"></td>
                                <td bo-text="foo.required"></td>
                                <td>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/appSetting/appSetting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/appSetting/list/appSetting_list.js"></script>
</html>