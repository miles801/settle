<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>资产管理风险编辑</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="itsm.asset.riskManage.edit" ng-controller="Ctrl" style="overflow: auto;">
    <div class="block">
        <div class="block-header">
                <span class="header-button">
                    <c:if test="${pageType eq 'add'}">
                        <a class="btn btn-green btn-min" ng-click="save()" ng-disabled="!form.$valid">保存 </a>
                        <a class="btn btn-green btn-min" ng-click="save(true)" ng-disabled="!form.$valid">保存并新建 </a>
                    </c:if>
                    <c:if test="${pageType eq 'modify'}">
                        <a type="button" class="btn btn-green btn-min" ng-click="update()" ng-disabled="!form.$valid"> 更新 </a>
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
                        <input type="hidden" id="assetId" value="${assetId}"/>
                    </div>
                    <div class="row float">

                        <div class="item w300">
                            <div class="form-label w100">
                                <label>资产名称:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.assetName"
                                   validate validate-required maxlength="40" readonly placeholder="自动带入" disabled/>
                        </div>

                        <div class="item w300">
                            <div class="form-label w100">
                                <label>资产值:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.assetValue" placeholder="自动带入"
                                   ng-change="valueChange();" disabled/>
                        </div>

                        <div class="item w300">
                            <div class="form-label w100">
                                <label>资产组名称:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.assetGroupName" disabled
                                   placeholder="自动带入"/>
                        </div>

                        <div class="item w300">
                            <div class="form-label w100">
                                <label>威胁:</label>
                            </div>
                            <div class="w200 pr">
                                <input type="text" class="w200" ng-model="beans.wxName" validate validate-required
                                       readonly placeholder="点击选择" ztree-single="wxTree"/>
                                <span class="add-on"><i class="icons icon fork" ng-click="clearWx();"></i></span>
                            </div>
                        </div>

                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.wxValue">威胁值T:</label>
                            </div>
                            <select class="w200" ng-model="beans.wxValue" name="wxValue" validate validate-required
                                    ng-options="foo.value as foo.name for foo in values" ng-change="valueChange();">
                            </select>
                        </div>


                        <div class="item w300">
                            <div class="form-label w100">
                                <label>弱点:</label>
                            </div>
                            <div class="w200 pr">
                                <input type="text" class="w200" ng-model="beans.rdName" validate validate-required
                                       readonly placeholder="点击选择" ztree-single="rdTree"/>
                                <span class="add-on"><i class="icons icon fork" ng-click="clearRd();"></i></span>
                            </div>
                        </div>

                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.rdValue">弱点值V:</label>
                            </div>
                            <select class="w200" ng-model="beans.rdValue" name="rdValue" validate validate-required
                                    ng-options="foo.value as foo.name for foo in values" ng-change="valueChange();">
                            </select>
                        </div>

                        <div class="item w300">
                            <div class="form-label w100">
                                <label>风险值:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.fxValue" validate validate-required
                                   readonly placeholder="自动计算"/>
                        </div>

                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.fxLevel">管理风险等级:</label>
                            </div>
                            <select class="w200" ng-model="beans.fxLevel" name="fxLevel" validate validate-required
                                    ng-options="foo.value as foo.name for foo in values" disabled>
                            </select>
                        </div>

                        <div class="item w300">
                            <div class="form-label w100">
                                <label>风险描述:</label>
                            </div>
                            <div class="w200 pr">
                                <input type="text" class="w200" ng-model="beans.fxmsName" validate validate-required
                                       readonly placeholder="点击选择" ztree-single="fxmsTree"/>
                                <span class="add-on"><i class="icons icon fork" ng-click="clearFxms();"></i></span>
                            </div>
                        </div>

                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.handlePolicy">处置策略:</label>
                            </div>
                            <select class="w200" ng-model="beans.handlePolicy" name="handlePolicy"
                                    validate validate-required
                                    ng-options="foo.value as foo.name for foo in handlePolicys">
                            </select>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>已有控制:</label>
                            </div>

                            <textarea rows="6" class="w800" ng-model="beans.ctrl" validate validate-required
                                   maxlength="1000"></textarea>
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
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>最后更新人:</label>
                                </div>
                                <span class="w200 sl">{{beans.modifierName}}</span>
                            </div>
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>最后时间:</label>
                                </div>
                                <div class="w200 sl">{{beans.modifiedDatetime|eccrmDatetime}}</div>
                            </div>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/riskManage/riskManage.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/asset/asset.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/assetGroup/assetGroup.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/otherSetting/otherSetting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/riskManage/riskManage_edit.js"></script>
</html>