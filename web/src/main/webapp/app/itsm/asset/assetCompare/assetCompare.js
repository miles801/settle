/**
 * 资产对比
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.asset.assetCompare', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('AssetCompareService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/asset/assetCompare/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 不带分页的列表查询
            query: {method: 'POST', params: {method: 'query'}, isArray: false},

            // 根据id查询信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            // 分页查询
            pageQuery: {
                method: 'POST',
                params: {method: 'pageQuery', limit: '@limit', start: '@start'},
                isArray: false
            },

            // 根据id字符串（使用逗号分隔多个值）
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 重新比对
            retry: {method: 'POST', params: {method: 'retry', ids: '@ids'}, isArray: false},

            // 查询指定对比中所有未登记的IP
            queryNotRegisterIp: {method: 'GET', params: {method: 'ips', id: '@id'}, isArray: false}
        })
    });

    app.service('AssetCompareParam', function (ParameterLoader) {
        var o = {};
        return o;
    });

    app.service('AssetCompareModal', function ($modal, AlertFactory, ModalFactory, CommonUtils, AssetCompareService) {
        return {
            ips: function (id, ip) {
                if (!id || !ip) {
                    AlertFactory.error('对比ID和网段不能为空!');
                    return;
                }
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/itsm/asset/assetCompare/modal-assetCompareIps.ftl.html'),
                    backdrop: 'static'
                });
                var $scope = modal.$scope;

                $scope.ip = ip;
                $scope.query = function () {
                    var promise = AssetCompareService.queryNotRegisterIp({id: id}, function (o) {
                        $scope.beans = o.data || [];
                    });
                    CommonUtils.loading(promise);
                };

                $scope.exportData = function () {
                    if ($scope.beans.length < 1) {
                        AlertFactory.error('无可导出数据!');
                        return;
                    }
                    window.open(CommonUtils.contextPathURL('/itsm/asset/assetCompare/ips/export?id=' + id));
                };
                $scope.query();

            }
        }
    });

})(angular);
