/**
 * 资产对比列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.asset.assetCompare.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.asset.assetCompare'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, AssetCompareService, AssetCompareModal) {
        var defaults = {}; // 默认查询条件

        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };


        // 查询数据
        $scope.query = function () {
            $scope.pager.query();
        };
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {start: this.start, limit: this.limit}, $scope.condition);
                $scope.beans = [];
                return CommonUtils.promise(function (defer) {
                    var promise = AssetCompareService.pageQuery(param, function (data) {
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
                    var promise = AssetCompareService.deleteByIds({ids: id}, function () {
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
                title: '新增资产对比',
                url: '/itsm/asset/assetCompare/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新资产对比',
                url: '/itsm/asset/assetCompare/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看资产对比',
                url: '/itsm/asset/assetCompare/detail?id=' + id
            });
        };


        // 重新比对
        $scope.retry = function (id, ip) {
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-danger">确定要对[' + ip + ']网段重新进行比对？</span>',
                callback: function () {
                    var promise = AssetCompareService.retry({ids: id}, function () {
                        AlertFactory.success('操作成功!');
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
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
            window.open(CommonUtils.contextPathURL('/itsm/asset/assetCompare/export?' + encodeURI(encodeURI($.param(o)))));
        };

        // 查看未登记IP
        $scope.ips = function (id, ip) {
            AssetCompareModal.ips(id, ip);
        };
    });
})(window, angular, jQuery);