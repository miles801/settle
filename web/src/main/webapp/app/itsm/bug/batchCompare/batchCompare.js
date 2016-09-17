/**
 * 批次对比
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.bug.batchCompare', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('BatchCompareService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/bug/batchCompare/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 不带分页的列表查询
            query: {method: 'POST', params: {method: 'query'}, isArray: false},

            // 根据id查询信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            // 获取比较结果
            compare: {method: 'GET', params: {method: 'compare', id: '@id'}, isArray: false},

            // 更新批次的数据
            execute: {method: 'POST', params: {method: 'execute', id: '@id'}, isArray: false},

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

    app.service('BatchCompareParam', function (ParameterLoader) {
        var o = {};
        return o;
    });

})(angular);
