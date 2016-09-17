// 会员数据导入
(function (window, angular, $) {
    var app = angular.module('settle.vip.vip.import', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.vip.vip'
    ]);

    app.controller('Ctrl', function ($scope, ModalFactory, CommonUtils, AlertFactory, VipService, VipParam) {
        $scope.beans = {};

        // 参数：所属文交所
        $scope.companys = [{name: '全部'}];
        VipParam.company(function (o) {
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
                AlertFactory.error('请选择本批次会员所属的文交所!');
                return;
            }
            var promise = VipService.importData({company: $scope.company, attachmentIds: ids.join(',')}, function () {
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