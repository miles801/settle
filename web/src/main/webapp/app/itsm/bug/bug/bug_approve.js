/**
 * 漏洞列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bug.repair', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bug'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugService, BugParam, BugModal) {
        var defaults = {    // 默认查询条件
            masterId: CommonUtils.loginContext().id,
            statusInclude: ['STOP', 'REPAIRED', 'APPROVING']
        };

        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };

        // 参数：风险等级
        $scope.riskLevels = [{name: '全部'}];
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

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看漏洞',
                url: '/itsm/bug/bug/detail?id=' + id
            });
        };

        // 审核
        $scope.approve = function (id, view) {
            BugModal.approve(id, view, $scope.query);
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
            window.open(CommonUtils.contextPathURL('/itsm/bug/bug/export?' + encodeURI(encodeURI($.param(o)))));
        };

    });
})(window, angular, jQuery);