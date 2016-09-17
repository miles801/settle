/**
 * 公告编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.notice.notice.view', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.notice.notice'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, NoticeService, NoticeParam) {

        var id = $('#id').val();

        $scope.back = CommonUtils.back;


        // 参数：栏目
        $scope.categorys = [{name: '请选择...'}];
        NoticeParam.category(function (o) {
            $scope.categorys.push.apply($scope.categorys, o);
        });

        // 参数：重要性
        $scope.importants = [{name: '请选择...'}];
        NoticeParam.important(function (o) {
            $scope.importants.push.apply($scope.importants, o);
        });


        // 附件上传
        $scope.uploadOptions = {
            showLabel: false,
            maxFile: 10,
            bid: id,
            btype: 'itsm.files.Files',
            readonly: true
        };

        // 加载数据
        $scope.load = function (id) {
            var promise = NoticeService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
                $('#content').html($scope.beans.content);
            });
            CommonUtils.loading(promise, 'Loading...');
        };

        $scope.load(id);
    });
})(window, angular, jQuery);