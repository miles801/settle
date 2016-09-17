/**
 * 批次对比编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.batchCompare.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bugBatch',    // 批次
        'itsm.bug.batchCompare'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BatchCompareService, BugBatchModal) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;

        var beans = $scope.beans = {};
        // 选择批次号
        $scope.pick = function (index) {
            BugBatchModal.pick({}, function (o) {
                beans['batchId' + index] = o.code;
            });
        };
        // 清除选择的批次号
        $scope.clear = function (index) {
            beans['batchId' + index] = null;
        };

        // 保存
        $scope.save = function () {
            if ($scope.beans.batchId1 == $scope.beans.batchId2) {
                AlertFactory.error("批次号不能相同,请重新选择!");
                return;
            }
            var promise = BatchCompareService.save($scope.beans, function (data) {
                AlertFactory.success('保存成功!');
                CommonUtils.addTab('update');
                $scope.form.$setValidity('committed', false);
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise);
        };


        // 加载数据
        $scope.load = function (id) {
            var promise = BatchCompareService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        if (pageType == 'add') {
        } else if (pageType == 'detail') {
            $scope.load(id);
            $('input,textarea,select').attr('disabled', 'disabled');
        } else {
            AlertFactory.error($scope, '错误的页面类型');
        }
    });
})(window, angular, jQuery);