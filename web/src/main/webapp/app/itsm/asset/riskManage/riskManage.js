/**
 * 资产管理风险
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.asset.riskManage', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('RiskManageService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/asset/riskManage/:method'), {}, {
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
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });

    app.service('RiskManageParam', function (ParameterLoader) {
        return {};
    });

})(angular);
