/**
 * 资产组列表
 * Created by Michael on 2016-08-09 17:06:00.
 */
(function (window, angular, $) {
    var app = angular.module('asset.assetGroup.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'asset.assetGroup'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, AssetGroupService, AssetParam) {
        $scope.condition = {};

        // 分类
        $scope.types = [{name: '全部...'}];
        AssetParam.type(function (o) {
            $scope.types.push.apply($scope.types, o);
        });

        // 资产价值
        $scope.values = [{name: '全部...'}];
        AssetParam.value(function (o) {
            $scope.values.push.apply($scope.values, o);
        });

        // 资产来源
        $scope.sources = [{name: '全部...'}];
        AssetParam.source(function (o) {
            $scope.sources.push.apply($scope.sources, o);
        });

        //查询数据
        $scope.query = function () {
            $scope.pager.query();
        };

        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {start: this.start, limit: this.limit}, $scope.condition);
                $scope.beans = [];
                return CommonUtils.promise(function (defer) {
                    var promise = AssetGroupService.pageQuery(param, function (data) {
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
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-danger">数据一旦删除将不可恢复，请确认!</span>',
                callback: function () {
                    var promise = AssetGroupService.deleteByIds({ids: id}, function () {
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
                title: '新增资产组',
                url: '/itsm/assetGroup/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新资产组',
                url: '/itsm/assetGroup/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看资产组',
                url: '/itsm/assetGroup/detail?id=' + id
            });
        };

        // 导入
        $scope.importData = function () {
            CommonUtils.addTab({
                title: '导入资产组',
                url: '/app/itsm/asset/assetGroup/list/assetGroup_import.jsp',
                onUpdate: $scope.query
            });
        };

        // 启用
        $scope.enable = function (id) {
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-success">是否启用，请确认!</span>',
                callback: function () {
                    var promise = AssetGroupService.enable({ids: id}, function () {
                        AlertFactory.success('操作成功!');
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };

        // 禁用
        $scope.disable = function (id) {
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-danger">是否禁用，请确认!</span>',
                callback: function () {
                    var promise = AssetGroupService.disable({ids: id}, function () {
                        AlertFactory.success('操作成功!');
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };


        // 克隆
        $scope.cloneAssetGroup = function (id) {
            CommonUtils.addTab({
                title: '资产组克隆',
                url: '/itsm/assetGroup/clone?id=' + id,
                onUpdate: $scope.query
            });
        };
    });

})(window, angular, jQuery);