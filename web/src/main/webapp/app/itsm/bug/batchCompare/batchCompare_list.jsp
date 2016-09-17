<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>批次对比列表</title>
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
<div class="main condition-row-1" ng-app="itsm.bug.batchCompare.list" ng-controller="Ctrl">
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
                                <label>原批次号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.batchId1" maxlength="40"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>修复后批次号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.batchId2" maxlength="40"/>
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
                    <span>批次对比列表</span>
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
                                <td>原批次号</td>
                                <td>对比批次号</td>
                                <td>原极高漏洞</td>
                                <td>原高漏洞</td>
                                <td>原中漏洞</td>
                                <td>原低漏洞</td>
                                <td>新极高漏洞</td>
                                <td>新高漏洞</td>
                                <td>新中漏洞</td>
                                <td>新低漏洞</td>
                                <td>整改率</td>
                                <td>是否更新</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="14" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="pager.start+$index+1"></td>
                                <td>
                                    <a class="cp" ng-click="viewBatch(foo.batchId1);" bo-text="foo.batchId1"></a>
                                </td>
                                <td>
                                    <a class="cp" ng-click="viewBatch(foo.batchId2);" bo-text="foo.batchId2"></a>
                                </td>
                                <td bo-text="foo.critical1||0"></td>
                                <td bo-text="foo.high1||0"></td>
                                <td bo-text="foo.medium1||0"></td>
                                <td bo-text="foo.low1||0"></td>
                                <td bo-text="foo.critical2||0"></td>
                                <td bo-text="foo.high2||0"></td>
                                <td bo-text="foo.medium2||0"></td>
                                <td bo-text="foo.low2||0"></td>
                                <td>
                                    <span bo-text="(foo.repairPercent*100|number:2)+ '%'" class="bgc red"
                                          ng-class="{green1:foo.repairPercent>0.8}"></span>
                                </td>
                                <td bo-text="foo.executed?'是':'否'"></td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-click="view(foo.id);">详情</a>
                                    <a class="btn-op red" ng-click="remove(foo.id);">删除</a>
                                    <a class="btn-op yellow" ng-click="update(foo.id);" ng-if="!foo.executed"> 更新 </a>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/batchCompare/batchCompare.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/batchCompare/batchCompare_list.js"></script>
</html>