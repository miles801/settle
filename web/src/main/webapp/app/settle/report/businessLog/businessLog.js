/**
 * 交易历史
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('settle.report.businessLog', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('BusinessLogService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/settle/report/businessLog/:method'), {}, {
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

    app.service('BusinessLogParam', function (ParameterLoader) {
        var o = {};

        // 文交所
        o['company'] = function (callback) {
            ParameterLoader.loadSysParam('VIP_COMPANY', callback);
        };

        return o;
    });

})(angular);
