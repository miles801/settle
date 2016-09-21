/**
 * 映射
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('settle.mapping.mapping', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('MappingService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/settle/mapping/mapping/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 不带分页的列表查询
            query: {method: 'POST', params: {method: 'query'}, isArray: false},

            // 批量启用
            enable: {method: 'POST', params: {method: 'enable', ids: '@ids'}, isArray: false},

            // 批量禁用
            disable: {method: 'POST', params: {method: 'disable', ids: '@ids'}, isArray: false},

            // 查询所有有效的数据，不带分页
            queryValid: {method: 'POST', params: {method: 'query-valid'}, isArray: false},
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

    app.service('MappingParam', function (ParameterLoader, Parameter, CommonUtils, MappingService) {
        var o = {};

        // 文交所
        o['company'] = function (callback) {
            ParameterLoader.loadSysParam('VIP_COMPANY', callback);
        };

        // 表名称
        o['name'] = function (callback) {
            ParameterLoader.loadSysParam('TABLE_NAME', callback);
        };
        // 查询所有的映射配置
        o['tables'] = function (name, callback) {
            Parameter.fetchSystemCascade('TABLE_NAME', name, callback);
        };

        return o;
    });

})(angular);
