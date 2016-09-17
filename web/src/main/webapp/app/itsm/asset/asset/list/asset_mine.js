/**
 * 资产列表
 * Created by Michael on 2016-08-11 13:26:25.
 */
(function (window, angular, $) {
    var app = angular.module('itsm.asset.mine', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'asset.assetGroup', // 资产组
        'base.org',
        'itsm.asset'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, AssetService, AssetParam, OrgTree) {
        $scope.condition = {
            deleted: false
        };

        //查询数据
        $scope.query = function () {
            $scope.pager.query();
        };

        $scope.reset = function () {
            $scope.condition = {};
            angular.forEach($scope.values, function (o) {
                o.checked = false;
            });
            $scope.query();
        };
        $scope.importData = function () {
            CommonUtils.addTab({
                title: '导入资产',
                url: 'app/itsm/asset/asset/list/asset_import.jsp',
                onClose: $scope.query
            });
        };

        // 部门
        $scope.orgTree = OrgTree.pick(function (o) {
            $scope.condition.orgId = o.id;
            $scope.condition.orgName = o.name;
            $scope.query();
        });
        $scope.clearOrg = function () {
            $scope.condition.orgId = null;
            $scope.condition.orgName = null;
            $scope.query();
        };
        $scope.values = [
            {name: 1},
            {name: 2},
            {name: 3},
            {name: 4},
            {name: 5}
        ];
        $scope.valueChange = function () {
            var values = [];
            angular.forEach($scope.values, function (o) {
                if (o.checked) {
                    values.push(o.name);
                }
            });
            $scope.condition.values = values;
            $scope.query();
        };
        // 资产组
        var assetGroupTree = AssetParam.groupTree(function (o) {
            $scope.condition.assetGroupId = o.id;
            $scope.condition.assetGroupName = o.name;
            $scope.query();
        });
        assetGroupTree.position = 'fixed';
        $scope.assetGroupTree = assetGroupTree;
        $scope.clearAssetGroup = function () {
            $scope.condition.assetGroupId = null;
            $scope.condition.assetGroupName = null;
            $scope.query();
        };

        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {start: this.start, limit: this.limit}, $scope.condition);
                $scope.beans = [];
                return CommonUtils.promise(function (defer) {
                    var promise = AssetService.mine(param, function (data) {
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

        // 资产分类树形
        var assetTypeTree = AssetParam.typeTree(function (o) {
            var condition = $scope.condition;
            $scope.assetTypeName = o.name;
            if (o.level == 2) {
                condition.assetType3 = o.id;
            } else if (o.level == 1) {
                condition.assetType2 = o.id;
            } else if (o.level == 0) {
                condition.assetType1 = o.id;
            }
            $scope.query();
        });
        assetTypeTree.position = 'fixed';
        $scope.assetTypeTree = assetTypeTree;

        $scope.clearAssetType = function () {
            var condition = $scope.condition;
            condition.assetType1 = null;
            condition.assetType2 = null;
            condition.assetType3 = null;
            $scope.assetTypeName = null;
            $scope.query();
        };


        // 删除或批量删除
        $scope.remove = function (id) {
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-danger">数据一旦删除将不可恢复，请确认!</span>',
                callback: function () {
                    var promise = AssetService.deleteByIds({ids: id}, function () {
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
                title: '新增资产',
                url: '/itsm/asset/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新资产',
                url: '/itsm/asset/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看资产',
                url: '/itsm/asset/detail?id=' + id
            });
        };

        // 管理风险
        $scope.setManageRisk = function (id) {
            CommonUtils.addTab({
                title: '管理风险',
                url: '/itsm/asset/riskManage?assetId=' + id,
                onUpdate: $scope.query
            });
        };
        // 技术风险
        $scope.setTechRisk = function (id) {
            CommonUtils.addTab({
                title: '技术风险',
                url: '/itsm/asset/riskTechnology?assetId=' + id,
                onUpdate: $scope.query
            });
        };

        // 克隆
        $scope.cloneAsset = function (id) {
            CommonUtils.addTab({
                title: '资产克隆',
                url: '/itsm/asset/clone?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 导出数据
        $scope.exportData = function () {
            alert('还未实现')
        };
    });
})(window, angular, jQuery);