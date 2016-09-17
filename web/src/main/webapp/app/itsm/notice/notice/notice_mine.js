/**
 * 公告列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.notice.notice.mine', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.notice.notice'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, NoticeService, NoticeParam) {
        var defaults = {}; // 默认查询条件

        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };


        // 参数：栏目
        $scope.categorys = [{name: '全部'}];
        NoticeParam.category(function (o) {
            $scope.categorys.push.apply($scope.categorys, o);
        });

        // 参数：重要性
        $scope.importants = [{name: '全部'}];
        NoticeParam.important(function (o) {
            $scope.importants.push.apply($scope.importants, o);
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
                    var promise = NoticeService.mine(param, function (data) {
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
                title: '阅读公告',
                url: '/itsm/notice/notice/read?id=' + id
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
            window.open(CommonUtils.contextPathURL('/itsm/notice/notice/export?' + encodeURI(encodeURI($.param(o)))));
        };

    });
})(window, angular, jQuery);