/**
 * 会员
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('settle.vip.vip', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('VipService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/settle/vip/vip/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 不带分页的列表查询
            query: {method: 'POST', params: {method: 'query'}, isArray: false},

            // 导入数据
            importData: {
                method: 'POST',
                params: {method: 'import', company: '@company', attachmentIds: '@attachmentIds'},
                isArray: false
            },

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

    app.service('VipParam', function (ParameterLoader) {
        var o = {};

        // 签约状态
        o['assignStatus'] = function (callback) {
            ParameterLoader.loadSysParam('VIP_ASSIGN_STATUS', callback);
        };


        // 状态
        o['status'] = function (callback) {
            ParameterLoader.loadSysParam('VIP_STATUS', callback);
        };


        // 所属文交所
        o['company'] = function (callback) {
            ParameterLoader.loadSysParam('VIP_COMPANY', callback);
        };

        return o;
    });

})(angular);
