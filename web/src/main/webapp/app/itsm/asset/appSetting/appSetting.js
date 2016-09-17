/**
 * 业务应用设置
 * Created by Michael on 2016-08-09 23:27:34.
 */
(function (angular) {
    var app = angular.module('itsm.appSetting', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('AppSettingService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/appSetting/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 根据id查询信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            // 分页查询
            pageQuery: {
                method: 'POST',
                params: {method: 'pageQuery', limit: '@limit', start: '@start'},
                isArray: false
            },

            // 查询所有有效的系统（不分页）
            queryValid: {method: 'POST', params: {method: 'query-valid'}, isArray: false},

            // 根据id字符串（使用逗号分隔多个值）
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });

    app.service('AppSettingParam', function (ParameterLoader, CommonUtils, AppSettingService) {
        return {
            /**
             * 重要性
             */
            important: function (callback) {
                var data = [
                    {name: "1", value: 1},
                    {name: "2", value: 2},
                    {name: "3", value: 3},
                    {name: "4", value: 4},
                    {name: "5", value: 5}
                ];
                callback(data);
            },

            /**
             * 等保等级
             */
            level: function (callback) {
                var data = [
                    {name: "1", value: 1},
                    {name: "2", value: 2},
                    {name: "3", value: 3},
                    {name: "4", value: 4},
                    {name: "5", value: 5}
                ];
                callback(data);
            },
            /**
             * 返回一个树形对象，适用于ztree-single
             */
            validTree: function (callback) {
                return {
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            var promise = AppSettingService.queryValid({}, function (data) {
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
    });

})(angular);
