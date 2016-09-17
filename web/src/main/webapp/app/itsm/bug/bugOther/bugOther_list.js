/**
 * 其他漏洞列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bugOther.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bugOther'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugOtherService, BugOtherParam) {
        var defaults = {}; // 默认查询条件

        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };


        // 参数：漏洞分类
        $scope.types = [{name: '全部'}];
        BugOtherParam.type(function (o) {
            $scope.types.push.apply($scope.types, o);
        });

        // 参数：修复后风险等级
        $scope.level2s = [{name: '全部'}];
        BugOtherParam.riskLevel(function (o) {
            $scope.level2s.push.apply($scope.level2s, o);
        });

        // 参数：状态
        $scope.statuss = [{name: '全部'}];
        BugOtherParam.status(function (o) {
            $scope.statuss.push.apply($scope.statuss, o);
        });

        // 查询数据
        $scope.query = function () {
            $scope.pager.query();
        };
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({start: this.start, limit: this.limit}, $scope.condition);
                if (param.createdDatetime) {
                    param.createdDatetime1 = moment(param.createdDatetime).format('YYYY-MM-DD');
                    param.createdDatetime2 = moment(param.createdDatetime).add(1, 'd').format('YYYY-MM-DD');
                    param.createdDatetime = null;
                }
                $scope.beans = [];
                return CommonUtils.promise(function (defer) {
                    var promise = BugOtherService.pageQuery(param, function (data) {
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
                    var promise = BugOtherService.deleteByIds({ids: id}, function () {
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
                title: '新增其他漏洞',
                url: '/itsm/bug/bugOther/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新其他漏洞',
                url: '/itsm/bug/bugOther/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看其他漏洞',
                url: '/itsm/bug/bugOther/detail?id=' + id
            });
        };


        // 导入数据
        $scope.importData = function (id) {
            CommonUtils.addTab({
                title: '导入其他漏洞',
                url: '/itsm/bug/bugOther/import',
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
            window.open(CommonUtils.contextPathURL('/itsm/bug/bugOther/export?' + encodeURI(encodeURI($.param(o)))));
        };

    });
})(window, angular, jQuery);