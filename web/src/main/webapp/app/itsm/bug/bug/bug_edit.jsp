<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>漏洞编辑</title>
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
<div class="main" ng-app="itsm.bug.bug.edit" ng-controller="Ctrl" style="overflow: auto;">
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
                    </div>
                    <div class="row float">
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>批次号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.batchId" disabled/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>IP地址:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.ip" validate validate-required
                                   maxlength="20" disabled/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>bug编号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.code" maxlength="40"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>风险值:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.riskValue"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.riskLevel">风险等级:</label>
                            </div>
                            <select class="w200" ng-model="beans.riskLevel" name="riskLevel"
                                    ng-options="foo.value as foo.name for foo in riskLevels">
                            </select>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label validate-error="form.protocol">协议类型:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.protocol"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>端口号:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.port"/>
                        </div>
                        <div class="item w300">
                            <div class="form-label w100">
                                <label>状态:</label>
                            </div>
                            <input type="text" class="w200" ng-model="beans.statusName" disabled/>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>漏洞名称:</label>
                            </div>
                            <input type="text" class="w800" ng-model="beans.name"/>
                        </div>
                        <div class="item w900">
                            <div class="form-label w100">
                                <label>操作系统:</label>
                            </div>
                            <input type="text" class="w800" ng-model="beans.operate" maxlength="40"/>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>摘要:</label>
                            </div>
                            <textarea rows="2" class="w800" ng-model="beans.synopsis" maxlength="1000"></textarea>
                        </div>
                        <c:if test="${pageType ne 'detail'}">
                            <div class="item w900 break">
                                <div class="form-label w100">
                                    <label>漏洞描述:</label>
                                </div>
                                <textarea rows="4" class="w800" ng-model="beans.description"
                                          maxlength="2000"></textarea>
                            </div>
                        </c:if>
                        <c:if test="${pageType eq 'detail'}">
                            <div class="item w900 break">
                                <div class="form-label w100">
                                    <label>漏洞描述:</label>
                                </div>
                                <textarea rows="4" class="w800" ng-model="beans.description" maxlength="2000"
                                          ng-show="!translate"></textarea>
                                <textarea rows="4" class="w800" ng-model="beans.descriptionName" maxlength="2000"
                                          ng-show="translate"></textarea>
                            </div>
                            <div class="item w100" style="padding-left: 15px;">
                                <span ng-show="beans.description!=beans.descriptionName" ng-cloak>
                                    <a class="btn btn-blue" ng-show="translate" ng-click="translate=false"
                                       ng-cloak> 英文 </a>
                                    <a class="btn btn-blue" ng-show="!translate" ng-click="translate=true"
                                       ng-cloak> 中文 </a>
                                </span>
                                <span ng-if="beans.description==beans.descriptionName && beans.code" ng-cloak>
                                    <a class="btn btn-blue" ng-show="translate"
                                       ng-click="addBugLib(beans.id);"> 补充漏洞中文库 </a>
                                </span>
                            </div>
                        </c:if>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>修复建议:</label>
                            </div>
                            <textarea rows="3" class="w800" ng-model="beans.solution" maxlength="1000"></textarea>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>其他:</label>
                            </div>
                            <textarea rows="2" class="w800" ng-model="beans.other" maxlength="1000"></textarea>
                        </div>
                        <div class="item w900 break">
                            <div class="form-label w100">
                                <label>输出信息:</label>
                            </div>
                            <textarea rows="6" class="w800" ng-model="beans.output" maxlength="1000"></textarea>
                        </div>
                        <c:if test="${pageType eq 'detail'}">
                            <div class="item w300 break">
                                <div class="form-label w100">
                                    <label>整改人:</label>
                                </div>
                                <input type="text" class="w200" ng-model="beans.masterName" disabled/>
                            </div>
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>优先级:</label>
                                </div>
                                <input type="text" class="w200" ng-model="beans.priorityName" disabled/>
                            </div>
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>整改时限:</label>
                                </div>
                                <span class="w200 sl">{{beans.endDate|eccrmDate}}</span>
                            </div>
                            <div class="item w300">
                                <div class="form-label w100">
                                    <label>整改后等级:</label>
                                </div>
                                <span class="w200 sl">{{beans.repairRiskLevel|riskLevel}}</span>
                            </div>
                            <div class="item w900 break">
                                <div class="form-label w100">
                                    <label>整改说明:</label>
                                </div>
                                <textarea rows="6" class="w800" ng-model="beans.repairDesc" maxlength="1000"></textarea>
                            </div>
                            <div class="item w900 break">
                                <div class="form-label w100">
                                    <label>修复证据:</label>
                                </div>
                                <div id="certify" class="w800"></div>
                            </div>
                        </c:if>
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
                            <div class="item w300 break">
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
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/itsm/bug/bug/bug_edit.js"></script>
</html>