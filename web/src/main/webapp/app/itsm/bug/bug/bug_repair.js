/**
 * 漏洞列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bug.repair', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'base.emp',
        'itsm.bug.bug'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugService, BugParam, EmpModal) {
        $scope.now = new Date().getTime();
        var defaults = {    // 默认查询条件
            masterId: CommonUtils.loginContext().id
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
        // 参数：风险等级
        $scope.status = [{name: '全部'}];
        BugParam.status(function (o) {
            $scope.status.push.apply($scope.status, o);
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
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看漏洞',
                url: '/itsm/bug/bug/detail?id=' + id
            });
        };

        // 提交整改
        $scope.repair = function (id) {
            CommonUtils.addTab({
                title: '漏洞整改',
                url: 'app/itsm/bug/bug/bug_repair_edit.jsp?id=' + id,
                onUpdate: $scope.query
            });
        };


        // 转派
        $scope.transport = function (beans) {
            console.dir(beans);
            EmpModal.pick({}, function (o) {
                if (beans.masterId == o.id) {
                    AlertFactory.error('不能转派给自己，请重新操作!');
                    return;
                }
                ModalFactory.confirm({
                    scope: $scope,
                    content: '是否确定要将该楼栋转派给[' + o.name + ']处理，请确认!',
                    callback: function () {
                        var oo = angular.extend({}, beans);
                        oo.masterId = o.id;
                        oo.masterName = o.name;
                        var promise = BugService.update(oo, function () {
                            AlertFactory.success('转派成功!');
                            $scope.query();
                        });
                        CommonUtils.loading(promise);
                    }
                });

            });
            ModalFactory.confirm();
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