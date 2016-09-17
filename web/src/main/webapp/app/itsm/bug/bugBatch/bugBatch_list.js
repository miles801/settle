/**
 * 批次列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bugBatch.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bugBatch'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugBatchService, BugBatchParam) {
        var defaults = {}; // 默认查询条件

        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };


        // 参数：扫描类型
        $scope.scanTypes = [{name: '全部'}];
        BugBatchParam.scanType(function (o) {
            $scope.scanTypes.push.apply($scope.scanTypes, o);
        });

        // 参数：扫描器
        $scope.scanMachines = [{name: '全部'}];
        BugBatchParam.scanMachine(function (o) {
            $scope.scanMachines.push.apply($scope.scanMachines, o);
        });


        // 查询数据
        $scope.query = function () {
            $scope.pager.query();
        };
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {start: this.start, limit: this.limit}, $scope.condition);
                $scope.beans = [];
                return CommonUtils.promise(function (defer) {
                    var promise = BugBatchService.pageQuery(param, function (data) {
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
                    var promise = BugBatchService.deleteByIds({ids: id}, function () {
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
                title: '新增批次',
                url: '/itsm/bug/bugBatch/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新批次',
                url: '/itsm/bug/bugBatch/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看批次',
                url: '/itsm/bug/bugBatch/detail?id=' + id
            });
        };
        // 详情
        $scope.detail = function (id) {
            CommonUtils.addTab({
                title: '批次详情',
                url: '/itsm/bug/bugBatch/bugs?id=' + id
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
            window.open(CommonUtils.contextPathURL('/itsm/bug/bugBatch/export?' + encodeURI(encodeURI($.param(o)))));
        };

    });
})(window, angular, jQuery);