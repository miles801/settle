/**
 * 批次对比列表
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.batchCompare.detail', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bug',
        'itsm.bug.batchCompare'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BatchCompareService) {

        var id = $('#id').val();
        if (!id) {
            AlertFactory.error('错误的访问类型!未获取到ID');
            return;
        }
        var batchId2;
        BatchCompareService.get({id: id}, function (o) {
            batchId2 = (o.data || {}).batchId2;
        });
        var promise = BatchCompareService.compare({id: id}, function (data) {
            var total1 = 0;
            var total2 = 0;
            angular.forEach(data.data || [], function (o) {
                total1 += (o[1] || 0);
                total2 += (o[3] || 0);
            });
            $scope.beans = data.data || [];
            $scope.total1 = total1;
            $scope.total2 = total2;
        });
        CommonUtils.loading(promise);

        // 导出数据
        $scope.exportData = function () {
            if ($scope.beans.length == 0) {
                AlertFactory.error('未获取到可以导出的数据!请先查询出数据!');
                return;
            }
            window.open(CommonUtils.contextPathURL('/itsm/bug/batchCompare/export?id=' + id));
        };

        $scope.view = function (ip) {
            if (!batchId2) {
                AlertFactory.error('未获取到号，请刷新加载该页面后重试!');
                return;
            }
            CommonUtils.addTab({
                title: '查看批次',
                url: '/itsm/bug/bug/batch?batchId=' + batchId2 + '&ip=' + ip,
                onUpdate: $scope.query
            });
        };

    });
})(window, angular, jQuery);