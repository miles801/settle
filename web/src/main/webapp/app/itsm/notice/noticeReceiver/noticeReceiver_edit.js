/**
 * 公告接收者编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.notice.noticeReceiver.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.notice.noticeReceiver'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, NoticeReceiverService, NoticeReceiverParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;


        // 附件上传
        $scope.uploadOptions = {
            showLabel: false,
            bid: id,
            btype: 'itsm.notice.NoticeReceiver',
            readonly: pageType == 'detail'
        };
        // 保存
        $scope.save = function (createNew) {
            $scope.beans.attachmentIds = $scope.uploadOptions.getAttachment().join(',');
            var promise = NoticeReceiverService.save($scope.beans, function (data) {
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
            var promise = NoticeReceiverService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id, callback) {
            var promise = NoticeReceiverService.get({id: id}, function (data) {
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