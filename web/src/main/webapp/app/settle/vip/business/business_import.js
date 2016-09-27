// 交易数据导入
(function (window, angular, $) {
    var app = angular.module('settle.vip.business.import', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.vip.business'
    ]);

    app.controller('Ctrl', function ($scope, ModalFactory, CommonUtils, AlertFactory, BusinessService, BusinessParam) {
        $scope.beans = {};

        // 参数：文交所
        $scope.companys = [{name: '全部'}];
        BusinessParam.company(function (o) {
            $scope.companys.push.apply($scope.companys, o);
        });
        $scope.date = new Date().getTime();
        // 导入数据
        $scope.importData = function () {
            var ids = $scope.fileUpload.getAttachment() || [];
            if (ids && ids.length < 1) {
                AlertFactory.error(null, '请上传数据文件!');
                return false;
            }
            if (!$scope.company) {
                AlertFactory.error('请选择本批次交易所属的文交所!');
                return;
            }
            var now = null;
            if (typeof $scope.date === 'string') {
                now = new Date($scope.date + '-01');
            } else {
                now = new Date($scope.date);
            }
            var promise = BusinessService.importData({
                attachmentIds: ids.join(','),
                company: $scope.company,
                date: now.getTime()
            }, function () {
                AlertFactory.success('导入成功!页面即将刷新!');
                CommonUtils.delay(function () {
                    window.location.reload();
                }, 1500);
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
                fileType: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
                fileTypeExts: '*.xls;*.xlsx;'
            }
        };

    });
})(window, angular, jQuery);