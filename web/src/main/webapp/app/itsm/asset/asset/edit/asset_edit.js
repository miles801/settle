/**
 * 资产编辑
 */
(function (window, angular, $) {
    var app = angular.module('itsm.asset.edit', [
        'itsm.asset',
        'asset.assetGroup', // 资产组
        'itsm.appSetting',  // 系统
        'base.emp',  // 员工
        'eccrm.angular',
        'eccrm.angularstrap',
        'base.org',
        'eccrm.angular.ztree'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, AssetService, AssetParam, OrgTree,
                                     AppSettingService, EmpModal) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;

        // 部门
        $scope.orgTree = OrgTree.pick(function (o) {
            $scope.beans.orgId = o.id;
            $scope.beans.orgName = o.name;
        });
        $scope.clearOrg = function () {
            $scope.beans.orgId = null;
            $scope.beans.orgName = null;
        };

        // 所属系统
        $scope.appTree = {
            data: function () {
                return CommonUtils.promise(function (defer) {
                    var promise = AppSettingService.queryValid(function (data) {
                        defer.resolve(data.data || []);
                    });
                    CommonUtils.loading(promise);
                });
            },
            click: function (o) {
                $scope.beans.appId = o.id;
                $scope.beans.appName = o.name;
            }
        };
        $scope.clearApp = function () {
            $scope.beans.appId = null;
            $scope.beans.appName = null;
        };


        // 资产组
        $scope.assetGroupTree = AssetParam.groupTree(function (o) {
            $scope.beans.assetGroupId = o.id;
            $scope.beans.assetGroupName = o.name;
            $scope.beans.assetValue = Math.max(o.valueC, o.valueI, o.valueA);
        });
        $scope.clearAssetGroup = function () {
            $scope.beans.assetGroupId = null;
            $scope.beans.assetGroupName = null;
            $scope.beans.assetValue = null;
        };

        // 资产位置
        $scope.locations = [{name: "请选择..."}];
        AssetParam.location(function (o) {
            $scope.locations.push.apply($scope.locations, o);
        });

        // 管理者
        $scope.pickMaster = function () {
            EmpModal.pickMulti({}, function (emps) {
                var ids = [];
                var names = [];
                angular.forEach(emps, function (emp) {
                    ids.push(emp.id);
                    names.push(emp.name);
                });
                $scope.beans.masterId = ids.join(',');
                $scope.beans.masterName = names.join(',');
            });
        };
        $scope.clearMaster = function () {
            $scope.beans.masterId = null;
            $scope.beans.masterName = null;
        };

        // 资产分类树形
        $scope.assetTypeTree = AssetParam.typeTree(function (o) {
            var beans = $scope.beans;
            if (o.level == 2) {
                beans.assetType3 = o.id;
                beans.assetTypeName3 = o.name;
                var p = o.getParentNode();
                beans.assetType2 = p.id;
                beans.assetTypeName2 = p.name;
                p = p.getParentNode();
                beans.assetType1 = p.id;
                beans.assetTypeName1 = p.name;
            } else if (o.level == 1) {
                beans.assetType2 = o.id;
                beans.assetTypeName2 = o.name;
                var p = o.getParentNode();
                beans.assetType1 = p.id;
                beans.assetTypeName1 = p.name;
            } else if (o.level == 0) {
                beans.assetType1 = o.id;
                beans.assetTypeName1 = o.name;
            }
        });

        $scope.clearAssetType = function () {
            var beans = $scope.beans;
            beans.assetType1 = null;
            beans.assetTypeName1 = null;
            beans.assetType2 = null;
            beans.assetTypeName2 = null;
            beans.assetType3 = null;
            beans.assetTypeName3 = null;
        };

        var validate = function () {
            var beans = $scope.beans;
            if (beans.assetTypeName1 == '软件') {
                if (!beans.ip) {
                    AlertFactory.error('软件资源，IP为必填项!');
                    return false;
                }
            }
            return true;
        };
        // 保存
        $scope.save = function (createNew) {
            if (!validate()) {
                return;
            }
            var promise = AssetService.save($scope.beans, function (data) {
                AlertFactory.success('保存成功!');
                CommonUtils.addTab('update');
                if (createNew === true) {
                    $scope.beans.name = null;
                    $scope.beans.code = null;
                } else {
                    $scope.form.$setValidity('committed', false);
                    CommonUtils.delay($scope.back, 2000);
                }
            });
            CommonUtils.loading(promise, '保存中...');
        };


        // 更新
        $scope.update = function () {
            if (!validate()) {
                return;
            }
            var promise = AssetService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id, callback) {
            var promise = AssetService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
                if (callback) {
                    callback();
                }
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        $scope.ip = {
            validateMsg: '错误的IP格式',
            validateFn: function (v) {
                if (!v) {
                    return true;
                }
                return /\d{2,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/.test(v);
            }
        };
        if (pageType == 'add') {
            $scope.beans = {};
        } else if (pageType == 'modify') {
            $scope.load(id);
        } else if (pageType == 'detail') {
            $scope.load(id);
            $('input,textarea,select').attr('disabled', 'disabled');
            $('span.add-on>.icons').remove();
        } else if (pageType == 'clone') {
            $scope.load(id, function () {
                // 清除不需要克隆的属性
                var beans = $scope.beans;
                beans.id = null;
                beans.name = null;
                beans.code = null;
                beans.creatorId = null;
                beans.creatorName = null;
                beans.createdDatetime = null;
                beans.modifierId = null;
                beans.modifierName = null;
                beans.modifiedDatetimeName = null;
            });
        } else {
            AlertFactory.error($scope, '错误的页面类型');
        }

    });
})(window, angular, jQuery);