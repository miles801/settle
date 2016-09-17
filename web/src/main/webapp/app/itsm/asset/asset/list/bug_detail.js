/**
 * 漏洞列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bug.detail', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bug'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugService, BugParam) {
        var defaults = {    // 默认查询条件
            ip: $('#ip').val()
        };
        $scope.translate = true;

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
                    var promise = BugService.pageQuery(param, function (data) {
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
                    var promise = BugService.deleteByIds({ids: id}, function () {
                        AlertFactory.success('删除成功!');
                        CommonUtils.addTab('update');
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新漏洞',
                url: '/itsm/bug/bug/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看漏洞',
                url: '/itsm/bug/bug/detail?id=' + id
            });
        };
    });
})(window, angular, jQuery);