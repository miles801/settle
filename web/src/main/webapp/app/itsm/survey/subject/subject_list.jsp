<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>题目列表</title>
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
<div class="main condition-row-1" ng-app="itsm.survey.subject.list" ng-controller="Ctrl">
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
                                <label>所属分类:</label>
                            </div>
                            <div class="w200 pr">
                                <input type="text" class="w200" ng-model="condition.categoryId"
                                       ztree-single="categoryIdTree" readonly placeholder="点击选择"/>
                                        <span class="add-on"><i class="icons icon fork"
                                                                ng-click="clearCategoryId();"
                                                                title="点击清除"></i></span>
                            </div>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>问题编号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.code"
                                   maxlength="40"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>问题类型:</label>
                            </div>
                            <select class="w200" ng-model="condition.type"
                                    ng-options="foo.value as foo.name for foo in types"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>部门ID:</label>
                            </div>
                            <input type="text" class="w200" ng-model="condition.orgId"
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
                    <span>题目列表</span>
                </div>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="importData();"> 导入题目 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="exportData();"
                           ng-disabled="!pager.total" ng-cloak> 导出数据 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="add();"> 新建 </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td>所属分类</td>
                                <td>合规要求</td>
                                <td>问题编号</td>
                                <td>问题内容</td>
                                <td>问题类型</td>
                                <td>部门</td>
                                <td>更新人</td>
                                <td>更新时间</td>
                                <td>状态</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="10" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="foo.categoryName"></td>
                                <td bo-text="foo.requireContent"></td>
                                <td bo-text="foo.code"></td>
                                <td bo-text="foo.content"></td>
                                <td bo-text="foo.typeName"></td>
                                <td bo-text="foo.orgName"></td>
                                <td bo-text="foo.modifierName||foo.creatorName"></td>
                                <td bo-text="(foo.modifiedDatetime||foo.createdDatetime)|eccrmDatetime"></td>
                                <td bo-text="foo.deleted?'禁用':'启用'"></td>
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
        src="<%=contextPath%>/app/itsm/survey/subject/subject.js"></script>
<script type="text/javascript"
        src="<%=contextPath%>/app/itsm/survey/subject/subject_list.js"></script>
</html>