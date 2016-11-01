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
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 设置返佣
            setBonus: {method: 'POST', params: {method: 'bonus'}, isArray: false},

            // 发送短信
            sendSms: {method: 'POST', params: {method: 'sendSms'}, isArray: false},
            // 分析1
            analysis1: {
                method: 'POST', params: {
                    method: 'analysis1',
                    company: '@company',
                    m1: '@m1',
                    m2: '@m2',
                    orderBy: '@orderBy',
                    reverse: '@reverse'
                }, isArray: false
            },
            // 分析2
            analysis2: {method: 'POST', params: {method: 'analysis2', company: '@company'}, isArray: false},
            // 分析3
            analysis3: {method: 'POST', params: {method: 'analysis3', groupName: '@groupName'}, isArray: false}
        })
    });

    app.service('GroupVipParam', function (ParameterLoader, CommonUtils, $http) {
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
