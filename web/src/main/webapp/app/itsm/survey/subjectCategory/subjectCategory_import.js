// 题目分类数据导入
(function (window, angular, $) {
    var app = angular.module('itsm.survey.subjectCategory.import', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.survey.subjectCategory'
    ]);

    app.controller('Ctrl', function ($scope, ModalFactory, CommonUtils, AlertFactory, SubjectCategoryService) {
        $scope.beans = {};

        // 导入数据
        $scope.importData = function () {
            var ids = $scope.fileUpload.getAttachment() || [];
            if (ids && ids.length < 1) {
                AlertFactory.error(null, '请上传数据文件!');
                return false;
            }
            var promise = SubjectCategoryService.importData({attachmentIds: ids.join(',')}, function () {
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