<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<html lang="en">
<head>
    <title>资产</title>
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
<div class="main condition-row-2" ng-app="itsm.asset.list" ng-controller="Ctrl">
    <div class="list-condition">
        <div class="block">
            <div class="block-header">
                <span class="header-text">
                    <span class="glyphicons search"></span>
                </span>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="reset();"> 重置 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="query();"> 查询 </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="row float">
                        <div class="item w250">
                            <div class="form-label w100">
                                <label validate-error="form.name">类型:</label>
                            </div>
                            <select ng-options="foo.id as foo.name for foo in type" class="w150"
                                    ng-model="condition.assetType1"
                                    ng-change="query();"></select>
                        </div>
                        <div class="item w250">
                            <div class="form-label w100">
                                <label>资产名称:</label>
                            </div>
                            <input class="w150" type="text" ng-model="condition.name" maxlength="60"/>
                        </div>
                        <div class="item w250">
                            <div class="form-label w100">
                                <label>IP地址:</label>
                            </div>
                            <input class="w150" type="text" ng-model="condition.ip" maxlength="60"/>
                        </div>
                        <div class="item w250">
                            <div class="form-label w100">
                                <label>管理者:</label>
                            </div>
                            <div class="w150 pr">
                                <input class="w150" type="text" ng-model="masterName" maxlength="60" readonly
                                       ng-click="pickEmp();" placeholder="点击选择"/>
                                <span class="add-on"><i class="icons icon fork" ng-click="clear();"
                                                        title="点击清除"></i></span>
                            </div>
                        </div>
                        <div class="item w250 break">
                            <div class="form-label w100">
                                <label>部门:</label>
                            </div>
                            <div class="w150 pr">
                                <input class="w150" type="text" ng-model="condition.orgName" ztree-single="orgTree"
                                       readonly placeholder="点击选择"/>
                                <span class="add-on">
                                    <i class="icons icon fork" ng-click="clearOrg();" title="点击清除"></i>
                                </span>
                            </div>
                        </div>
                        <div class="item w250">
                            <div class="form-label w100">
                                <label>价值:</label>
                            </div>
                            <div class="w150">
                                <label ng-repeat="v in values" style="margin-left: 5px;" ng-cloak>
                                    <input type="checkbox" ng-model="v.checked" ng-change="valueChange();"/> {{v.name}}
                                </label>
                            </div>
                        </div>
                        <div class="item w250">
                            <div class="form-label w100">
                                <label>资产组:</label>
                            </div>
                            <div class="w150 pr">
                                <input class="w150" type="text" ng-model="condition.assetGroupName"
                                       ztree-single="assetGroupTree" readonly placeholder="点击选择"/>
                                <span class="add-on">
                                    <i class="icons icon fork" ng-click="clearAssetGroup();" title="点击清除"></i>
                                </span>
                            </div>
                        </div>
                        <div class="item w250">
                            <div class="form-label w100">
                                <label>技术风险:</label>
                            </div>
                            <div class="w150">
                                <label ng-repeat="v in risk" style="margin-left: 5px;" ng-cloak>
                                    <input type="checkbox" ng-model="v.checked" ng-change="riskChange();"/> {{v.name}}
                                </label>
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
                    <span class="glyphicons list"></span>
                    <span>资产清单</span>
                </div>
                <span class="header-button">
                    <a type="button" class="btn btn-green btn-min" ng-click="add();">新建 </a>
                    <a type="button" class="btn btn-green btn-min" ng-click="cloneAsset();" ng-cloak
                       ng-disabled="items.length!=1">克隆 </a>
                    <a type="button" class="btn btn-green btn-min" ng-click="importData();">导入资产 </a>
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
                                <td>编号</td>
                                <td>部门</td>
                                <td>资产名称</td>
                                <td>类型</td>
                                <td>所属系统</td>
                                <td>IP地址</td>
                                <td>资产说明</td>
                                <td>管理者</td>
                                <td>资产组</td>
                                <td>价值</td>
                                <td>管理风险</td>
                                <td>技术风险</td>
                                <td>综合风险</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="15" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td><input type="checkbox" ng-model="foo.isSelected"/></td>
                                <td title="点击查询明细！" style="cursor: pointer;">
                                    <a ng-click="view(foo.id)" bo-text="foo.code"></a>
                                </td>
                                <td bo-text="foo.orgName"></td>
                                <td bo-text="foo.name|substr:16" bo-title="foo.name"></td>
                                <td bo-text="foo.assetTypeName1"></td>
                                <td bo-text="foo.appName"></td>
                                <td bo-text="foo.ip"></td>
                                <td bo-text="foo.description|substr:15" bo-title="foo.description"></td>
                                <td bo-text="foo.masterName"></td>
                                <td bo-text="foo.assetGroupName"></td>
                                <td bo-text="foo.assetValue"></td>
                                <td>
                                    <a class="cp" ng-click="setManageRisk(foo.id,foo.assetType1);" bo-text="foo.manageRiskValue||'未评估'"></a>
                                </td>
                                <td>
                                    <a class="cp" ng-click="foo.ip && setTechRisk(foo.id,foo.ip);"
                                       bo-text="foo.techRiskValue||'未评估'" ng-disabled="!foo.ip"></a>
                                </td>
                                <td bo-text="foo.manageRiskValue|max:foo.techRiskValue"></td>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/asset/asset.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/org/org.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/emp/emp.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/assetGroup/assetGroup.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/asset/list/asset_list.js"></script>
</html>