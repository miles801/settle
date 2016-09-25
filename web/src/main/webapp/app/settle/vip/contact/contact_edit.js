/**
 * 通讯录编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('settle.vip.contact.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.vip.contact'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, ContactService, ContactParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;


        // 参数：文交所
        $scope.companys = [{name: '请选择...'}];
        ContactParam.company(function (o) {
            $scope.companys.push.apply($scope.companys, o);
        });

        // 参数：返佣银行
        $scope.banks = [{name: '请选择...'}];
        ContactParam.bank(function (o) {
            $scope.banks.push.apply($scope.banks, o);
        });


        // 保存
        $scope.save = function (createNew) {
            var promise = ContactService.save($scope.beans, function (data) {
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
            var promise = ContactService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id, callback) {
            var promise = ContactService.get({id: id}, function (data) {
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
            $('span.add-on>.icons').remove();
        } else {
            AlertFactory.error($scope, '错误的页面类型');
        }
    });
})(window, angular, jQuery);