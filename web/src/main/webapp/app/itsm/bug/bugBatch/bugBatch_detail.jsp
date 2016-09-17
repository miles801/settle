<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>批次详情</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-upload.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="itsm.bug.bugBatch.detail" ng-controller="Ctrl" style="overflow: auto;">
    <div class="dn">
        <input type="hidden" id="code" value="${beans.code}"/>
    </div>
    <div>
        <div class="table-responsive panel panel-table">
            <table class="table table-striped table-hover">
                <thead class="table-header">
                <tr>
                    <td style="width: 150px;">批次编号</td>
                    <td>扫描时间</td>
                    <td>设备总数</td>
                    <td>极高风险设备数</td>
                    <td>高风险设备数</td>
                    <td>中风险设备数</td>
                    <td>低风险设备数</td>
                    <td>极低风险设备数</td>
                </tr>
                </thead>
                <tbody class="table-body">
                <tr>
                    <td>${beans.code}</td>
                    <td>
                        <fmt:formatDate value="${beans.createdDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>${beans.ips}</td>
                    <td>${risk5 eq null?0:risk5}</td>
                    <td>${risk4 eq null?0:risk4}</td>
                    <td>${risk3 eq null?0:risk3}</td>
                    <td>${risk2 eq null?0:risk2}</td>
                    <td>${risk1 eq null?0:risk1}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="row float" style="margin-bottom: 15px;z-index: 10;position: relative;">
        <div class="item w300">
            <div class="form-label w100">
                <label>IP地址:</label>
            </div>
            <input type="text" class="w200" ng-model="condition.ip"/>
        </div>
        <div class="item w300">
            <div class="form-label w100">
                <label>风险等级:</label>
            </div>
            <div class="w200">
                <label ng-repeat="foo in risk" style="margin-left: {{$index==0?'0':'8px'}}">
                    <input type="checkbox" ng-model="foo.checked" ng-change="checkChange();"/><span
                        style="margin-left: 3px;">{{foo.name}}</span>
                </label>
            </div>
        </div>
    </div>
    <div style="height: 100%;position: relative;margin-top:-156px;padding: 156px 10px 10px 10px;width: 100%;z-index: 1">
        <div class="list-result no-pagination">
            <div class="block">
                <div class="block-header">
                    <div class="header-text">漏洞详情</div>
                    <div class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="query();"> 查询 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="exportData();"
                           ng-disabled="!beans.length" ng-cloak> 导出数据 </a>
                    </div>
                </div>
                <div class="block-content">
                    <div class="content-wrap">
                        <div class="table-responsive panel panel-table">
                            <table class="table table-striped table-hover">
                                <thead class="table-header">
                                <tr>
                                    <td style="width: 40px;">序号</td>
                                    <td style="width: 150px;">IP地址</td>
                                    <td>资产名</td>
                                    <td>资产描述</td>
                                    <td>操作系统</td>
                                    <td style="width: 100px;">漏洞数</td>
                                    <td style="width: 110px;">设备风险等级</td>
                                    <td>操作</td>
                                </tr>
                                </thead>
                                <tbody class="table-body">
                                <tr ng-show="!beans.length">
                                    <td colspan="8" class="text-center">没有查询到数据！</td>
                                </tr>
                                <tr bindonce ng-repeat="foo in beans" ng-cloak>
                                    <td bo-text="$index+1"></td>
                                    <td bo-text="foo[0]"></td>
                                    <td bo-text="foo[2]||''"></td>
                                    <td class="text-left" bo-text="(foo[3]||'')|substr:20" bo-title="foo[3]||''"></td>
                                    <td bo-text="foo[4]||''"></td>
                                    <td bo-text="foo[5]||0"></td>
                                    <td>
                                        <span class="bgc"
                                              ng-class="{red:foo[6]==5,ori:foo[6]==4,yellow:foo[6]==3,green1:foo[6]==2,green2:foo[6]==1}"
                                              bo-text="foo[6]|riskLevel:'code'"></span>
                                    </td>
                                    <td class="text-left">
                                        <a class="btn-op blue" ng-click="view('${beans.code}',foo[0]);">详情</a>
                                        <a class="btn-op red" ng-click="remove('${beans.code}',foo[0]);">删除</a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bugBatch/bugBatch.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bugBatch/bugBatch_detail.js"></script>
</html>