/**
 * 中文漏洞库
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.bug.bugLib', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('BugLibService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/bug/bugLib/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 不带分页的列表查询
            query: {method: 'POST', params: {method: 'query'}, isArray: false},

            // 导入数据
            importData: {method: 'POST', params: {method: 'import', attachmentIds: '@attachmentIds'}, isArray: false},

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

    app.service('BugLibParam', function (ParameterLoader) {
        var o = {};

        // 风险等级
        o['level'] = function (callback) {
            ParameterLoader.loadSysParam('BUG_RISK_LEVEL', callback);
        };

        return o;
    });

})(angular);
