/**
 * 其他漏洞
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.bug.bugOther', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('BugOtherService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/bug/bugOther/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save', attachmentIds: '@attachmentIds'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update', attachmentIds: '@attachmentIds'}, isArray: false},

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

    app.service('BugOtherParam', function (ParameterLoader) {
        var o = {};

        // 漏洞分类
        o['type'] = function (callback) {
            ParameterLoader.loadSysParam('BUG_TYPE', callback);
        };

        // 原风险等级
        o['riskLevel'] = function (callback) {
            ParameterLoader.loadSysParam('BUG_RISK_LEVEL', callback);
        };

        // 状态
        o['status'] = function (callback) {
            ParameterLoader.loadSysParam('BUG_REPAIR_STATUS', callback);
        };

        return o;
    });

})(angular);
