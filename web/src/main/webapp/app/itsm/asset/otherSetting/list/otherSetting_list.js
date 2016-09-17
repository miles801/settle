/**
 * 风评库设置列表
 * Created by Michael on 2016-08-10 12:41:41.
 */
(function (window, angular, $) {
    var app = angular.module('itsm.otherSetting.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.otherSetting'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, OtherSettingService, OtherSettingParam) {
        $scope.condition = {
            deleted: false
        };
        $scope.reset = function () {
            $scope.condition = {deleted: false};
        };

        //查询数据
        $scope.query = function () {
            $scope.pager.query();
        };

        $scope.types = [{name: '全部'}];
        OtherSettingParam.type(function (o) {
            $scope.types.push.apply($scope.types, o);
        });


        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {start: this.start, limit: this.limit}, $scope.condition);
                $scope.beans = [];
                return CommonUtils.promise(function (defer) {
                    var promise = OtherSettingService.pageQuery(param, function (data) {
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
                    var promise = OtherSettingService.deleteByIds({ids: id}, function () {
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
                title: '新增风评库设置',
                url: '/itsm/otherSetting/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新风评库设置',
                url: '/itsm/otherSetting/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看风评库设置',
                url: '/itsm/otherSetting/detail?id=' + id
            });
        };
        // 导入数据
        $scope.importData = function () {
            CommonUtils.addTab({
                title: '导入风评库',
                url: '/itsm/otherSetting/import',
                onUpdate: $scope.query
            });
        }
    });
})(window, angular, jQuery);