<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>文件管理编辑</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/zTree/css/ztree.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-upload.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="itsm.files.files.edit" ng-controller="Ctrl" style="overflow: auto;">
    <div class="block">
        <div class="block-header">
                <span class="header-button">
                    <c:if test="${pageType eq 'add'}">
                        <a class="btn btn-green btn-min" ng-click="save()" ng-disabled="!form.$valid">发布 </a>
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
                                <label validate-error="form.scope1">管理框架:</label>
                            </div>
                            <div class="w200 pr">
                                <input class="w200" type="text" ng-model="beans.scope1Name"
                                       validate validate-required maxlength="20" readonly placeholder="点击选择"
                                       ztree-single="scopeTree"/>
                                <span class="add-on"><i class="icons icon fork" ng-click="clearScope();" title="清除"></i></span>
                            </div>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.scope2">管理域:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.scope2Name" disabled validate
                                   validate-required name="scope2" pattern="自动获取"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>版本:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.version" maxlength="10"/>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>管理条款:</label>
                            </div>
                            <textarea class="w800" ng-model="beans.content" maxlength="2000" rows="8">
                            </textarea>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>文件名:</label>
                            </div>
                            <input type="text" class="w800" ng-model="beans.name" validate validate-required
                                   maxlength="200"
                                   placeholder="提示：上传文件时会自动获取文件名称，单个文件限制为10M，仅限文档类型"/>
                        </div>
                        <div class="item w300 break">
                            <div class="form-label w100">
                                <label validate-error="form.level">级别:</label>
                            </div>
                            <select class="w200" ng-model="beans.level" name="level"
                                    ng-options="foo.value as foo.name for foo in levels"
                                    validate validate-required>
                            </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.type">文件类型:</label>
                            </div>
                            <select class="w200" ng-model="beans.type" name="type"
                                    ng-options="foo.value as foo.name for foo in types"
                                    validate validate-required>
                            </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.secret">密级:</label>
                            </div>
                            <select class="w200" ng-model="beans.secret" name="secret"
                                    ng-options="foo.value as foo.name for foo in secrets"
                                    validate validate-required>
                            </select>
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
                    <h3 class="text-center" style="width: 900px;">分发范围</h3>
                    <hr class="ycrl split" style="width: 900px;margin: 20px 15px;"/>
                    <div class="row float" style="margin-bottom: 15px;">
                        <div class="item  w300">
                            <div class="form-label w100">
                                <label>全部:</label>
                            </div>
                            <input style="width: 24px" type="checkbox" ng-model="receiver.all">
                        </div>
                        <div style="clear: both" ng-show="!receiver.all">
                            <div class="item w900 break">
                                <div class="form-label w100">
                                    <label>部门:</label>
                                </div>
                                <div class="w800 pr">
                                    <input type="text" class="w800" readonly placeholder="点击选择部门"
                                           ng-model="receiver.orgName" ztree-single="orgTree">
                                    <span class="add-on"><i class="icons icon fork" title="清除选择的部门"
                                                            ng-click="clearOrg();"></i></span>
                                </div>
                            </div>
                            <div class="item w900">
                                <div class="form-label w100">
                                    <label>岗位:</label>
                                </div>
                                <div class="w800 pr">
                                    <input type="text" class="w800" readonly placeholder="点击选择岗位"
                                           ng-model="receiver.positionName"
                                           ng-click="pickPosition();">
                                    <span class="add-on"><i class="icons icon fork" ng-click="clearPosition();"
                                                            title="清除选择的岗位"></i></span>
                                </div>
                            </div>
                            <div class="item w900">
                                <div class="form-label w100">
                                    <label>个人:</label>
                                </div>
                                <div class="w800 pr">
                                    <input type="text" class="w800" readonly placeholder="点击选择员工"
                                           ng-model="receiver.empName"
                                           ng-click="pickEmp();">
                                    <span class="add-on"><i class="icons icon fork" ng-click="clearEmp();"
                                                            title="清除选择的员工"></i></span>
                                </div>
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/files/files/files.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/org/org.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/emp/emp.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/position/position.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/files/files/files_edit.js"></script>
</html>