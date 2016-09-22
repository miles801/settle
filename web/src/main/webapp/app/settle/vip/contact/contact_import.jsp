<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <base href="<%=request.getContextPath()%>/">
    <title>通讯录导入</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-upload.js"></script>

    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="settle.vip.contact.import" ng-controller="Ctrl">
    <div class="block">
        <div class="block-header">
            <span class="header-text"> </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <form name="form" class="form-horizontal" role="form">
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.company">所属文交所:</label>
                        </div>
                        <select class="col-2" ng-model="company" name="company"
                                ng-options="foo.value as foo.name for foo in companys"
                                validate validate-required></select>
                    </div>
                    <div class="row" eccrm-upload="fileUpload"></div>
                    <div class="row" style="margin-left: 10.5%;margin-top:8px;">
                        <p style="font-size: 14px;font-weight: 700;">注意：</p>

                        <p>1. 附件不支持多页签(只会读取sheet1的数据)!</p>

                        <p>2. 如果数据不正确，将会全部失败!</p>

                    </div>
                    <div class="button-row">
                        <a class="btn" ng-href="<%=contextPath%>/settle/vip/contact/template" target="_blank"
                           style="width: 160px;height: 50px;line-height: 50px;">下载数据模板</a>
                        <button class="btn" ng-click="importData();" ng-disabled="!canImport"
                                style="margin-left:80px;width: 150px;">执行导入
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app//settle/vip/contact/contact.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app//settle/vip/contact/contact_import.js"></script>
</html>
