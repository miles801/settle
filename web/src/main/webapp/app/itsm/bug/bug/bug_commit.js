/**
 * 漏洞编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bug.commit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'base.emp',
        'itsm.bug.bug'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugService, BugParam, EmpModal) {

        var id = $('#id').val();

        $scope.back = CommonUtils.back;

        $scope.beans = {id: id};

        // 参数：风险等级
        $scope.priority = [{name: '请选择...'}];
        BugParam.priority(function (o) {
            $scope.priority.push.apply($scope.priority, o);
        });

        // 选择整改人
        $scope.pickMaster = function () {
            EmpModal.pick({}, function (o) {
                $scope.beans.masterId = o.id;
                $scope.beans.masterName = o.name;
            });
        };

        $scope.clearMaster = function () {
            $scope.beans.masterId = null;
            $scope.beans.masterName = null;
        };
        // 保存
        $scope.save = function () {
            var promise = BugService.commit($scope.beans, function (data) {
                AlertFactory.success('提交成功!');
                CommonUtils.addTab('update');
                $scope.form.$setValidity('committed', false);
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise);
        };
    });
})(window, angular, jQuery);