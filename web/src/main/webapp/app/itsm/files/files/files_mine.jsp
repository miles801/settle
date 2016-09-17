<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>文件管理列表</title>
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
<div class="main condition-row-2" ng-app="itsm.files.files.list" ng-controller="Ctrl">
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
                                <label>管理框架:</label>
                            </div>
                            <select class="w200" ng-model="condition.scope1"
                                    ng-options="foo.value as foo.name for foo in scope1s"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>管理域:</label>
                            </div>
                            <select class="w200" ng-model="condition.scope2"
                                    ng-options="foo.value as foo.name for foo in scope2s"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>级别:</label>
                            </div>
                            <select class="w200" ng-model="condition.level"
                                    ng-options="foo.value as foo.name for foo in levels"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300 break">
                            <div class="form-label w100">
                                <label>文件类型:</label>
                            </div>
                            <select class="w200" ng-model="condition.type"
                                    ng-options="foo.value as foo.name for foo in types"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>密级:</label>
                            </div>
                            <select class="w200" ng-model="condition.secret"
                                    ng-options="foo.value as foo.name for foo in secrets"
                                    ng-change="query();"> </select>
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
                    <span>文件列表</span>
                </div>
                <span class="header-button">
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td style="width: 120px;">管理框架</td>
                                <td>管理域</td>
                                <td>文件名</td>
                                <td style="width: 80px;">版本</td>
                                <td style="width: 60px;">级别</td>
                                <td style="width: 80px;">文件类型</td>
                                <td style="width: 80px;">密级</td>
                                <td style="width: 100px;">发布人</td>
                                <td style="width: 110px;">发布时间</td>
                                <td style="width: 80px;">是否已阅</td>
                                <td style="width: 100px;">阅读时间</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="12" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td>
                                    <a ng-click="view(foo.id)" bo-text="foo.scope1Name" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.scope2Name"></td>
                                <td class="text-left ">
                                    <a ng-click="downloadAll(foo.id,foo.hasRead);"
                                       bo-text="foo.name" target="_blank" bo-title="foo.name"
                                       class="cp ellip" title="点击下载相关文件" style="width: 250px;"></a>
                                </td>
                                <td bo-text="foo.version"></td>
                                <td bo-text="foo.levelName"></td>
                                <td bo-text="foo.typeName"></td>
                                <td bo-text="foo.secretName"></td>
                                <td bo-text="foo.creatorName"></td>
                                <td bo-text="foo.createdDatetime|eccrmDatetime"></td>
                                <td>
                                    <span ng-class="{'green':foo.hasRead,'red':!foo.hasRead}"
                                          bo-text="foo.hasRead?'已读':'未读'"
                                          class="bgc"></span>
                                </td>
                                <td bo-text="foo.readTime|eccrmDatetime"></td>
                                <td>
                                    <a class="btn-op yellow" ng-click="addCallback(foo.id);">反馈意见</a>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/files/files/files.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/files/filesCallback/filesCallback.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/files/files/files_mine.js"></script>
</html>