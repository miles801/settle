/**
 * 其他漏洞编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.bug.bugOther.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'itsm.bug.bugOther'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, BugOtherService, BugOtherParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;


        // 参数：漏洞分类
        $scope.types = [{name: '请选择...'}];
        BugOtherParam.type(function (o) {
            $scope.types.push.apply($scope.types, o);
        });

        // 参数：原风险等级
        $scope.level1s = [{name: '请选择...'}];
        BugOtherParam.riskLevel(function (o) {
            $scope.level1s.push.apply($scope.level1s, o);
        });

        // 参数：修复后风险等级
        $scope.level2s = [{name: '请选择...'}];
        BugOtherParam.riskLevel(function (o) {
            $scope.level2s.push.apply($scope.level2s, o);
        });

        var attachmentIds = [];
        // 初始化富文本编辑器
        var editor = KindEditor.create('#result', {
            uploadJson: CommonUtils.contextPathURL('/attachment/upload2?dataType=jsp'),
            afterUpload: function (url, obj) {
                $scope.$apply(function () {
                    attachmentIds.push(obj.id)
                });
            }
        });

        // 参数：状态
        $scope.statuss = [{name: '请选择...'}];
        BugOtherParam.status(function (o) {
            $scope.statuss.push.apply($scope.statuss, o);
        });


        // 附件上传
        $scope.uploadOptions = {
            showLabel: false,
            bid: id,
            btype: 'itsm.bug.BugOther',
            readonly: pageType == 'detail'
        };
        // 保存
        $scope.save = function (createNew) {
            // 获取富文本内容
            if (!editor.text()) {
                AlertFactory.error('请输入内容!');
                return;
            }
            $scope.beans.result = editor.html();
            $scope.beans.attachmentIds = $scope.uploadOptions.getAttachment().join(',');
            var promise = BugOtherService.save($scope.beans, function (data) {
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
            // 获取富文本内容
            if (!editor.text()) {
                AlertFactory.error('请输入内容!');
                return;
            }
            $scope.beans.result = editor.html();
            $scope.beans.attachmentIds = $scope.uploadOptions.getAttachment().join(',');
            var promise = BugOtherService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id, callback) {
            var promise = BugOtherService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
                editor.html($scope.beans.result);
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