<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>批次IP漏洞明细</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main condition-row-1" ng-app="itsm.bug.bug.detail" ng-controller="Ctrl">
    <div class="dn">
        <input type="hidden" id="assetId" value="${param.assetId}"/>
        <input type="hidden" id="ip" value="${param.ip}"/>
    </div>
    <div class="list-condition" style="padding-top: 15px;">
        <div class="row float">
            <div class="item w300">
                <div class="form-label w100">
                    <label>系统名称:</label>
                </div>
                <input type="text" class="w200" maxlength="40"/>
            </div>
            <div class="item w300">
                <div class="form-label w100">
                    <label>系统IP:</label>
                </div>
                <input type="text" class="w200" maxlength="40" value="${param.ip}" disabled/>
            </div>
            <div class="item w300">
                <div class="form-label w100">
                    <label>漏洞数量:</label>
                </div>
                <input type="text" class="w200" maxlength="40" ng-model="beans.total" disabled/>
            </div>
        </div>
    </div>
    <div class="list-result" style="padding-left: 8px;padding-right: 9px;">
        <div class="block">
            <div class="block-header">
                <div class="header-text">
                    <span>漏洞详情</span>
                </div>
                <span class="header-button">
                    <a type="button" class="btn btn-green btn-min" ng-show="translate" ng-click="translate=false"
                       ng-cloak> 英文 </a>
                    <a type="button" class="btn btn-green btn-min" ng-show="!translate" ng-click="translate=true"
                       ng-cloak> 中文 </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td style="width: 80px;">端口</td>
                                <td style="width: 120px;">漏洞名称</td>
                                <td style="width: 100px;">漏洞编号</td>
                                <td style="width: 100px;">发现时间</td>
                                <td>漏洞描述</td>
                                <td>PLUG-IN OUTPUT</td>
                                <td style="width: 80px;">风险等级</td>
                                <td>修复建议</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="9" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td bo-text="foo.port"></td>
                                <td class="text-left">
                                    <a class="cp" ng-click="view(foo.id)" bo-text="foo.name|substr:30"
                                       bo-title="foo.name"></a>
                                </td>
                                <td bo-text="foo.code"></td>
                                <td bo-text="foo.createdDatetime|eccrmDate"></td>
                                <td ng-cloak ng-if="translate" class="text-left" bo-text="foo.descriptionName|substr:20"
                                    bo-title="foo.descriptionName"></td>
                                <td ng-cloak ng-if="!translate" class="text-left" bo-text="foo.description|substr:20"
                                    bo-title="foo.description"></td>
                                <td class="text-left" bo-text="foo.output|substr:20" bo-title="foo.output"></td>
                                <td bo-text="foo.riskLevelName||foo.riskLevel"></td>
                                <td class="text-left" bo-text="foo.solution|substr:20" bo-title="foo.solution"></td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-click="view(foo.id);">详情</a>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/asset/list/bug_detail.js"></script>
</html>