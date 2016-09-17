/**
 * 资产组
 * Created by Michael on 2016-08-09 17:06:00.
 */
(function (angular) {
    var app = angular.module('asset.assetGroup', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('AssetGroupService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/assetGroup/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 根据id查询信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            // 批量禁用
            disable: {method: 'POST', params: {method: 'disable', ids: '@ids'}, isArray: false},

            // 批量启用
            enable: {method: 'POST', params: {method: 'enable', ids: '@ids'}, isArray: false},

            // 查询指定资产组的最大价值
            maxValue: {method: 'GET', params: {method: 'value-max', id: '@id'}, isArray: false},

            // 查询所有有效的资产组
            queryValid: {method: 'POST', params: {method: 'query-valid'}, isArray: false},

            // 分页查询
            pageQuery: {
                method: 'POST',
                params: {method: 'pageQuery', limit: '@limit', start: '@start'},
                isArray: false
            },

            // 根据id字符串（使用逗号分隔多个值）
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 导入数据
            importData: {method: 'POST', params: {method: 'import', attachmentIds: '@attachmentIds'}, isArray: false}
        })
    });

    app.service('AssetParam', function (ParameterLoader, AssetGroupService, CommonUtils) {
        return {
            /**
             * 资产组树
             */
            groupTree: function (callback) {
                return {
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            var promise = AssetGroupService.queryValid(function (data) {
                                defer.resolve(data.data || []);
                            });
                            CommonUtils.loading(promise);
                        });
                    },
                    position: 'fixed',
                    click: callback
                };

            },
            /**
             * 资产来源
             * @param callback
             */
            source: function (callback) {
                ParameterLoader.loadSysParam('ASSET_SOURCE', callback);
            },
            /**
             * 资产价值
             * @param callback
             */
            value: function (callback) {
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
             * 资产类型（一级分类）
             * @param callback
             */
            type: function (callback) {
                ParameterLoader.loadSysParam('ASSET_TYPE1', callback);
            },
            /**
             * 资产位置
             */
            location: function (callback) {
                ParameterLoader.loadSysParam('ASSET_LOCATION', callback);
            },
            /**
             * 资产风险处置策略
             */
            policy: function (callback) {
                ParameterLoader.loadSysParam('ASSET_HANDLE_POLICY', callback);
            },

            /**
             * 资产类型树
             * @param callback 单击节点后的回掉
             */
            typeTree: function (callback) {
                return ParameterLoader.cascadeTree('ASSET_TYPE1', callback);
            }
        };
    });

})(angular);
