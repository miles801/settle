<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>资产对比列表</title>
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
<div class="main condition-row-1" ng-app="itsm.asset.assetCompare.list" ng-controller="Ctrl">
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
                                <label>对比网段:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.ip"
                                   maxlength="20"/>
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
                    <span>资产对比列表</span>
                </div>
                <span class="header-button">
                    <a type="button" class="btn btn-green btn-min" ng-click="add();"> 新建 </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td style="width: 20px;">序号</td>
                                <td style="width: 100px;">比对时间</td>
                                <td style="width: 100px;">对比网段</td>
                                <td style="width: 120px;">资产表中IP数</td>
                                <td style="width: 120px;">实际网段IP数</td>
                                <td style="width: 120px;">未登记IP</td>
                                <td style="width: 120px;">登记率</td>
                                <td >备注</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="9" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="$index+1"></td>
                                <td bo-text="foo.createdDatetime|eccrmDatetime"></td>
                                <td>
                                    <a ng-click="view(foo.id)" bo-text="foo.ip" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.assetIps"></td>
                                <td bo-text="foo.onlineIps"></td>
                                <td>
                                    <a ng-click="foo.onlineIps && ips(foo.id,foo.ip)" bo-text="foo.offlineIps"
                                       class="cp"
                                       title="点击查看未登记IP"></a>
                                </td>
                                <td>
                                    <span bo-text="foo.registerPercent|number:2"></span>
                                    <span style="margin-left: 2px;">%</span>
                                </td>
                                <td bo-text="foo.description|substr:40" bo-title="foo.description"></td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-click="modify(foo.id);">编辑</a>
                                    <a class="btn-op yellow" ng-click="retry(foo.id,foo.ip);">重新比对</a>
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
        src="<%=contextPath%>/app/itsm/asset/assetCompare/assetCompare.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/app/itsm/asset/assetCompare/assetCompare_list.js"></script>
</html>