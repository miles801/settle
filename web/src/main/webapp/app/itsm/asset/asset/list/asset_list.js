/**
 * 资产列表
 * Created by Michael on 2016-08-11 13:26:25.
 */
(function (window, angular, $) {
    var app = angular.module('itsm.asset.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'asset.assetGroup', // 资产组
        'base.emp',  // 员工
        'base.org',
        'itsm.asset'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, AssetService, AssetParam, OrgTree, EmpModal) {
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
        $scope.risk = [
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
        $scope.riskChange = function () {
            var values = [];
            angular.forEach($scope.risk, function (o) {
                if (o.checked) {
                    values.push(o.name);
                }
            });
            $scope.condition.techRiskValues = values;
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
                    var promise = AssetService.pageQuery(param, function (data) {
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
        $scope.type = [{name: '全部...'}];
        AssetParam.type(function (o) {
            $scope.type.push.apply($scope.type, o);
        });

        $scope.pickEmp = function () {
            EmpModal.pick({}, function (o) {
                $scope.condition.masterId = o.id;
                $scope.masterName = o.name;
                $scope.query();
            });
        };

        $scope.clear = function () {
            $scope.condition.masterId = null;
            $scope.masterName = null;
        };

        // 删除或批量删除
        $scope.remove = function (id) {
            if (!id) {
                var ids = [];
                angular.forEach($scope.items, function (o) {
                    ids.push(o.id);
                });
                if (ids.length == 0) {
                    AlertFactory.error('请选择需要删除的资产!');
                    return;
                }
                id = ids.join(',');
            }
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
        $scope.setManageRisk = function (id, assetType) {
            CommonUtils.addTab({
                title: '管理风险',
                url: '/itsm/asset/riskManage?assetId=' + id,
                onUpdate: $scope.query
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

        // 克隆
        $scope.cloneAsset = function () {
            var id = $scope.items[0].id;
            if (!id) {
                AlertFactory.error('请选择需要克隆的对象!');
                return;
            }
            CommonUtils.addTab({
                title: '资产克隆',
                url: '/itsm/asset/clone?id=' + id,
                onUpdate: $scope.query
            });
        };
    });

    app.filter('max', function () {
        return function (value1, value2) {
            return parseInt(value1) > parseInt(value2) ? value1 : value2;
        }
    });

})(window, angular, jQuery);