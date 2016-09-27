/**
 * 团队佣金列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('settle.report.vip', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.vip.vip'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, VipService, VipParam) {
        var defaults = {// 默认查询条件
            orderBy: 'totalMoney',
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
        $scope.companys = [{name: '全部'}];
        VipParam.company(function (o) {
            $scope.companys.push.apply($scope.companys, o);
        });

        // 查询数据
        $scope.query = function () {
            var param = angular.extend({}, $scope.condition);
            if (!param.occurDate) {
                AlertFactory.error('请选择需要统计的时间段!');
                return;
            }
            var d = moment(param.occurDate);
            param.occurDate1 = new Date(d.format('YYYY-MM-01')).getTime();
            d.add(1, 'M');
            param.occurDate2 = new Date(d.format('YYYY-MM-01')).getTime();
            param.occurDate = null;
            $scope.beans = [];
            var promise = VipService.analysis(param, function (data) {
                param = null;
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

        // 删除或批量删除
        $scope.remove = function (id) {
            if (!id) {
                var ids = [];
                angular.forEach($scope.items || [], function (o) {
                    ids.push(o.id);
                });
                id = ids.join(',');
            }
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-danger">数据一旦删除将不可恢复，请确认!</span>',
                callback: function () {
                    var promise = GroupBonusService.deleteByIds({ids: id}, function () {
                        AlertFactory.success('删除成功!');
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };

        // 新增
        $scope.add = function () {
            CommonUtils.addTab({
                title: '新增团队佣金',
                url: '/settle/report/groupBonus/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新团队佣金',
                url: '/settle/report/groupBonus/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看团队佣金',
                url: '/settle/report/groupBonus/detail?id=' + id
            });
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

        $scope.query();

    });

})(window, angular, jQuery);