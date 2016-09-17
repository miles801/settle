<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>编辑资产</title>
    <meta content="text/html" charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script type="text/javascript">
        window.angular.contextPathURL = "<%=contextPath%>";
    </script>
</head>
<body>
<div class="main" ng-app="itsm.asset.edit" ng-controller="Ctrl">
    <div class="block">
        <div class="block-header">
                <span class="header-text">
                    <span class="glyphicons info-sign"></span>
                </span>
                <span class="header-button">
                    <c:if test="${pageType eq 'add'}">
                    <button type="button" class="btn btn-green btn-min" ng-click="save()" ng-disabled="form.$invalid">
                        <span class="glyphicons disk_save"></span> 保存
                    </button>
                        <button type="button" class="btn btn-green btn-min" ng-click="save(true)"
                                ng-disabled="form.$invalid">
                            <span class="glyphicons disk_open"></span> 保存并新建
                        </button>
                    </c:if>
                    <c:if test="${pageType eq 'clone'}">
                        <button type="button" class="btn btn-green btn-min" ng-click="save()"
                                ng-disabled="form.$invalid">保存
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
                            <label>资产名称:</label>
                        </div>
                        <input class="col-2-half" type="text" ng-model="beans.name"
                               validate validate-required maxlength="20"/>
                        <div class="form-label col-1-half">
                            <label>部门:</label>
                        </div>
                        <div class="col-2-half">
                            <input class="col-12" type="text" ng-model="beans.orgName" ztree-single="orgTree" readonly
                                   validate validate-required maxlength="40" placeholder="点击选择"/>
                            <span class="add-on">
                                <i class="icons icon fork" ng-click="clearOrg();" title="点击清除"></i>
                            </span>
                        </div>

                        <div class="form-label col-1-half">
                            <label>资产编号:</label>
                        </div>
                        <input class="col-2-half" type="text" ng-model="beans.code" readonly placeholder="由后台自动创建"/>

                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.name">类型:</label>
                        </div>
                        <div class="col-2-half">
                            <input class="col-12" type="text" ng-model="beans.assetTypeName1"
                                   validate validate-required maxlength="20" readonly placeholder="点击选择"
                                   ztree-single="assetTypeTree"/>
                            <span class="add-on"><i class="icons icon fork" ng-click="clearAssetType();" title="清除"></i></span>
                        </div>
                        <div class="form-label col-1-half">
                            <label validate-error="form.name">二级分类:</label>
                        </div>
                        <input type="text" class="col-2-half" disabled ng-model="beans.assetTypeName2">
                        <div class="form-label col-1-half">
                            <label validate-error="form.name">三级分类:</label>
                        </div>
                        <input type="text" class="col-2-half" disabled ng-model="beans.assetTypeName3">
                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label>所属系统:</label>
                        </div>
                        <div class="col-2-half">
                            <input class="col-12" type="text" ng-model="beans.appName" ztree-single="appTree" readonly
                                   validate validate-required maxlength="40" placeholder="点击选择"/>
                            <span class="add-on">
                                <i class="icons icon fork" ng-click="clearApp();" title="点击清除"></i>
                            </span>
                        </div>
                        <div class="form-label col-1-half">
                            <label>资产数量:</label>
                        </div>
                        <input type="text" class="col-2-half" ng-model="beans.counts"/>
                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label>资产位置:</label>
                        </div>
                        <select ng-model="beans.location" class="col-2-half"
                                ng-options="foo.value as foo.name for foo in locations"></select>
                        <div class="form-label col-1-half">
                            <label>位置说明:</label>
                        </div>
                        <input type="text" class="col-2-half" ng-model="beans.location2" max="50"/>

                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.ip">IP地址:</label>
                        </div>
                        <input type="text" class="col-2-half" ng-model="beans.ip" validate validate-options="ip"
                               name="ip"/>
                        <span class="sl" style="color: #fb0f0f; margin-left: 10px; line-height: 24px">* 资产类型为“软件”，“硬件(服务器、网络设备、安全设备)”时，必填</span>
                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.description">资产说明:</label>
                        </div>
                        <textarea class="col-10-half" rows="6" ng-model="beans.description" maxlength="500"
                                  validate validate-required name="description"></textarea>
                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label>管理者:</label>
                        </div>
                        <div class="col-6-half">
                            <input type="text" class="col-12" ng-model="beans.masterName" placeholder="点击选择"
                                   readonly ng-click="pickMaster();"/>
                            <span class="add-on">
                                <i class="icons icon fork" ng-click="clearMaster();" title="点击清除"></i>
                            </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.userName">使用者:</label>
                        </div>
                        <div class="col-6-half">
                            <input type="text" class="col-12" ng-model="beans.userName" validate validate-required
                                   name="userName"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label>资产组:</label>
                        </div>
                        <div class="col-2-half">
                            <input class="col-12" type="text" ng-model="beans.assetGroupName"
                                   ztree-single="assetGroupTree" readonly
                                   validate validate-required maxlength="40" placeholder="点击选择"/>
                            <span class="add-on">
                                <i class="icons icon fork" ng-click="clearAssetGroup();" title="点击清除"></i>
                            </span>
                        </div>
                        <div class="form-label col-1-half">
                            <label>资产价值:</label>
                        </div>
                        <input type="text" class="col-2-half" ng-model="beans.assetValue" disabled
                               placeholder="取值与MAX(C,I,A)"/>
                    </div>
                    <c:if test="${pageType ne 'add' && pageType ne 'clone'}">
                        <div class=" row">
                            <div class="form-label col-1-half">
                                <label>创建人:</label>
                            </div>
                            <span class="col-2-half">{{beans.creatorName}}</span>
                            <div class="form-label col-1-half">
                                <label>创建时间:</label>
                            </div>
                            <div class="col-2-half">{{beans.createdDatetime|eccrmDatetime}}</div>
                        </div>
                        <div class="row">
                            <div class="form-label col-1-half">
                                <label>最后更新人:</label>
                            </div>
                            <span class="col-2-half">{{beans.modifierName}}</span>
                            <div class="form-label col-1-half">
                                <label>最后时间:</label>
                            </div>
                            <div class="col-2-half">{{beans.modifiedDatetime|eccrmDatetime}}</div>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/base/org/org.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/asset/asset.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/appSetting/appSetting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/assetGroup/assetGroup.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/emp/emp.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/asset/asset/edit/asset_edit.js"></script>
</html>