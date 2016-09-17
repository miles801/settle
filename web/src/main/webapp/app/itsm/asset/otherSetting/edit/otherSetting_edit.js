/**
 * 风评库设置编辑
 */
(function (window, angular, $) {
    var app = angular.module('itsm.otherSetting.edit', [
        'itsm.otherSetting',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.controller('Ctrl', function ($scope, $q, CommonUtils, AlertFactory, ModalFactory, OtherSettingService, OtherSettingParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        // 回显checkbox
        var typeDefer = CommonUtils.defer();
        var loadDefer = CommonUtils.defer();
        $q.all([typeDefer.promise, loadDefer.promise]).then(function () {
            var type = $scope.beans.assetType;
            if (!type) {
                AlertFactory.error('数据错误!获取“受影响的资产类型”失败!');
                return;
            }
            angular.forEach($scope.assetTypes || [], function (o) {
                o.checked = type.indexOf(o.id) !== -1;
            });
        });
        // 设置类型
        $scope.types = [{name: '请选择'}];
        OtherSettingParam.type(function (o) {
            $scope.types.push.apply($scope.types, o);
            typeDefer.resolve(o);
        });

        // 资产分类
        OtherSettingParam.assetType1(function (o) {
            $scope.assetTypes = o || [];
        });


        $scope.back = CommonUtils.back;

        $scope.validate = function () {
            // 检查是否设置了资产类型
            var ids = [];
            var names = [];
            angular.forEach($scope.assetTypes || [], function (o) {
                if (o.checked) {
                    ids.push(o.id);
                    names.push(o.name);
                }
            });
            $scope.beans.assetType = ids.join(',');
            $scope.beans.assetTypeName = names.join(',');
        };
        // 保存
        $scope.save = function (createNew) {
            var promise = OtherSettingService.save($scope.beans, function (data) {
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
            var promise = OtherSettingService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id) {
            var promise = OtherSettingService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
                loadDefer.resolve($scope.beans);
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        if (pageType == 'add') {
            $scope.beans = {};
            loadDefer.reject();
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