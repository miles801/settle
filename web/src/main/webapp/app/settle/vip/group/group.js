/**
 * 团队
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('settle.vip.group', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('GroupService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/settle/vip/group/:method'), {}, {
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
                params: {method: 'pageQuery', limit: '@limit', start: '@start'},
                isArray: false
            },

            // 清空
            clear: {method: 'POST', params: {method: 'clear'}, isArray: false}
        })
    });

    app.service('GroupParam', function (ParameterLoader, CommonUtils, GroupService, $http) {
        var o = {
            /**
             * 查询有效的数据并返回一个树形对象，适用于ztree-single
             * @param callback 选中节点后的回调
             */
            validTree: function (callback) {
                return {
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            var promise = GroupService.queryValid(function (data) {
                                defer.resolve(data.data || []);
                            });
                            CommonUtils.loading(promise);
                        });
                    },
                    position: 'fixed',
                    click: callback
                };
            }
        };

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
