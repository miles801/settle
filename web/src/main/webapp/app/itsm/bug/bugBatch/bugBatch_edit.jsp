<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>批次编辑</title>
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
<div class="main" ng-app="itsm.bug.bugBatch.edit" ng-controller="Ctrl" style="overflow: auto; width: 1000px;margin: 0 auto;">
    <div class="block">
        <div class="block-header">
                <span class="header-button">
                    <c:if test="${pageType eq 'add'}">
                        <a class="btn btn-green btn-min" ng-click="save()" ng-disabled="!form.$valid" ng-cloak>执行导入 </a>
                    </c:if>
                    <a class="btn btn-green btn-min" ng-click="back();">返回 </a>
                </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <form name="form" class="form-horizontal" role="form">
                    <div style="display: none;">
                        <input type="hidden" id="pageType" value="${pageType}"/>
                        <input type="hidden" id="id" value="${id}"/>
                    </div>
                    <div class="row float">
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>批次编号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.code" disabled placeholder="保存后由程序自动生成"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>部门名称:</label>
                            </div>
                            <div class="w200 pr">
                                <input class="w200" type="text" ng-model="beans.orgName" ztree-single="orgNameTree"
                                       readonly
                                       placeholder="点击选择" validate validate-required/>
                                <span class="add-on">
                                    <i class="icons icon fork" ng-click="clearOrgName();" title="点击清除"></i>
                                </span>
                            </div>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.scanType">扫描类型:</label>
                            </div>
                            <select class="w200" ng-model="beans.scanType" name="scanType"
                                    ng-options="foo.value as foo.name for foo in scanTypes"
                                    validate validate-required>
                            </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.scanMachine">扫描器:</label>
                            </div>
                            <select class="w200" ng-model="beans.scanMachine" name="scanMachine"
                                    ng-options="foo.value as foo.name for foo in scanMachines"
                                    validate validate-required>
                            </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.scanRange">扫描范围:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.scanRange" maxlength="40" validate
                                   validate-required/>
                        </div>
                        <div class="item w600 break">
                            <div class="form-label w100">
                                <label>扫描说明:</label>
                            </div>
                            <input type="text" class="w500" ng-model="beans.scanPurpose" maxlength="40"  validate validate-required/>
                        </div>
                    </div>
                    <div class="row float break">
                        <div class="item w900">
                            <div class="form-label w100">
                                <label>附件:</label>
                            </div>
                            <div class="w800">
                                <div eccrm-upload="uploadOptions"></div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${pageType ne 'add'}">
                        <div class="row float break" ng-cloak>
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>创建人:</label>
                                </div>
                                <span class="w200 sl">{{beans.creatorName}}</span>
                            </div>
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>创建时间:</label>
                                </div>
                                <div class="w200 sl">{{beans.createdDatetime|eccrmDatetime}}</div>
                            </div>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bugBatch/bugBatch.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/org/org.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bugBatch/bugBatch_edit.js"></script>
</html>