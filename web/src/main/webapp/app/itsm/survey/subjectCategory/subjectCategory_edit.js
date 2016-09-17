/**
 * 题目分类编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.survey.subjectCategory.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'itsm.survey.subjectCategory'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, SubjectCategoryService, SubjectCategoryParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;


        $scope.parentTree = SubjectCategoryParam.validTree(function (o) {
            $scope.beans.parentId = o.id;
            $scope.beans.parentName = o.name;
        });

        $scope.clearParent = function () {
            $scope.beans.parentId = null;
            $scope.beans.parentName = null;
        };
        // 保存
        $scope.save = function (createNew) {
            var promise = SubjectCategoryService.save($scope.beans, function (data) {
                AlertFactory.success('保存成功!');
                CommonUtils.addTab('update');
                if (createNew === true) {
                    $scope.beans.name = null;
                    $scope.beans.sequenceNo = ($scope.beans.sequenceNo || 0) + 1;
                } else {
                    $scope.form.$setValidity('committed', false);
                    CommonUtils.delay($scope.back, 2000);
                }
            });
            CommonUtils.loading(promise);
        };


        // 更新
        $scope.update = function () {
            var promise = SubjectCategoryService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id) {
            var promise = SubjectCategoryService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        if (pageType == 'add') {
            $scope.beans = {
                sequenceNo: 1
            };
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