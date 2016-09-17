/**
 * 文件管理编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.files.files.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'base.org',
        'base.emp',
        'base.position',
        'itsm.files.files'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, FilesService, FilesParam,
                                     PositionModal, OrgTree, EmpModal) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;

        $scope.receiver = {
            all: true,
            orgId: null,
            orgName: null,
            positionId: null,
            positionName: null,
            empId: null,
            empName: null
        };


        $scope.scopeTree = FilesParam.scope1Tree(function (o) {
            var beans = $scope.beans;
            if (o.level == 1) {
                beans.scope2 = o.value;
                beans.scope2Name = o.name;
                var p = o.getParentNode();
                beans.scope1 = p.value;
                beans.scope1Name = p.name;
            } else if (o.level == 0) {
                beans.scope1 = o.value;
                beans.scope1Name = o.name;
            }
        });

        $scope.clearScope = function () {
            var beans = $scope.beans;
            beans.scope1 = null;
            beans.scope1Name = null;
            beans.scope2 = null;
            beans.scope2Name = null;
        };

        // 参数：级别
        $scope.levels = [{name: '请选择...'}];
        FilesParam.level(function (o) {
            $scope.levels.push.apply($scope.levels, o);
        });

        // 参数：文件类型
        $scope.types = [{name: '请选择...'}];
        FilesParam.type(function (o) {
            $scope.types.push.apply($scope.types, o);
        });

        // 参数：密级
        $scope.secrets = [{name: '请选择...'}];
        FilesParam.secret(function (o) {
            $scope.secrets.push.apply($scope.secrets, o);
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
            },
            onSuccess: function (o) {
                $scope.$apply(function () {
                    $scope.beans.name = ($scope.beans.name ? ($scope.beans.name + ' ; ') : '') + o.fileName;
                });
            }
        };
        // 保存
        $scope.save = function (createNew) {
            // 附件
            var attachment = $scope.uploadOptions.getAttachment();
            if (attachment.length == 0) {
                AlertFactory.error('请上传附件!');
                return;
            }

            // 接收人
            var receivers = [];
            if ($scope.receiver.all) {
                receivers.push({receiveType: 'ALL'});
            } else {
                if ($scope.receiver.orgId) {
                    var orgIds = $scope.receiver.orgId.split(',');
                    var orgNames = $scope.receiver.orgName.split(',');
                    angular.forEach(orgIds, function (o, index) {
                        receivers.push({receiveType: 'ORG', receiveId: o, receiveName: orgNames[index]});
                    });
                }
                if ($scope.receiver.positionId) {
                    var positionIds = $scope.receiver.positionId.split(',');
                    var positionNames = $scope.receiver.positionName.split(',');
                    angular.forEach(positionIds, function (o, index) {
                        receivers.push({receiveType: 'POSITION', receiveId: o, receiveName: positionNames[index]});
                    });
                }
                if ($scope.receiver.empId) {
                    var empIds = $scope.receiver.empId.split(',');
                    var empNames = $scope.receiver.empName.split(',');
                    angular.forEach(empIds, function (o, index) {
                        receivers.push({receiveType: 'EMP', receiveId: o, receiveName: empNames[index]});
                    });
                }
            }
            if (receivers.length == 0) {
                AlertFactory.error('没有获取到接收人信息,请选择!');
                return;
            }
            ModalFactory.confirm({
                scope: $scope,
                content: '文件发布后，相关的人员将会看到，请再次确认操作?',
                callback: function () {
                    var o = {
                        files: $scope.beans,
                        filesReceivers: receivers,
                        attachmentIds: attachment.join(',')
                    };

                    var promise = FilesService.save(o, function (data) {
                        AlertFactory.success('发布成功!两秒后页面将会返回!');
                        CommonUtils.addTab('update');
                        $scope.form.$setValidity('committed', false);
                        CommonUtils.delay($scope.back, 2000);
                    });
                    CommonUtils.loading(promise);
                }
            });
        };


        // 加载数据
        $scope.load = function (id, callback) {
            var promise = FilesService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
                callback && callback();
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        if (pageType == 'add') {
            $scope.beans = {};
        } else if (pageType == 'detail') {
            $scope.load(id, function () {
                // 对数据进行回显处理
                var emp = [];
                var org = [];
                var position = [];
                $scope.receiver.all = false;
                angular.forEach($scope.beans.receivers || [], function (o) {
                    var receiveType = o.receiveType;
                    if (receiveType == 'ALL') {
                        $scope.receiver.all = true;
                    } else if (receiveType == 'ORG') {
                        org.push(o.receiveName);
                    } else if (receiveType == 'POSITION') {
                        position.push(o.receiveName);
                    } else if (receiveType == 'EMP') {
                        emp.push(o.receiveName);
                    }
                });
                if (!$scope.receiver.all) {
                    $scope.receiver.empName = emp.join(',');
                    $scope.receiver.orgName = org.join(',');
                    $scope.receiver.positionName = position.join(',');
                }
            });
            $('input,textarea,select').attr('disabled', 'disabled');
            $('span.add-on>.icons').remove();
        } else {
            AlertFactory.error($scope, '错误的页面类型');
        }
    });
})(window, angular, jQuery);