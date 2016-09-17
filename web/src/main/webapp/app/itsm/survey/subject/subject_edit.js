/**
 * 题目编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.survey.subject.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'itsm.survey.subjectCategory',  // 分类
        'base.org',                     // 机构
        'itsm.survey.subject'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, SubjectService, SubjectParam,
                                     SubjectCategoryParam, OrgTree) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;


        // 参数：问题类型
        $scope.types = [{name: '请选择...'}];
        SubjectParam.type(function (o) {
            $scope.types.push.apply($scope.types, o);
        });

        var repeat = function (o, key, split) {
            if (o.getParentNode() != null) {
                return repeat(o.getParentNode(), key, split) + split + o[key];
            } else {
                return o[key];
            }
        };

        // 树：所属分类
        $scope.categoryTree = SubjectCategoryParam.validTree(function (o) {
            $scope.beans.categoryId = repeat(o, 'id', ',');
            $scope.beans.categoryName = repeat(o, 'name', ' - ');
        });
        // 清除数据
        $scope.clearCategory = function () {
            $scope.beans.categoryId = null;
            $scope.beans.categoryName = null;
        };


        // 树：部门名称
        $scope.orgTree = OrgTree.pick(function (o) {
            $scope.beans.orgId = o.id;
            $scope.beans.orgName = o.name;
        });

        // 清除数据
        $scope.clearOrg = function () {
            $scope.beans.orgId = null;
            $scope.beans.orgName = null;
        };

        // 保存
        $scope.save = function (createNew) {
            var promise = SubjectService.save($scope.beans, function (data) {
                AlertFactory.success('保存成功!');
                CommonUtils.addTab('update');
                if (createNew === true) {
                    $scope.beans.content = null;
                    $scope.beans.code = null;
                } else {
                    $scope.form.$setValidity('committed', false);
                    CommonUtils.delay($scope.back, 2000);
                }
            });
            CommonUtils.loading(promise);
        };


        // 更新
        $scope.update = function () {
            var promise = SubjectService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id) {
            var promise = SubjectService.get({id: id}, function (data) {
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