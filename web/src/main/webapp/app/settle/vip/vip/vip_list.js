/**
 * 会员列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('settle.vip.vip.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.vip.vip'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, VipService, VipParam) {
        var defaults = {// 默认查询条件
            orderBy: 'occurDate',
            reverse: true
        };

        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };


        // 参数：签约状态
        $scope.assignStatuss = [{name: '全部'}];
        VipParam.assignStatus(function (o) {
            $scope.assignStatuss.push.apply($scope.assignStatuss, o);
        });

        // 参数：状态
        $scope.statuss = [{name: '全部'}];
        VipParam.status(function (o) {
            $scope.statuss.push.apply($scope.statuss, o);
        });

        // 参数：所属文交所
        $scope.companys = [{name: '全部'}];
        VipParam.company(function (o) {
            $scope.companys.push.apply($scope.companys, o);
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
                    var promise = VipService.pageQuery(param, function (data) {
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
                    var promise = VipService.deleteByIds({ids: id}, function () {
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
                title: '新增会员',
                url: '/settle/vip/vip/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新会员',
                url: '/settle/vip/vip/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看会员',
                url: '/settle/vip/vip/detail?id=' + id
            });
        };

        // 查看团队
        $scope.viewGroup = function (code) {
            CommonUtils.addTab({
                title: '查看团队-' + code,
                url: '/settle/vip/group/detail?code=' + code
            });
        };
        // 导入会员
        $scope.importData = function () {
            CommonUtils.addTab({
                title: '导入会员',
                url: '/settle/vip/vip/import',
                onUpdate: $scope.query
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
            window.open(CommonUtils.contextPathURL('/settle/vip/vip/export?' + encodeURI(encodeURI($.param(o)))));
        };

    });
})(window, angular, jQuery);