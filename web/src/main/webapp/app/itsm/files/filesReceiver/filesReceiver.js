/**
 * 文件接收者
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.files.filesReceiver', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('FilesReceiverService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/files/filesReceiver/:method'), {}, {
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

    app.service('FilesReceiverParam', function (ParameterLoader) {
        var o = {};

        // 接收者类型
        o['receiveType'] = function (callback) {
            ParameterLoader.loadSysParam('FILES_RECEIVE_TYPE', callback);
        };

        return o;
    });

})(angular);
