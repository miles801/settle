/**
 * 批次编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bugBatch.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'base.org', // 组织机构
        'itsm.bug.bugBatch'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugBatchService, BugBatchParam, OrgTree) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;


        // 参数：扫描类型
        $scope.scanTypes = [{name: '请选择...'}];
        BugBatchParam.scanType(function (o) {
            $scope.scanTypes.push.apply($scope.scanTypes, o);
        });

        // 参数：扫描器
        $scope.scanMachines = [{name: '请选择...'}];
        BugBatchParam.scanMachine(function (o) {
            $scope.scanMachines.push.apply($scope.scanMachines, o);
        });


        // 树：部门名称
        $scope.orgNameTree = OrgTree.pick(function (o) {
            $scope.beans.orgId = o.id;
            $scope.beans.orgName = o.name;
        });
        // 清除数据
        $scope.clearOrgName = function () {
            $scope.beans.orgId = null;
            $scope.beans.orgName = null;
        };

        // 附件上传
        $scope.uploadOptions = {
            showLabel: false,
            bid: id,
            readonly: pageType == 'detail',
            btype: 'itsm.bug.BugBatch'
        };
        // 保存
        $scope.save = function (createNew) {
            var attachment = $scope.uploadOptions.getAttachment();
            if (attachment.length < 1) {
                AlertFactory.error('请上传数据附件!');
                return;
            }
            $scope.beans.attachmentIds = attachment.join(',');
            var promise = BugBatchService.save($scope.beans, function (data) {
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
            $scope.beans.attachmentIds = $scope.uploadOptions.getAttachment().join(',');
            var promise = BugBatchService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id) {
            var promise = BugBatchService.get({id: id}, function (data) {
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