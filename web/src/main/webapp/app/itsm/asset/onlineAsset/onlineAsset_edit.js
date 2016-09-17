/**
 * 在线资产编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.asset.onlineAsset.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.asset.onlineAsset'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, OnlineAssetService, OnlineAssetParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;


        // 保存
        $scope.save = function (createNew) {
            var promise = OnlineAssetService.save($scope.beans, function (data) {
                AlertFactory.success('保存成功!');
                CommonUtils.addTab('update');
                if (createNew === true) {
                    $scope.beans = {};
                } else {
                    $scope.form.$setValidity('committed', false);
                    CommonUtils.delay($scope.back, 2000);
                }
            });
            CommonUtils.loading(promise);
        };


        // 更新
        $scope.update = function () {
            var promise = OnlineAssetService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id) {
            var promise = OnlineAssetService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
            });
            CommonUtils.loading(promise);
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