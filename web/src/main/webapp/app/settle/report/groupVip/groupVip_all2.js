/**
 * 团队会员列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('settle.report.groupVip.all', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.report.groupVip'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, GroupVipService, GroupVipParam) {
        var month = parseInt($('#month').val());
        var year = parseInt($('#year').val());
        var defaults = {    // 默认查询条件
            orderBy: 'groupName',
            reverse: true,
            bonus: true,
            occurDate1: year + '-' + month + '-01',
            occurDate2: year + '-' + (month + 1) + '-01'
        };
        $scope.condition = angular.extend({}, defaults);

        // 重置查询条件并查询
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
            $scope.query();
        };


        // 参数：文交所
        $scope.companys = [{name: '全部'}];
        GroupVipParam.company(function (o) {
            $scope.companys.push.apply($scope.companys, o);
        });

        // 查询数据
        $scope.query = function () {
            $scope.pager.query();
        };
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({start: this.start, limit: this.limit}, $scope.condition);
                $scope.beans = [];
                $scope.items ? $scope.items.length = 0 : '';
                return CommonUtils.promise(function (defer) {
                    var promise = GroupVipService.pageQuery(param, function (data) {
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
                    var promise = GroupVipService.deleteByIds({ids: id}, function () {
                        AlertFactory.success('删除成功!');
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新数据',
                url: '/app/settle/report/groupVip/groupVip_edit_all.jsp?pageType=modify&id=' + id,
                onUpdate: $scope.query
            });
        };

        // 查看明细
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看明细',
                url: '/app/settle/report/groupVip/groupVip_edit_all.jsp?pageType=view&id=' + id
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
            window.open(CommonUtils.contextPathURL('/settle/report/groupVip/export-total?' + encodeURI(encodeURI($.param(o)))));
        };


        $scope.order = function (key) {
            if ($scope.condition.orderBy == key) {
                $scope.condition.reverse = !$scope.condition.reverse;
            } else {
                $scope.condition.orderBy = key;
                $scope.condition.reverse = false;
            }
            $scope.query();
        };

    });
})(window, angular, jQuery);