<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>公告列表</title>
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
<div class="main condition-row-1" ng-app="itsm.notice.notice.list" ng-controller="Ctrl">
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
                                <label>编号:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.code"
                                   maxlength="40"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>标题:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.title"
                                   maxlength="100"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>栏目:</label>
                            </div>
                            <select class="w120" ng-model="condition.category"
                                    ng-options="foo.value as foo.name for foo in categorys"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>重要性:</label>
                            </div>
                            <select class="w120" ng-model="condition.important"
                                    ng-options="foo.value as foo.name for foo in importants"
                                    ng-change="query();"> </select>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>关键字:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.keywords"
                                   maxlength="100"/>
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
                    <span>公告列表</span>
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
                                <td style="width: 100px;">编号</td>
                                <td style="width: 110px;">发布时间</td>
                                <td style="width: 80px;">发布人</td>
                                <td>标题</td>
                                <td style="width: 120px;">栏目</td>
                                <td style="width: 120px;">重要性</td>
                                <td style="width: 80px;">附件</td>
                                <td style="width: 40px;">顶置</td>
                                <td style="width: 40px;">提醒</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="10" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="foo.code"></td>
                                <td bo-text="foo.createdDatetime|eccrmDatetime"></td>
                                <td bo-text="foo.creatorName"></td>
                                <td >
                                    <a ng-click="view(foo.id)" bo-text="foo.title" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.categoryName"></td>
                                <td bo-text="foo.importantName"></td>
                                <td>
                                    <a ng-href="<%=contextPath%>/attachment/download-all?bid={{foo.id}}" target="_blank"
                                       ng-if="foo.attachments>0" class="cp" title="点击下载相关文件">下载
                                        ({{foo.attachments}}) </a>
                                </td>
                                <td bo-text="foo.top?'是':'否'"></td>
                                <td bo-text="foo.notice?'是':'否'"></td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-click="modify(foo.id);">编辑</a>
                                    <a class="btn-op red" ng-click="remove(foo.id);">删除</a>
                                    <a class="btn-op blue" ng-click="viewReadHistory(foo.id);">阅读记录</a>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/notice/notice/notice.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/notice/noticeRecord/noticeRecord.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/notice/notice/notice_list.js"></script>
</html>