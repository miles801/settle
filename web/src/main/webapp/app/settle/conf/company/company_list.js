/**
 * 文交所列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('settle.conf.company.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.conf.company'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, CompanyService, CompanyModal) {
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
                    var promise = CompanyService.pageQuery(param, function (data) {
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
                    var promise = CompanyService.deleteByIds({ids: id}, function () {
                        AlertFactory.success('删除成功!');
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };

        // 新增
        $scope.add = function () {
            CompanyModal.add({}, $scope.query);
        };

        // 更新
        $scope.modify = function (id) {
            CompanyModal.modify({id: id}, $scope.query);
        };

        // 查看明细
        $scope.view = function (id) {
            CompanyModal.view({id: id});
        };

        // 启用
        $scope.enable = function (id) {
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-success">是否启用，请确认!</span>',
                callback: function () {
                    var promise = CompanyService.enable({ids: id}, function () {
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
                    var promise = CompanyService.disable({ids: id}, function () {
                        AlertFactory.success('操作成功!');
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
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
            window.open(CommonUtils.contextPathURL('/settle/conf/company/export?' + encodeURI(encodeURI($.param(o)))));
        };

    });
})(window, angular, jQuery);