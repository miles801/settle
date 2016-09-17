<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>编辑资产组</title>
    <meta content="text/html" charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        window.angular.contextPathURL = "<%=contextPath%>";
    </script>
</head>
<body>
<div class="main" ng-app="asset.assetGroup.edit" ng-controller="Ctrl">
    <div class="block">
        <div class="block-header">
                <span class="header-text">
                    <span class="glyphicons info-sign"></span>
                </span>
            <span class="header-button">
                    <c:if test="${pageType eq 'add' or pageType eq 'clone'}">
                    <button type="button" class="btn btn-green btn-min" ng-click="save()" ng-disabled="form.$invalid">
                        <span class="glyphicons disk_save"></span> 保存
                    </button>
                        <button type="button" class="btn btn-green btn-min" ng-click="save(true)"
                                ng-disabled="form.$invalid">
                            <span class="glyphicons disk_open"></span> 保存并新建
                        </button>
                    </c:if>
                    <c:if test="${pageType eq 'modify'}">
                    <button type="button" class="btn btn-green btn-min" ng-click="update()" ng-disabled="form.$invalid">
                        <span class="glyphicons claw_hammer"></span> 更新
                    </button>
                    </c:if>
                    <a type="button" class="btn btn-green btn-min" ng-click="back();">
                        <span class="glyphicons message_forward"></span> 返回
                    </a>
                </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <form name="form" class="form-horizontal" role="form">
                    <div style="display: none;">
                        <input type="hidden" id="pageType" value="${pageType}"/>
                        <input type="hidden" id="id" value="${id}"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.code">组编号:</label>
                        </div>
                        <input class="col-2-half" type="text" ng-model="beans.code" validate validate-required
                               maxlength="20" placeholder="不可重复" name="code"/>
                        <div class="form-label col-1-half">
                            <label validate-error="form.assetType">资产类型:</label>
                        </div>
                        <select ng-model="beans.assetType" class="col-2-half" name="assetType"
                                ng-options="foo.value as foo.name for foo in types"
                                validate validate-required>
                        </select>
                        <div class="form-label col-1-half">
                            <label validate-error="form.name">组名称:</label>
                        </div>
                        <input class="col-2-half" type="text" ng-model="beans.name" name="name"
                               validate validate-required maxlength="20"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.c">资产价值C:</label>
                        </div>
                        <select ng-model="beans.valueC" class="col-2-half" validate validate-required name="c"
                                ng-options="foo.value as foo.name for foo in values">
                        </select>
                        <div class="form-label col-1-half">
                            <label validate-error="form.i">资产价值I:</label>
                        </div>
                        <select ng-model="beans.valueI" class="col-2-half" validate validate-required name="i"
                                ng-options="foo.value as foo.name for foo in values">
                        </select>
                        <div class="form-label col-1-half">
                            <label validate-error="form.a">资产价值A:</label>
                        </div>
                        <select ng-model="beans.valueA" class="col-2-half" validate validate-required name="a"
                                ng-options="foo.value as foo.name for foo in values">
                        </select>
                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.source">资产来源:</label>
                        </div>
                        <select ng-model="beans.source" class="col-2-half" name="source"
                                ng-options="foo.value as foo.name for foo in sources"
                                validate validate-required>
                        </select>
                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.description">资产说明:</label>
                        </div>
                        <textarea class="col-10-half" rows="6" ng-model="beans.description" maxlength="500"
                                  validate validate-required name="description"></textarea>
                    </div>
                    <c:if test="${pageType ne 'add' and pageType ne 'clone'}">
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>创建人:</label>
                            </div>
                            <span class="col-2-half sl">{{beans.creatorName}}</span>
                            <div class="form-label col-1-half">
                                <label>创建时间:</label>
                            </div>
                            <div class="col-2-half sl">{{beans.createdDatetime|eccrmDatetime}}</div>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>最后更新人:</label>
                            </div>
                            <span class="col-2-half sl">{{beans.modifierName}}</span>
                            <div class="form-label col-1-half">
                                <label>最后时间:</label>
                            </div>
                            <div class="col-2-half sl">{{beans.modifiedDatetime|eccrmDatetime}}</div>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/assetGroup/assetGroup.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/assetGroup/edit/assetGroup_edit.js"></script>
</html>