<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>批次列表</title>
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
<div class="main condition-row-1" ng-app="itsm.bug.bugBatch.list" ng-controller="Ctrl">
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
                                <label>批次编号:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.code" maxlength="40"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>扫描说明:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.scanPurpose" maxlength="40"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label validate-error="form.scanType">扫描类型:</label>
                            </div>
                            <select class="w120" ng-model="condition.scanType" name="scanType"
                                    ng-options="foo.value as foo.name for foo in scanTypes"
                                    ng-change="query();">
                            </select>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label validate-error="form.scanMachine">扫描器:</label>
                            </div>
                            <select class="w120" ng-model="condition.scanMachine" name="scanMachine"
                                    ng-options="foo.value as foo.name for foo in scanMachines"
                                    ng-change="query();">
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
                    <span>批次列表</span>
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
                                <td style="width: 150px;">批次编号</td>
                                <td style="width: 100px;">扫描人</td>
                                <td>扫描说明</td>
                                <td>部门</td>
                                <td>扫描类型</td>
                                <td>扫描器</td>
                                <td>扫描范围</td>
                                <td>包含IP数</td>
                                <td>漏洞数</td>
                                <td style="width: 80px;">修复率</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="12" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="pager.start+$index+1"></td>
                                <td style="width: 100px;">
                                    <a class="cp" ng-click="view(foo.id);" bo-text="foo.code"></a>
                                </td>
                                <td bo-text="foo.creatorName"></td>
                                <td bo-text="foo.scanPurpose"></td>
                                <td bo-text="foo.orgName"></td>
                                <td bo-text="foo.scanTypeName"></td>
                                <td bo-text="foo.scanMachineName"></td>
                                <td bo-text="foo.scanRange"></td>
                                <td bo-text="foo.ips"></td>
                                <td bo-text="foo.bugs"></td>
                                <td>
                                    <a bo-text="((foo.repair*100|number:2)||'0.00')+' %'" class="bgc red"
                                       ng-class="{green1:foo.repair>0.8}"></a>
                                </td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-click="detail(foo.id);">详情</a>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bugBatch/bugBatch.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bugBatch/bugBatch_list.js"></script>
</html>