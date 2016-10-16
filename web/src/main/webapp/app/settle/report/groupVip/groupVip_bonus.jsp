<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>返佣</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/vendor/bootstrap-v3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style/standard/css/eccrm-common-new.css">
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/jquery-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/ycrl/javascript/angular-strap-all.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/vendor/My97DatePicker/WdatePicker.js"></script>
    <script>
        window.angular.contextPathURL = '<%=contextPath%>';
    </script>
</head>
<body>
<div class="main condition-row-2" ng-app="settle.report.groupVip.bonus" ng-controller="Ctrl">
    <div class="dn">
        <input type="hidden" id="year" value="${param.year}">
        <input type="hidden" id="month" value="${param.month}">
        <input type="hidden" id="company" value="${param.company}">
    </div>
    <div class="list-condition">
        <div class="block">
            <div class="block-header">
                    <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="reset();"> 重置 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="query();"> 查询 </a>
                    </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="row float">
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>交易商数量:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.vipCounts" maxlength="5"
                                   placeholder=">="/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>正常数量:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.normalCounts" maxlength="5"
                                   placeholder=">="/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>签约数量:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.assignCounts" maxlength="5"
                                   placeholder=">="/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>交易数量:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.businessCounts" maxlength="5"
                                   placeholder=">="/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>成交额:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.totalMoney" maxlength="5"
                                   placeholder=">"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>手续费:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.fee" maxlength="5" placeholder=">"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>佣金:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.commission" maxlength="5"
                                   placeholder=">"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>含税服务费:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.taxServerFee" maxlength="5"
                                   placeholder=">"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>支付金额:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.payMoney" maxlength="5"
                                   placeholder=">"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>税金:</label>
                            </div>
                            <input type="text" class="w120" ng-model="condition.tax" maxlength="5" placeholder=">"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>设置返佣:</label>
                            </div>
                            <input type="checkbox" style="width: 14px;" ng-model="condition.bonus"
                                   ng-change="query();"/>
                        </div>
                        <div class="item w200">
                            <div class="form-label w80">
                                <label>已发送短信:</label>
                            </div>
                            <input type="checkbox" style="width: 14px;" ng-model="condition.sendSms"
                                   ng-change="query();"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="list-result">
        <div class="block">
            <div class="block-header">
                <div class="header-text">
                </div>
                <span class="header-button">
                        <a type="button" class="btn btn-green btn-min" ng-click="setBonus();"> 确定返佣 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="sendSms();"> 发送短信 </a>
                        <a type="button" class="btn btn-green btn-min" ng-click="exportData();"> 导出 </a>
                </span>
            </div>
            <div class="block-content">
                <div class="content-wrap">
                    <div class="table-responsive panel panel-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-header">
                            <tr>
                                <td class="width-min">
                                    <div select-all-checkbox checkboxes="beans.data" selected-items="items"
                                         anyone-selected="anyone"></div>
                                </td>
                                <td class="width-min">序号</td>
                                <td class="cp" ng-click="order('groupName');">团队名称
                                    <span ng-show="condition.orderBy=='groupName'" ng-cloak>
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('vipCounts');">交易商数量
                                    <span ng-show="condition.orderBy=='vipCounts'" ng-cloak>
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('normalCounts');">正常数量
                                    <span ng-show="condition.orderBy=='normalCounts'" ng-cloak>
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('assignCounts');">签约数量
                                    <span ng-show="condition.orderBy=='assignCounts'" ng-cloak>
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('businessCounts');">有交易数量
                                    <span ng-show="condition.orderBy=='businessCounts'" ng-cloak>
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('totalMoney');">成交额
                                    <span ng-cloak ng-show="condition.orderBy=='totalMoney'">
                                        <span ng-cloak ng-show="condition.reverse">▼</span>
                                        <span ng-cloak ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('fee');">交易手续费
                                    <span ng-cloak ng-show="condition.orderBy=='fee'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('commission');">标准佣金
                                    <span ng-cloak ng-show="condition.orderBy=='commission'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('stepPercent');">阶梯比例
                                    <span ng-cloak ng-show="condition.orderBy=='stepPercent'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('taxServerFee');">含税服务费
                                    <span ng-cloak ng-show="condition.orderBy=='taxServerFee'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('payMoney');">支付金额
                                    <span ng-cloak ng-show="condition.orderBy=='payMoney'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('outofTax');">除税支付金额
                                    <span ng-cloak ng-show="condition.orderBy=='outofTax'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td class="cp" ng-click="order('tax');">税金
                                    <span ng-cloak ng-show="condition.orderBy=='tax'">
                                        <span ng-show="condition.reverse">▼</span>
                                        <span ng-show="!condition.reverse">▲</span>
                                    </span>
                                </td>
                                <td>发送短信</td>
                                <td>错误</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody class="table-body">
                            <tr ng-show="!beans || !beans.total">
                                <td colspan="18" class="text-center">没有查询到数据！</td>
                            </tr>
                            <tr bindonce ng-repeat="foo in beans.data" ng-cloak>
                                <td><input type="checkbox" ng-model="foo.isSelected"/></td>
                                <td bo-text="pager.start+$index+1"></td>
                                <td>
                                    <a ng-click="view(foo.id)" bo-text="foo.groupName" class="cp" title="点击查看详情"></a>
                                </td>
                                <td bo-text="foo.vipCounts"></td>
                                <td bo-text="foo.normalCounts"></td>
                                <td bo-text="foo.assignCounts"></td>
                                <td bo-text="foo.businessCounts"></td>
                                <td bo-text="foo.totalMoney|number:2"></td>
                                <td bo-text="foo.fee|number:2"></td>
                                <td bo-text="foo.commission"></td>
                                <td bo-text="foo.stepPercent"></td>
                                <td bo-text="foo.taxServerFee"></td>
                                <td bo-text="foo.payMoney|number:2"></td>
                                <td bo-text="foo.outofTax|number:2"></td>
                                <td bo-text="foo.tax|number:2"></td>
                                <td>
                                    <span class="bgc" bo-text="foo.sendSms?'成功':'否'"
                                          ng-class="{red:!foo.sendSms,green:foo.sendSms}"></span>
                                </td>
                                <td bo-text="foo.errorMsg"></td>
                                <td class="text-left">
                                    <a class="btn-op blue" ng-click="modify(foo.id);">编辑</a>
                                    <a class="btn-op red" ng-click="remove(foo.id);">删除</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="list-pagination" eccrm-page="pager"></div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/app/settle/report/groupVip/groupVip.js"></script>
<script type="text/javascript" src="<%=contextPath%>/app/settle/report/groupVip/groupVip_bonus.js"></script>
</html>