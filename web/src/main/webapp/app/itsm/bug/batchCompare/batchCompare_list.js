/**
 * 批次对比列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.batchCompare.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.batchCompare'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BatchCompareService, BatchCompareParam) {
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
                    var promise = BatchCompareService.pageQuery(param, function (data) {
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
                    var promise = BatchCompareService.deleteByIds({ids: id}, function () {
                        AlertFactory.success('删除成功!');
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };

        $scope.update = function (id) {
            ModalFactory.confirm({
                scope: $scope,
                content: '将用漏洞比对结果批量更新漏洞总表中漏洞信息，请确认',
                callback: function () {
                    var promise = BatchCompareService.execute({id: id}, function () {
                        AlertFactory.success('操作成功!');
                        $scope.query();
                    });
                    CommonUtils.loading(promise);
                }
            });
        };
        // 新增
        $scope.add = function () {
            CommonUtils.addTab({
                title: '新增批次对比',
                url: '/itsm/bug/batchCompare/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新批次对比',
                url: '/itsm/bug/batchCompare/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '批次对比',
                url: '/app/itsm/bug/batchCompare/batchCompare_detail.jsp?id=' + id
            });
        };

        // 查看明细
        $scope.viewBatch = function (code) {
            CommonUtils.addTab({
                title: '批次详情-' + code,
                url: '/itsm/bug/bugBatch/detail2?code=' + code
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
            window.open(CommonUtils.contextPathURL('/itsm/bug/batchCompare/export?' + encodeURI(encodeURI($.param(o)))));
        };

    });
})(window, angular, jQuery);