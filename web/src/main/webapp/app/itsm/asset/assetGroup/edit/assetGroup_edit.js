/**
 * 资产组编辑
 */
(function (window, angular, $) {
    var app = angular.module('asset.assetGroup.edit', [
        'asset.assetGroup',
        'eccrm.angular',
        'eccrm.angular.ztree',
        'eccrm.angularstrap'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, AssetGroupService, AssetParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;

        $scope.beans = {};

        // 分类
        $scope.types = [{name: '请选择...'}];
        AssetParam.type(function (o) {
            $scope.types.push.apply($scope.types, o);
        });
        // 资产价值
        $scope.values = [{name: '请选择...'}];
        AssetParam.value(function (o) {
            $scope.values.push.apply($scope.values, o);
        });
        // 资产来源
        $scope.sources = [{name: '请选择...'}];
        AssetParam.source(function (o) {
            $scope.sources.push.apply($scope.sources, o);
        });

        // 保存
        $scope.save = function (createNew) {
            var promise = AssetGroupService.save($scope.beans, function (data) {
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
            var promise = AssetGroupService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id, callback) {
            var promise = AssetGroupService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
                callback && callback();
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
        } else if (pageType == 'clone') {
            $scope.load(id, function () {
                $scope.beans.id = null;
                $scope.beans.name = null;
                $scope.beans.code = null;
                $scope.beans.creatorId = null;
                $scope.beans.creatorName = null;
                $scope.beans.createdDatetime = null;
                $scope.beans.modifierId = null;
                $scope.beans.modifierName = null;
                $scope.beans.modifiedDatetime = null;
            });
        } else {
            AlertFactory.error($scope, '错误的页面类型');
        }

    });
})(window, angular, jQuery);