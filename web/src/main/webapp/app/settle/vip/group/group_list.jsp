<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>团队列表</title>
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
<div class="main condition-row-1" ng-app="settle.vip.group.list" ng-controller="Ctrl">
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
                                <label>编号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.code" maxlength="40"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>名称:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.name" maxlength="40"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>文交所:</label>
                            </div>
                            <select class="w200" ng-model="condition.company"
                                    ng-options="foo.value as foo.name for foo in companys"></select>
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
                    <span>团队列表</span>
                </div>
                <span class="header-button">
                    <a type="button" class="btn btn-green btn-min" ng-click="exportData();" ng-disabled="!pager.total"
                       ng-cloak> 导出数据 </a>
                    <a type="button" class="btn btn-green btn-min" ng-click="add();"> 新建 </a>
                    <a type="button" class="btn btn-green btn-min" ng-click="importData();"> 导入团队 </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td class="width-min">序号</td>
                                <td style="width: 120px;">编号</td>
                                <td style="width: 150px;">名称</td>
                                <td style="width: 150px;">文交所</td>
                                <td style="width: 120px;">状态</td>
                                <td>备注</td>
                                <td class="text-left">操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="7" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="pager.start+$index+1"></td>
                                <td>
                                    <a ng-click="view(foo.id)" bo-text="foo.code" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.name"></td>
                                <td bo-text="foo.companyName"></td>
                                <td>
                                    <a ng-class="{'green':!foo.deleted,'red':foo.deleted}"
                                       bo-text="foo.deleted?'禁用':'启用'"
                                       class="bgc"></a>
                                </td>
                                <td bo-text="foo.description" class="text-left"></td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-click="modify(foo.id);">编辑</a>
                                    <a class="btn-op green" ng-click="enable(foo.id);" ng-if="foo.deleted">启用</a>
                                    <a class="btn-op red" ng-click="disable(foo.id);" ng-if="!foo.deleted">禁用</a>
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
        src="<%=contextPath%>/app/settle/vip/group/group.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/app/settle/vip/group/group_list.js"></script>
</html>