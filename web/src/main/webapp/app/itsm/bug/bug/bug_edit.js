/**
 * 漏洞编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bug.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bug'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugService, BugParam) {

        $scope.translate = true;
        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;


        // 参数：风险等级
        $scope.riskLevels = [{name: '请选择...'}];
        BugParam.riskLevel(function (o) {
            $scope.riskLevels.push.apply($scope.riskLevels, o);
        });


        // 保存
        $scope.save = function (createNew) {
            var promise = BugService.save($scope.beans, function (data) {
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

        $scope.addBugLib = function () {
            CommonUtils.addTab({
                title: '补充漏洞库',
                url: '/itsm/bug/bugLib/add?bugId=' + id,
                onUpdate: window.location.reload
            });
        };

        // 更新
        $scope.update = function () {
            var promise = BugService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id, callback) {
            var promise = BugService.get({id: id}, function (data) {
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
            $scope.load(id, function () {
                $('#certify').html($scope.beans.certify);
            });
            $('input,textarea,select').attr('disabled', 'disabled');
            $('span.add-on>.icons').remove();
        } else {
            AlertFactory.error($scope, '错误的页面类型');
        }
    });
})(window, angular, jQuery);