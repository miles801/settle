/**
 * 团队编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('settle.vip.group.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.vip.group'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, GroupService, GroupParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();
        var code = $('#code').val();

        $scope.back = CommonUtils.back;


        // 参数：文交所
        $scope.companys = [{name: '请选择...'}];
        GroupParam.company(function (o) {
            $scope.companys.push.apply($scope.companys, o);
        });


        // 保存
        $scope.save = function (createNew) {
            var promise = GroupService.save($scope.beans, function (data) {
                AlertFactory.success('保存成功!');
                CommonUtils.addTab('update');
                if (createNew === true) {
                    $scope.beans.name = null;
                    $scope.beans.code = null;
                    $scope.beans.description = null;
                } else {
                    $scope.form.$setValidity('committed', false);
                    CommonUtils.delay($scope.back, 2000);
                }
            });
            CommonUtils.loading(promise);
        };


        // 更新
        $scope.update = function () {
            var promise = GroupService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id, callback) {
            var promise = GroupService.get({id: id, code: code}, function (data) {
                if (!data.data) {
                    AlertFactory.error('团队不存在!页面即将关闭!');
                    CommonUtils.delay(function () {
                        CommonUtils.back();
                    }, 1500);
                    return;
                }
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