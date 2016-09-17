/**
 * 在线资产列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.asset.onlineAsset.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bug',
        'itsm.asset.onlineAsset'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, OnlineAssetService, BugParam) {
        var defaults = {}; // 默认查询条件

        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };

// 参数：风险等级
        $scope.riskLevels = [{name: '全部...'}];
        BugParam.riskLevel(function (o) {
            $scope.riskLevels.push.apply($scope.riskLevels, o);
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
                    var promise = OnlineAssetService.pageQuery(param, function (data) {
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
                    var promise = OnlineAssetService.deleteByIds({ids: id}, function () {
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
                title: '新增在线资产',
                url: '/itsm/asset/onlineAsset/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新在线资产',
                url: '/itsm/asset/onlineAsset/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看在线资产',
                url: '/itsm/asset/onlineAsset/detail?id=' + id
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
            window.open(CommonUtils.contextPathURL('/itsm/asset/onlineAsset/export?' + encodeURI(encodeURI($.param(o)))));
        };

        // 导入数据
        $scope.importData = function () {
            CommonUtils.addTab({
                title: '导入在线资产',
                url: '/itsm/asset/onlineAsset/import',
                onClose: $scope.query
            });
        };

        // 技术风险
        $scope.setTechRisk = function (id, ip) {
            CommonUtils.addTab({
                title: '技术风险',
                url: '/app/itsm/asset/asset/list/bug_detail.jsp?assetId=' + id + '&ip=' + ip,
                onUpdate: $scope.query
            });
        };

    });
})(window, angular, jQuery);