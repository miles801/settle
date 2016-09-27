<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>会员列表</title>
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
<div class="main condition-row-2" ng-app="settle.vip.vip.list" ng-controller="Ctrl">
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
                                <label>所属团队:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.groupId"
                                   maxlength="40"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>签约状态:</label>
                            </div>
                            <select class="w200" ng-model="condition.assignStatus"
                                    ng-options="foo.value as foo.name for foo in assignStatuss"
                                    ng-change="query();"></select>
                        </div>
                        <div class="item w300 break">
                            <div class="form-label w100">
                                <label>状态:</label>
                            </div>
                            <select class="w200" ng-model="condition.status"
                                    ng-options="foo.value as foo.name for foo in statuss" ng-change="query();"></select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>文交所:</label>
                            </div>
                            <select class="w200" ng-model="condition.company"
                                    ng-options="foo.value as foo.name for foo in companys"
                                    ng-change="query();"></select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>推荐人:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.recommend"
                                   maxlength="40"/>
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
                    <span>会员列表</span>
                </div>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="clearAll();"> 清空 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="generateReport();"> 产生报表 </a>
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
                                <td style="width: 120px;">名称</td>
                                <td style="width: 120px;">所属团队</td>
                                <td style="width: 100px;">签约状态</td>
                                <td style="width: 100px;">状态</td>
                                <td style="width: 150px;">文交所</td>
                                <td style="width: 120px;">推荐人</td>
                                <td style="width: 120px;">创建时间</td>
                                <td>备注</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="10" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="pager.start+$index+1"></td>
                                <td>
                                    <a ng-click="view(foo.id)" bo-text="foo.code" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.name"></td>
                                <td>
                                    <a ng-click="viewGroup(foo.groupId)" bo-text="foo.groupId" class="cp"
                                       title="查看团队"></a>
                                </td>
                                <td>
                                    <span class="bgc" bo-text="foo.assignStatusName"
                                          ng-class="{red:foo.assignStatus!='1',green1:foo.assignStatus=='1'}"></span>
                                </td>
                                <td>
                                    <span class="bgc" ng-class="{red:foo.status!='1',green1:foo.status=='1'}"
                                          bo-text="foo.statusName"></span>
                                </td>
                                <td bo-text="foo.companyName"></td>
                                <td bo-text="foo.recommend"></td>
                                <td bo-text="foo.occurDate|eccrmDatetime"></td>
                                <td class="text-left" bo-text="foo.description|substr:30"></td>
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
        src="<%=contextPath%>/app/settle/vip/vip/vip.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/app/settle/vip/vip/vip_list.js"></script>
</html>