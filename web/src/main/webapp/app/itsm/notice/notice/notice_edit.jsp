<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>公告编辑</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-ztree-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-upload.js"></script>
    <%-- 富文本 --%>
    <script type="text/javascript" src="<%=contextPath%>/vendor/kindeditor-4.1.10/kindeditor-min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/kindeditor-4.1.10/lang/zh_CN.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="itsm.notice.notice.edit" ng-controller="Ctrl" style="overflow: auto;">
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
                <form name="form" class="form-horizontal" role="form" style="width: 1000px;margin: 0 auto;">
                    <div style="display: none;">
                        <input type="hidden" id="pageType" value="${pageType}"/>
                        <input type="hidden" id="id" value="${id}"/>
                    </div>
                    <div class="row float">
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>编号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.code" maxlength="40" disabled
                                   placeholder="由后台自动生成"/>
                        </div>
                        <div class="item w600">
                            <div class="form-label w100">
                                <label>标题:</label>
                            </div>
                            <input type="text" class="w500" ng-model="beans.title" validate validate-required
                                   maxlength="100"/>
                        </div>
                        <div class="item w300 break">
                            <div class="form-label w100">
                                <label validate-error="form.category">栏目:</label>
                            </div>
                            <select class="w200" ng-model="beans.category" name="category" validate validate-required
                                    ng-options="foo.value as foo.name for foo in categorys">
                            </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.important">重要性:</label>
                            </div>
                            <select class="w200" ng-model="beans.important" name="important"
                                    ng-options="foo.value as foo.name for foo in importants">
                            </select>
                        </div>
                        <div class="item w150">
                            <div class="form-label w100">
                                <label>是否顶置:</label>
                            </div>
                            <input type="checkbox" style="width: 14px;" ng-model="beans.top"/>
                        </div>
                        <div class="item w150">
                            <div class="form-label w100">
                                <label>是否提醒:</label>
                            </div>
                            <input type="checkbox" style="width: 14px;" ng-model="beans.notice"/>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>关键字:</label>
                            </div>
                            <input type="text" class="w800" ng-model="beans.keywords" maxlength="100"
                                   placeholder="多个关键字请使用逗号或者空格进行分隔"/>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>内容:</label>
                            </div>
                            <textarea class="w800" rows="16" id="content"></textarea>
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
                    <h3 class="text-center" style="width: 900px;">发布范围</h3>
                    <hr class="ycrl split" style="width: 900px;margin: 20px 15px;"/>
                    <div class="row float" style="margin-bottom: 15px;" ng-cloak>
                        <div class="item  w300">
                            <div class="form-label w100">
                                <label>全部:</label>
                            </div>
                            <input style="width: 18px" type="checkbox" ng-model="receivers.all">
                        </div>
                        <div style="clear: both" ng-show="!receivers.all">
                            <div class="item w900 break">
                                <div class="form-label w100">
                                    <label>部门:</label>
                                </div>
                                <div class="w800 pr">
                                    <input type="text" class="w800" readonly placeholder="点击选择部门"
                                           ng-model="receivers.orgName" ztree-single="orgTree">
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
                                           ng-model="receivers.positionName"
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
                                           ng-model="receivers.empName"
                                           ng-click="pickEmp();">
                                    <span class="add-on"><i class="icons icon fork" ng-click="clearEmp();"
                                                            title="清除选择的员工"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/notice/notice/notice.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/org/org.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/emp/emp.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/base/position/position.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/notice/notice/notice_edit.js"></script>
</html>