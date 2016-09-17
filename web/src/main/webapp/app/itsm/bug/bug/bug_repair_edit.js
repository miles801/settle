/**
 * 漏洞编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bug.repair2', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bug'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugService, BugParam) {

        var id = $('#id').val();

        $scope.beans = {
            id: id
        };
        $scope.back = CommonUtils.back;

        // 参数：风险等级
        $scope.vendor = [{name: '请选择...'}];
        BugParam.vendor(function (o) {
            $scope.vendor.push.apply($scope.vendor, o);
        });
        // 参数：整改后等级
        $scope.riskLevel = [{name: '请选择...'}];
        BugParam.riskLevel(function (o) {
            $scope.riskLevel.push.apply($scope.riskLevel, o);
        });

        var attachmentIds = [];
        // 初始化富文本编辑器
        var editor = KindEditor.create('#certify', {
            allowImageRemote: false,    // 禁用网络图片
            uploadJson: CommonUtils.contextPathURL('/attachment/upload2?dataType=jsp'),
            afterUpload: function (url, obj) {
                $scope.$apply(function () {
                    attachmentIds.push(obj.id)
                });
            }
        });

        // 保存
        $scope.save = function () {
            // 获取富文本内容
            if (!editor.text()) {
                AlertFactory.error('请输入内容!');
                return;
            }
            $scope.beans.certify = editor.html();
            $scope.beans.attachmentIds = attachmentIds.join(',');
            var promise = BugService.repair($scope.beans, function (data) {
                AlertFactory.success('提交成功!');
                CommonUtils.addTab('update');
                $scope.form.$setValidity('committed', false);
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise);
        };
    });
})(window, angular, jQuery);