/**
 * 批次编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bugBatch.detail', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bug',
        'itsm.bug.bugBatch'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugBatchService, BugBatchParam,
                                     BugService, BugParam) {
        $scope.condition = {
            batchId: $('#code').val()
        };

        $scope.back = CommonUtils.back;

        // 风险等级
        BugParam.riskLevel(function (o) {
            $scope.risk = o;
        });
        $scope.checkChange = function () {
            var risks = [];
            angular.forEach($scope.risk, function (o) {
                if (o.checked) {
                    risks.push(o.value);
                }
            });
            $scope.condition.riskLevels = risks;
            $scope.query();
        };

        // 参数：扫描类型
        $scope.scanTypes = [{name: '请选择...'}];
        BugBatchParam.scanType(function (o) {
            $scope.scanTypes.push.apply($scope.scanTypes, o);
        });

        // 参数：扫描器
        $scope.scanMachines = [{name: '请选择...'}];
        BugBatchParam.scanMachine(function (o) {
            $scope.scanMachines.push.apply($scope.scanMachines, o);
        });

        // 清除数据
        $scope.clearOrgName = function () {
            $scope.beans.orgId = null;
            $scope.beans.orgName = null;
        };

        // 查询数据
        $scope.query = function () {
            $scope.beans = [];
            var promise = BugBatchService.bugs($scope.condition, function (data) {
                $scope.beans = data.data || [];
            });
            CommonUtils.loading(promise, 'Loading...');
        };

        // 导出数据
        $scope.exportData = function () {
            if ($scope.beans.length < 1) {
                AlertFactory.error('未获取到可以导出的数据!请先查询出数据!');
                return;
            }
            var o = angular.extend({}, $scope.condition);
            window.open(CommonUtils.contextPathURL('/itsm/bug/bugBatch/bugs/export?' + encodeURI(encodeURI($.param(o)))));
        };

        // 浏览指定批次指定IP相关的数据
        $scope.view = function (batchId, ip) {
            CommonUtils.addTab({
                title: '查看批次',
                url: '/itsm/bug/bug/batch?batchId=' + batchId + '&ip=' + ip,
                onUpdate: $scope.query
            });
        };

        // 删除批次中指定IP相关的数据
        $scope.remove = function (batchId, ip) {
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="text-danger">数据一旦删除将不可恢复，请确认!</span>',
                callback: function () {
                    var promise = BugBatchService.deleteByIp({code: batchId, ip: ip}, function () {
                        AlertFactory.success('删除成功!');
                        $scope.query();
                    });
                    CommonUtils.loading((promise));
                }
            });
        };
        $scope.query();
    });

})(window, angular, jQuery);