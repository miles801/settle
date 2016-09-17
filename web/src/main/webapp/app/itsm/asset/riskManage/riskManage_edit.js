/**
 * 资产管理风险编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('itsm.asset.riskManage.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'itsm.asset.riskManage',
        'itsm.asset',   // 资产
        'itsm.otherSetting',   // 威胁、弱点、风险描述
        'asset.assetGroup'  // 资产组
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, RiskManageService,
                                     AssetService, AssetParam, OtherSettingParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();
        var assetId = null;
        $scope.wxTree = CommonUtils.defer();
        $scope.rdTree = CommonUtils.defer();
        $scope.fxmsTree = CommonUtils.defer();
        if (pageType == 'add') {
            assetId = $('#assetId').val();
            if (!assetId) {
                AlertFactory.error('错误的访问!没有获取到资产ID!');
                return;
            }
        }

        $scope.back = CommonUtils.back;

        // 策略
        $scope.handlePolicys = [{name: '请选择...'}];
        AssetParam.policy(function (o) {
            $scope.handlePolicys.push.apply($scope.handlePolicys, o);
        });

        // 等级
        $scope.values = [{name: '请选择...'}];
        AssetParam.value(function (o) {
            $scope.values.push.apply($scope.values, o);
        });


        $scope.clearWx = function () {
            $scope.beans.wxId = null;
            $scope.beans.wxName = null;
        };

        $scope.clearRd = function () {
            $scope.beans.rdId = null;
            $scope.beans.rdName = null;
        };
        $scope.clearFxms = function () {
            $scope.beans.fxmsID = null;
            $scope.beans.fxmsName = null;
        };

        // A,T,V改变时触发的事件
        $scope.valueChange = function () {
            var beans = $scope.beans;
            var a = beans.assetValue;
            var t = beans.wxValue;
            var v = beans.rdValue;
            if (!a || !t || !v) {
                beans.fxValue = null;
            } else {
                var value = beans.fxValue = a * t * v;
                if (value < 21) {
                    beans.fxLevel = 1;
                } else if (value < 33) {
                    beans.fxLevel = 2;
                } else if (value < 49) {
                    beans.fxLevel = 3;
                } else if (value < 81) {
                    beans.fxLevel = 4
                } else {
                    beans.fxLevel = 5
                }
            }
        };
        // 保存
        $scope.save = function (createNew) {
            var promise = RiskManageService.save($scope.beans, function (data) {
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
            var promise = RiskManageService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id, callback) {
            var promise = RiskManageService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
                callback && callback();
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        // 加载资产信息
        $scope.loadAsset = function (assetId) {
            AssetService.get({id: assetId}, function (o) {
                o = o.data || {};
                $scope.beans.assetId = o.id;
                $scope.beans.assetName = o.name;
                $scope.beans.assetValue = o.assetValue;
                $scope.beans.assetGroupId = o.assetGroupId;
                $scope.beans.assetGroupName = o.assetGroupName;
                var assetType = o.assetType1;
                // 威胁
                $scope.wxTree.resolve(OtherSettingParam.wxTree(assetType, function (o) {
                    $scope.beans.wxId = o.id;
                    $scope.beans.wxName = o.name;

                }));
                // 弱点
                $scope.rdTree.resolve(OtherSettingParam.rdTree(assetType, function (o) {
                    $scope.beans.rdId = o.id;
                    $scope.beans.rdName = o.name;
                }));
                // 风险描述
                $scope.fxmsTree.resolve(OtherSettingParam.fxmsTree(assetType, function (o) {
                    $scope.beans.fxmsID = o.id;
                    $scope.beans.fxmsName = o.name;
                }));
            });
        };
        if (pageType == 'add') {
            $scope.beans = {};
            $scope.loadAsset(assetId);
        } else if (pageType == 'modify') {
            $scope.load(id, function () {
                $scope.loadAsset($scope.beans.assetId);
            });
        } else if (pageType == 'detail') {
            $scope.load(id);
            $('input,textarea,select').attr('disabled', 'disabled');
            $('.add-on>.icons').remove();
            $scope.wxTree.reject();
            $scope.rdTree.reject();
            $scope.fxmsTree.reject();
        } else {
            AlertFactory.error($scope, '错误的页面类型');
        }
    });
})(window, angular, jQuery);