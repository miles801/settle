/**
 * 公告编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.notice.notice.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'base.org',
        'base.emp',
        'base.position',
        'itsm.notice.notice'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, NoticeService, NoticeParam,
                                     PositionModal, OrgTree, EmpModal) {

        var pageType = $('#pageType').val();
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


        // 初始化富文本编辑器
        var attachmentIds = [];
        var editor = KindEditor.create('#content', {
            uploadJson: CommonUtils.contextPathURL('/attachment/upload2?dataType=jsp'),
            afterUpload: function (url, obj) {
                $scope.$apply(function () {
                    attachmentIds.push(obj.id)
                });
            }
        });

        // 选择员工
        $scope.pickEmp = function () {
            EmpModal.pickMulti({}, function (o) {
                var ids = [];
                var names = [];
                angular.forEach(o || [], function (e) {
                    ids.push(e.id);
                    names.push(e.name);
                });
                $scope.receiver.empId = ids.join(',');
                $scope.receiver.empName = names.join(',');
            });
        };
        $scope.clearEmp = function () {
            $scope.receiver.empId = null;
            $scope.receiver.empName = null;
        };

        // 选择岗位
        $scope.pickPosition = function () {
            PositionModal.pickMulti({}, function (ps) {
                var ids = [];
                var names = [];
                angular.forEach(ps || [], function (o) {
                    ids.push(o.id);
                    names.push(o.name);
                });
                $scope.receiver.positionId = ids.join(',');
                $scope.receiver.positionName = names.join(',');
            });
        };
        $scope.clearPosition = function () {
            $scope.receiver.positionId = null;
            $scope.receiver.positionName = null;
        };

        // 岗位树
        $scope.orgTree = OrgTree.pick(function (o) {
            $scope.receiver.orgId = o.id;
            $scope.receiver.orgName = o.name;
        });
        /**
         * 清除机构信息
         */
        $scope.clearOrg = function () {
            $scope.receiver.orgId = null;
            $scope.receiver.orgName = null;
        };

        // 附件上传
        $scope.uploadOptions = {
            showLabel: false,
            maxFile: 10,
            bid: id,
            btype: 'itsm.files.Files',
            readonly: pageType == 'detail',
            swfOption: {
                fileSizeLimit: 10000,
                fileType: 'image/*,application/pdf,application/msword,,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,text/*',
                fileTypeExts: '*.doc;*.docx;*.xls;*.xlsx;*.pdf;*.png;*.jpg;*.gif;*.ppt;*.pptx;*.txt'
            }
        };

        $scope.getReceiver = function () {
            // 接收人
            var receiverArr = [];
            var receiver = $scope.receivers;
            if (receiver.all) {
                receiverArr.push({type: 'ALL'});
            } else {
                if (receiver.orgId) {
                    var orgIds = receiver.orgId.split(',');
                    var orgNames = receiver.orgName.split(',');
                    angular.forEach(orgIds, function (o, index) {
                        receiverArr.push({type: 'ORG', receiverId: o, receiverName: orgNames[index]});
                    });
                }
                if (receiver.positionId) {
                    var positionIds = receiver.positionId.split(',');
                    var positionNames = receiver.positionName.split(',');
                    angular.forEach(positionIds, function (o, index) {
                        receiverArr.push({type: 'POSITION', receiverId: o, receiverName: positionNames[index]});
                    });
                }
                if (receiver.empId) {
                    var empIds = receiver.empId.split(',');
                    var empNames = receiver.empName.split(',');
                    angular.forEach(empIds, function (o, index) {
                        receiverArr.push({type: 'EMP', receiverId: o, receiverName: empNames[index]});
                    });
                }
            }
            if (receiverArr.length == 0) {
                AlertFactory.error('没有获取到接收人信息,请选择!');
                return;
            }
            $scope.beans.receivers = receiverArr;
        };
        // 保存
        $scope.save = function (createNew) {
            // 获取富文本内容
            if (!editor.text()) {
                AlertFactory.error('请输入内容!');
                return;
            }
            $scope.beans.content = editor.html();
            var attachmentArray = $scope.uploadOptions.getAttachment().concat(attachmentIds);
            $scope.beans.attachmentIds = attachmentArray.join(',');
            $scope.beans.attachments = attachmentArray.length || 0;
            $scope.getReceiver();
            var promise = NoticeService.save($scope.beans, function (data) {
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
            $scope.beans.content = editor.html();
            ModalFactory.confirm({
                scope: $scope,
                content: '<span class="error">执行过后，将会删除之前的发布记录并重新发布（之前已经查看过该公告的用户将会重新收到通知，未收到的用户不会再收到），请确认!</span>',
                callback: function () {
                    var attachmentArray = $scope.uploadOptions.getAttachment().concat(attachmentIds);
                    $scope.beans.attachmentIds = attachmentArray.join(',');
                    $scope.beans.attachments = attachmentArray.length || 0;
                    $scope.getReceiver();
                    var promise = NoticeService.update($scope.beans, function (data) {
                        AlertFactory.success('更新成功!');
                        $scope.form.$setValidity('committed', false);
                        CommonUtils.addTab('update');
                        CommonUtils.delay($scope.back, 2000);
                    });
                    CommonUtils.loading(promise, '更新中...');
                }
            });
        };

        // 加载数据
        $scope.load = function (id, callback) {
            var promise = NoticeService.get({id: id}, function (data) {
                $scope.beans = data.data || {};

                editor.html($scope.beans.content);

                // 对数据进行回显处理
                var emp = [];
                var org = [];
                var position = [];
                $scope.receivers = {};
                $scope.receivers.all = false;
                angular.forEach($scope.beans.receivers || [], function (o) {
                    var receiveType = o.type;
                    if (receiveType == 'ALL') {
                        $scope.receivers.all = true;
                    } else if (receiveType == 'ORG') {
                        org.push(o.receiveName);
                    } else if (receiveType == 'POSITION') {
                        position.push(o.receiveName);
                    } else if (receiveType == 'EMP') {
                        emp.push(o.receiveName);
                    }
                });
                if (!$scope.receivers.all) {
                    $scope.receivers.empName = emp.join(',');
                    $scope.receivers.orgName = org.join(',');
                    $scope.receivers.positionName = position.join(',');
                }

                callback && callback();
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        if (pageType == 'add') {
            $scope.beans = {};
            $scope.receivers = {all: true};
        } else if (pageType == 'modify') {
            $scope.load(id);
        } else if (pageType == 'detail') {
            $scope.load(id, function () {
                editor.readonly();
            });
            $('input,textarea,select').attr('disabled', 'disabled');
            $('span.add-on>.icons').remove();
        } else {
            AlertFactory.error($scope, '错误的页面类型');
        }
    });
})(window, angular, jQuery);