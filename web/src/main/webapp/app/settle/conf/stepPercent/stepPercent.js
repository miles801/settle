/**
 * 阶梯比例
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('settle.conf.stepPercent', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('StepPercentService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/settle/conf/stepPercent/:method'), {}, {
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

    app.service('StepPercentParam', function (ParameterLoader, $http, CommonUtils) {
        var o = {};

        // 文交所
        o['company'] = function (callback) {
            $http.post(CommonUtils.contextPathURL('/settle/conf/company/query'))
                .success(function (data) {
                    data = data.data || [];
                    angular.forEach(data, function (o) {
                        o.value = o.id;
                    });
                    callback(data || []);
                });
        };
        return o;
    });

})(angular);
