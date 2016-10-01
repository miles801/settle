/**
 * 团队佣金列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('settle.report.vip', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.report.groupVip'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, GroupVipService, GroupVipParam) {
        var defaults = {// 默认查询条件
            orderBy: 'assignCounts',
            reverse: true,
            occurDate: new Date().getTime()
        };

        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };


        // 参数：文交所
        $scope.companys = [{name: '请选择...'}];
        GroupVipParam.company(function (o) {
            $scope.companys.push.apply($scope.companys, o);
        });

        // 查询数据
        $scope.query = function () {
            if (!$scope.condition.company) {
                AlertFactory.warning('请选择文交所!');
                return;
            }
            $scope.beans = [];
            var promise = GroupVipService.analysis1($scope.condition, function (data) {
                $scope.beans = data.data || [];

                // 设置文交所名称
                var c = {};
                angular.forEach($scope.companys, function (o) {
                    c[o.value] = o.name;
                });
                angular.forEach($scope.beans, function (o) {
                    o.companyName = c[o.company];
                });
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        // 导出数据
        $scope.exportData = function () {
            if ($scope.pager.total < 1) {
                AlertFactory.error('未获取到可以导出的数据!请先查询出数据!');
                return;
            }
            var o = angular.extend({}, $scope.condition);
            o.start = null;
            o.limit = null;
            window.open(CommonUtils.contextPathURL('/settle/report/groupBonus/export?' + encodeURI(encodeURI($.param(o)))));
        };

        $scope.order = function (key) {
            if ($scope.condition.orderBy == key) {
                $scope.condition.reverse = !$scope.condition.reverse;
            } else {
                $scope.condition.orderBy = key;
                $scope.condition.reverse = false;
            }
            $scope.query();
        };
    });

})(window, angular, jQuery);