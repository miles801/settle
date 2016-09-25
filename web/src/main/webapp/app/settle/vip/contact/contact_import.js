// 通讯录数据导入
(function (window, angular, $) {
    var app = angular.module('settle.vip.contact.import', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.vip.contact'
    ]);

    app.controller('Ctrl', function ($scope, ModalFactory, CommonUtils, AlertFactory, ContactService, ContactParam) {
        $scope.beans = {};

        // 参数：文交所
        $scope.companys = [{name: '全部'}];
        ContactParam.company(function (o) {
            $scope.companys.push.apply($scope.companys, o);
        });
        // 导入数据
        $scope.importData = function () {
            var ids = $scope.fileUpload.getAttachment() || [];
            if (ids && ids.length < 1) {
                AlertFactory.error(null, '请上传数据文件!');
                return false;
            }
            if (!$scope.company) {
                AlertFactory.error('请选择本批次通讯录中用户所属的文交所!');
                return;
            }
            var promise = ContactService.importData({
                attachmentIds: ids.join(','),
                company: $scope.company
            }, function () {
                AlertFactory.success('导入成功!页面即将刷新!');
                CommonUtils.delay(function () {
                    window.location.reload();
                }, 2000);
            });
            CommonUtils.loading(promise, '导入中,请稍后...');
        };

        // 关闭当前页签
        $scope.back = CommonUtils.back;


        // 附件配置
        $scope.fileUpload = {
            maxFile: 3,
            canDownload: true,
            onSuccess: function () {
                $scope.$apply(function () {
                    $scope.canImport = true;
                });
            },
            swfOption: {
                fileTypeExts: '*.xls;*.xlsx'
            }
        };

    });
})(window, angular, jQuery);