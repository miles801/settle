/**
 * 团队会员
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('settle.report.groupVip', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('GroupVipService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/settle/report/groupVip/:method'), {}, {
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
                params: {
                    method: 'pageQuery',
                    limit: '@limit',
                    start: '@start',
                    orderBy: '@orderBy',
                    reverse: '@reverse'
                },
                isArray: false
            },

            // 根据id字符串（使用逗号分隔多个值）
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });

    app.service('GroupVipParam', function (ParameterLoader) {
        var o = {};

        // 文交所
        o['company'] = function (callback) {
            ParameterLoader.loadSysParam('VIP_COMPANY', callback);
        };

        return o;
    });

})(angular);
