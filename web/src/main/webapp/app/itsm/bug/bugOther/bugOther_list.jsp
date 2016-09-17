<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>其他漏洞列表</title>
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
<div class="main condition-row-2" ng-app="itsm.bug.bugOther.list" ng-controller="Ctrl">
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
                                <label>IP地址:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.ip"
                                   maxlength="20"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>漏洞分类:</label>
                            </div>
                            <select class="w200" ng-model="condition.type"
                                    ng-options="foo.value as foo.name for foo in types"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>修复后等级:</label>
                            </div>
                            <select class="w200" ng-model="condition.level2"
                                    ng-options="foo.value as foo.name for foo in level2s"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300 break">
                            <div class="form-label w100">
                                <label>状态:</label>
                            </div>
                            <select class="w200" ng-model="condition.status"
                                    ng-options="foo.value as foo.name for foo in statuss"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>发现时间:</label>
                            </div>
                            <div class="w200 pr">
                                <input type="text" class="w200" ng-model="condition.createdDatetime" readonly
                                       eccrm-my97="{}"
                                       placeholder="点击选择时间" ng-change="query();"/>
                                <span class="add-on"><i class="icons icon clock" title="清除"
                                                        ng-click="condition.createdDatetime=null"></i></span>
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
                    <span>其他漏洞列表</span>
                </div>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="exportData();"
                           ng-disabled="!pager.total" ng-cloak> 导出数据 </a>
                    <a type="button" class="btn btn-green btn-min" ng-click="importData();"> 导入数据 </a>
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
                                <td>IP地址</td>
                                <td>发现时间</td>
                                <td>资产名称</td>
                                <td>漏洞分类</td>
                                <td>漏洞说明</td>
                                <td>原风险等级</td>
                                <td>修复后风险等级</td>
                                <td>整改人</td>
                                <td>整改时间</td>
                                <td>状态</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="12" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td><input type="checkbox" ng-model="foo.isSelected"/></td>
                                <td>
                                    <a ng-click="view(foo.id)" bo-text="foo.ip" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.createdDatetime|eccrmDate"></td>
                                <td bo-text="foo.assetName"></td>
                                <td bo-text="foo.typeName"></td>
                                <td bo-text="foo.description"></td>
                                <td bo-text="foo.level1Name"></td>
                                <td bo-text="foo.level2Name"></td>
                                <td bo-text="foo.modifierName"></td>
                                <td bo-text="foo.modifiedDatetime|eccrmDatetime"></td>
                                <td bo-text="foo.statusName"></td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-click="modify(foo.id);">整改</a>
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
<script type="text/javascript"
        src="<%=contextPath%>/app/itsm/bug/bugOther/bugOther.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/app/itsm/bug/bugOther/bugOther_list.js"></script>
</html>