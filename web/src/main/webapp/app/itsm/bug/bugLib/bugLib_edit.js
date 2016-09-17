/**
 * 中文漏洞库编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bugLib.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bug',
        'itsm.bug.bugLib'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugLibService, BugLibParam, BugService) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();
        var bugId = $('#bugId').val();
        $scope.beans = {};
        // 从原bug中获取
        if (bugId && pageType == 'add') {
            BugService.get({id: bugId}, function (o) {
                o = o.data || {};
                $scope.beans.code = o.code;
                $scope.beans.description = o.description;
                $scope.beans.os = o.operate;
                $scope.beans.level = o.riskLevel;
            });
        }
        $scope.back = CommonUtils.back;


        // 参数：风险等级
        $scope.levels = [{name: '请选择...'}];
        BugLibParam.level(function (o) {
            $scope.levels.push.apply($scope.levels, o);
        });


        // 保存
        $scope.save = function (createNew) {
            var promise = BugLibService.save($scope.beans, function (data) {
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
            var promise = BugLibService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id) {
            var promise = BugLibService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        if (pageType == 'add') {
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