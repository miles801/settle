/**
 * 资产管理风险列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.asset.riskManage.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.asset.riskManage',
        'itsm.asset',   // 资产
        'itsm.otherSetting',   // 威胁、弱点、风险描述
        'asset.assetGroup'  // 资产组
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, RiskManageService, AssetParam) {
        var assetId = $("#assetId").val();
        if (!assetId) {
            AlertFactory.error('错误的访问!没有获取到资产ID!');
            return;
        }

        var defaults = {
            assetId: assetId
        };
        $scope.condition = angular.extend({}, defaults);

        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };

        // 策略
        $scope.handlePolicys = [{name: '全部...'}];
        AssetParam.policy(function (o) {
            $scope.handlePolicys.push.apply($scope.handlePolicys, o);
        });

        // 等级
        $scope.values = [{name: '全部...'}];
        AssetParam.value(function (o) {
            $scope.values.push.apply($scope.values, o);
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
                    var promise = RiskManageService.pageQuery(param, function (data) {
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
                    var promise = RiskManageService.deleteByIds({ids: id}, function () {
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
                title: '新增资产管理风险',
                url: '/itsm/asset/riskManage/add?assetId=' + assetId,
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新资产管理风险',
                url: '/itsm/asset/riskManage/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看资产管理风险',
                url: '/itsm/asset/riskManage/detail?id=' + id
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
            window.open(CommonUtils.contextPathURL('/itsm/asset/riskManage/export?' + encodeURI(encodeURI($.param(o)))));
        };

    });
})(window, angular, jQuery);