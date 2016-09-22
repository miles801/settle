<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>通讯录列表</title>
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
<div class="main condition-row-1" ng-app="settle.vip.contact.list" ng-controller="Ctrl">
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
                                <label>姓名:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.name"
                                   maxlength="40"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>手机:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.mobile"
                                   maxlength="20"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>所属团队:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.groupId"
                                   maxlength="40"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>所属文交所:</label>
                            </div>
                            <select class="w200" ng-model="condition.company"
                                    ng-options="foo.value as foo.name for foo in companys"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>返佣银行:</label>
                            </div>
                            <select class="w200" ng-model="condition.bank"
                                    ng-options="foo.value as foo.name for foo in banks"
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
                    <span>通讯录列表</span>
                </div>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="exportData();"
                           ng-disabled="!pager.total" ng-cloak> 导出数据 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="add();"> 新建 </a>
                    <a type="button" class="btn btn-green btn-min" ng-click="remove();" ng-disabled="!anyone" ng-cloak> 删除 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="importData();"> 导入数据 </a>
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
                                <td style="width: 30px">序号</td>
                                <td style="width: 120px;">姓名</td>
                                <td style="width: 150px;">手机</td>
                                <td>所属团队</td>
                                <td style="width: 120px;">所属文交所</td>
                                <td style="width: 180px;">返佣银行</td>
                                <td style="width: 150px;">银行账户</td>
                                <td>备注</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="10" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td><input type="checkbox" ng-model="foo.isSelected"/></td>
                                <td bo-text="$pager.start+$index+1"></td>
                                <td>
                                    <a ng-click="view(foo.id)" bo-text="foo.name" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.mobile"></td>
                                <td bo-text="foo.groupId"></td>
                                <td bo-text="foo.companyName"></td>
                                <td bo-text="foo.bankName"></td>
                                <td bo-text="foo.bankAccount"></td>
                                <td bo-text="foo.description"></td>
                                <td class="text-left">
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
<script type="text/javascript"
        src="<%=contextPath%>/app/settle/vip/contact/contact.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/app/settle/vip/contact/contact_list.js"></script>
</html>