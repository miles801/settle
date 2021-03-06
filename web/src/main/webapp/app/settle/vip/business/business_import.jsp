<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <base href="<%=request.getContextPath()%>/">
    <title>交易导入</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-upload.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/moment/moment.min.js" class="js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main" ng-app="settle.vip.business.import" ng-controller="Ctrl">
    <div class="block">
        <div class="block-header">
            <span class="header-text"> </span>
        </div>
        <div class="block-content">
            <div class="content-wrap">
                <form name="form" class="form-horizontal" role="form">
                    <div class="row">
                        <div class="form-label col-1-half">
                            <label validate-error="form.company">文交所:</label>
                        </div>
                        <select class="col-2" ng-model="company" name="company"
                                ng-options="foo.value as foo.name for foo in companys"
                                validate validate-required></select>
                        <div class="form-label col-1-half">
                            <label validate-error="form.date">交易时间:</label>
                        </div>
                        <div class="col-2-half">
                            <input type="text" class="col-12" ng-model="date" name="date"
                                   validate validate-required readonly eccrm-my97="{dateFmt:'yyyy-MM'}"/>
                            <span class="add-on"><i class="icons icon clock" ng-click="date=null"></i></span>
                        </div>
                    </div>
                    <div class="row" eccrm-upload="fileUpload"></div>
                    <div class="row" style="margin-left: 10.5%;margin-top:8px;">
                        <p style="font-size: 14px;font-weight: 700;">注意：</p>

                        <p>1. 附件不支持多页签(只会读取sheet1的数据)!</p>

                        <p>2. 如果数据不正确，将会全部失败!</p>

                    </div>
                    <div class="button-row">
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
<script type="text/javascript" src="<%=contextPath%>/app//settle/vip/business/business.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app//settle/vip/business/business_import.js"></script>
</html>
