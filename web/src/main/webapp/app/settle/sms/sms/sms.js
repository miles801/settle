/**
 * 短信
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('settle.sms.sms', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('SmsService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/settle/sms/sms/:method'), {}, {
            // 保存
            init: {method: 'POST', params: {method: 'init'}, isArray: false},

            // 不带分页的列表查询
            query: {method: 'POST', params: {method: 'query'}, isArray: false},

            // 批量启用
            enable: {method: 'POST', params: {method: 'enable', ids: '@ids'}, isArray: false},

            // 批量禁用
            disable: {method: 'POST', params: {method: 'disable', ids: '@ids'}, isArray: false},

            // 根据id查询信息
            get: {method: 'GET', params: {method: 'get'}, isArray: false},

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

})(angular);
