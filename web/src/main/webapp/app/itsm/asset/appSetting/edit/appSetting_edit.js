/**
 * 业务应用设置编辑
 */
(function (window, angular, $) {
    var app = angular.module('itsm.appSetting.edit', [
        'itsm.appSetting',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, AppSettingService, AppSettingParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        // 重要性
        $scope.importants = [{name: '请选择...'}];
        AppSettingParam.important(function (o) {
            $scope.importants.push.apply($scope.importants, o);
        });
        // 级别
        $scope.levels = [{name: '请选择...'}];
        AppSettingParam.level(function (o) {
            $scope.levels.push.apply($scope.levels, o);
        });

        $scope.back = CommonUtils.back;

        // 保存
        $scope.save = function (createNew) {
            var promise = AppSettingService.save($scope.beans, function (data) {
                AlertFactory.success('保存成功!');
                CommonUtils.addTab('update');
                if (createNew === true) {
                    $scope.beans = {};
                } else {
                    $scope.form.$setValidity('committed', false);
                    CommonUtils.delay($scope.back, 2000);
                }
            });
            CommonUtils.loading(promise, '保存中...');
        };


        // 更新
        $scope.update = function () {
            var promise = AppSettingService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id) {
            var promise = AppSettingService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        if (pageType == 'add') {
            $scope.beans = {};
        } else if (pageType == 'modify') {
            $scope.load(id);
        } else if (pageType == 'detail') {
            $scope.load(id);
            $('input,textarea,select').attr('disabled', 'disabled');
        } else {
            AlertFactory.error($scope, '错误的页面类型');
        }

    });
})(window, angular, jQuery);