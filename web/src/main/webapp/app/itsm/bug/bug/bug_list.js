/**
 * 漏洞列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bug.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bug'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugService, BugParam) {
        var defaults = {}; // 默认查询条件

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

        // 参数：协议类型
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

        // 新增
        $scope.add = function () {
            CommonUtils.addTab({
                title: '新增漏洞',
                url: '/itsm/bug/bug/add',
                onUpdate: $scope.query
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

        // 提交整改
        $scope.commit = function () {
            var ids = [];
            angular.forEach($scope.items || [], function (o) {
                if (o.status == 'NOT_START') {
                    ids.push(o.id);
                }
            });
            if (ids.length == 0) {
                AlertFactory.error('未发现符合要求的选项，只有“未修复”的漏洞才允许进行整改操作，请重新选择!');
                return;
            }
            ModalFactory.confirm({
                scope: $scope,
                content: '共选择了[' + $scope.items.length + ']个漏洞，符合条件的一共有[' + ids.length + ']个，是否跳转到整改页面进行处理?',
                callback: function () {
                    CommonUtils.addTab({
                        title: '漏洞整改',
                        url: '/itsm/bug/bug/commit?id=' + ids.join(','),
                        onUpdate: $scope.query
                    });
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
            window.open(CommonUtils.contextPathURL('/itsm/bug/bug/export?' + encodeURI(encodeURI($.param(o)))));
        };

        // 移除其他的tab，然后创建新的
        // 关闭其他页签
        $scope.closeTab = function () {
            $(window.parent.document.body).find('ul.nav-tabs>li:gt(0)').remove();
            $(window.parent.document.body).find('.tab-content iframe:gt(0)').remove();
        };

        $scope.closeTab();
        CommonUtils.addTab({
            title: '其他漏洞',
            url: '/itsm/bug/bugOther',
            active: false,
            canClose: false
        });

    });
})(window, angular, jQuery);