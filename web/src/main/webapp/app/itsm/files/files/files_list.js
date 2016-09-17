/**
 * 文件管理列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.files.files.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.files.filesRecord',   // 阅读记录
        'itsm.files.filesCallback',   // 意见反馈
        'itsm.files.files'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, FilesService, FilesParam,
                                     FilesRecordModal, FilesCallbackModal) {
        // 默认查询条件
        var defaults = {
            orderBy: 'level',
            reverse: true
        };

        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };

        // 重排序
        $scope.reverse = function (reverse) {
            // 防止重复
            if ($scope.condition.reverse == reverse) {
                return;
            }
            $scope.condition.reverse = reverse;
            $scope.query();
        };

        // 参数：管理框架
        $scope.scope1s = [{name: '全部'}];
        FilesParam.scope1(function (o) {
            $scope.scope1s.push.apply($scope.scope1s, o);
        });

        // 参数：管理域
        $scope.scope2s = [{name: '全部'}];
        FilesParam.scope2(function (o) {
            $scope.scope2s.push.apply($scope.scope2s, o);
        });

        // 参数：级别
        $scope.levels = [{name: '全部'}];
        FilesParam.level(function (o) {
            $scope.levels.push.apply($scope.levels, o);
        });

        // 参数：文件类型
        $scope.types = [{name: '全部'}];
        FilesParam.type(function (o) {
            $scope.types.push.apply($scope.types, o);
        });

        // 参数：密级
        $scope.secrets = [{name: '全部'}];
        FilesParam.secret(function (o) {
            $scope.secrets.push.apply($scope.secrets, o);
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
                    var promise = FilesService.pageQuery(param, function (data) {
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
                    var promise = FilesService.deleteByIds({ids: id}, function () {
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
                title: '新增文件管理',
                url: '/itsm/files/files/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新文件管理',
                url: '/itsm/files/files/modify?id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看文件管理',
                url: '/itsm/files/files/detail?id=' + id
            });
        };

        // 启用
        $scope.enable = function (id) {
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-success">是否启用，请确认!</span>',
                callback: function () {
                    var promise = FilesService.enable({ids: id}, function () {
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
                    var promise = FilesService.disable({ids: id}, function () {
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
            window.open(CommonUtils.contextPathURL('/itsm/files/files/export?' + encodeURI(encodeURI($.param(o)))));
        };

        // 文件的阅读记录
        $scope.viewReadHistory = function (fileId) {
            FilesRecordModal.readHistory(fileId);
        };

        // 意见反馈
        $scope.viewCallback = function (id) {
            FilesCallbackModal.view({filesId: id});
        };

    });
})(window, angular, jQuery);