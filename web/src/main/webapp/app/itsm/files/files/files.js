/**
 * 文件管理
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.files.files', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('FilesService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/files/files/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save', attachmentIds: '@attachmentIds'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update', attachmentIds: '@attachmentIds'}, isArray: false},

            // 不带分页的列表查询
            query: {method: 'POST', params: {method: 'query'}, isArray: false},

            // 批量启用
            enable: {method: 'POST', params: {method: 'enable', ids: '@ids'}, isArray: false},

            // 批量禁用
            disable: {method: 'POST', params: {method: 'disable', ids: '@ids'}, isArray: false},

            // 查询所有有效的数据，不带分页
            queryValid: {method: 'POST', params: {method: 'query-valid'}, isArray: false},

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

            // 查询我的文件
            mine: {
                method: 'POST',
                params: {method: 'mine', limit: '@limit', start: '@start'},
                isArray: false
            },

            // 阅读文件
            read: {method: 'POST', params: {method: 'read', filesId: '@filesId'}, isArray: false},

            // 根据id字符串（使用逗号分隔多个值）
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });

    app.service('FilesParam', function (ParameterLoader, CommonUtils, FilesService) {
        var o = {
            /**
             * 查询有效的数据并返回一个树形对象，适用于ztree-single
             * @param callback 选中节点后的回调
             */
            validTree: function (callback) {
                return {
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            var promise = FilesService.queryValid(function (data) {
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
             * 返回管理框架的级联树形
             */
            scope1Tree: function (callback) {
                return ParameterLoader.cascadeTree('FILE_SCOPE1', callback);
            }
        };

        // 管理框架
        o['scope1'] = function (callback) {
            ParameterLoader.loadSysParam('FILE_SCOPE1', callback);
        };


        // 管理域
        o['scope2'] = function (callback) {
            ParameterLoader.loadSysParam('FILE_SCOPE2', callback);
        };


        // 级别
        o['level'] = function (callback) {
            ParameterLoader.loadSysParam('FILE_LEVEL', callback);
        };


        // 文件类型
        o['type'] = function (callback) {
            ParameterLoader.loadSysParam('FILE_TYPE', callback);
        };


        // 密级
        o['secret'] = function (callback) {
            ParameterLoader.loadSysParam('FILE_SECRET', callback);
        };

        return o;
    });

})(angular);
