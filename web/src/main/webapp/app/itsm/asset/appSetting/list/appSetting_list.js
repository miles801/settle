/**
 * 业务应用设置列表
 * Created by Michael on 2016-08-09 23:40:05.
 */
(function (window, angular, $) {
    var app = angular.module('itsm.appSetting.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.appSetting'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, AppSettingService, AppSettingParam) {
        $scope.condition = {};

        // 重要性
        $scope.importants = [{name: '全部'}];
        AppSettingParam.important(function (o) {
            $scope.importants.push.apply($scope.importants, o);
        });
        // 级别
        $scope.levels = [{name: '全部'}];
        AppSettingParam.level(function (o) {
            $scope.levels.push.apply($scope.levels, o);
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
                    var promise = AppSettingService.pageQuery(param, function (data) {
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
                angular.forEach($scope.items, function (o) {
                    ids.push(o.id);
                });
                id = ids.join(',');
            }
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-danger">数据一旦删除将不可恢复，请确认!</span>',
                callback: function () {
                    var promise = AppSettingService.deleteByIds({ids: id}, function () {
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
                title: '新增业务应用设置',
                url: '/itsm/appSetting/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新业务应用设置',
                url: '/itsm/appSetting/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看业务应用设置',
                url: '/itsm/appSetting/detail?id=' + id
            });
        }
    });
})(window, angular, jQuery);