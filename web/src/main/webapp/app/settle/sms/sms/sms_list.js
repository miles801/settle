/**
 * 短信列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('settle.sms.sms.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.sms.sms',
        'settle.sms.smsBuy'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, SmsService, SmsBuyService, SmsBuyModal) {
        var defaults = {}; // 默认查询条件

        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };

        $scope.querySms = function () {
            SmsService.get(function (o) {
                $scope.sms = o.data || {no: true};
            });
        };

        $scope.querySms();

        // 查询数据
        $scope.query = function () {
            $scope.pager.query();
        };
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {start: this.start, limit: this.limit}, $scope.condition);
                $scope.beans = [];
                return CommonUtils.promise(function (defer) {
                    var promise = SmsBuyService.pageQuery(param, function (data) {
                        param = null;
                        $scope.beans = data.data || {total: 0};
                        defer.resolve($scope.beans);
                    });
                    CommonUtils.loading(promise, 'Loading...');
                });
            },
            finishInit: function () {
                this.query();
            }
        };

        // 启用
        $scope.enable = function (id) {
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-success">是否启用，请确认!</span>',
                callback: function () {
                    var promise = SmsService.enable({ids: new Date().getTime()}, function () {
                        AlertFactory.success('操作成功!');
                        $scope.querySms();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };

        // 禁用
        $scope.disable = function (id) {
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-danger">短信功能禁用后将无法发送短信，请确认!</span>',
                callback: function () {
                    var promise = SmsService.disable({ids: new Date().getTime()}, function () {
                        AlertFactory.success('操作成功!');
                        $scope.querySms();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };

        $scope.init = function () {
            SmsService.init(function () {
                AlertFactory.success('初始化成功!');
                $scope.querySms();
            });
        };

        // 充值
        $scope.buy = function () {
            SmsBuyModal.add(function () {
                $scope.querySms();
                $scope.query();
            });
        };


    });
})(window, angular, jQuery);