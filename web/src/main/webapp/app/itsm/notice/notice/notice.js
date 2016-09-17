/**
 * 公告
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.notice.notice', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('NoticeService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/notice/notice/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save', attachmentIds: '@attachmentIds'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update', attachmentIds: '@attachmentIds'}, isArray: false},

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
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 查询我的公告
            mine: {
                method: 'POST',
                params: {method: 'mine', limit: '@limit', start: '@start'},
                isArray: false
            },

            // 阅读公告
            read: {method: 'POST', params: {method: 'read', id: '@id'}, isArray: false}
        })
    });

    app.service('NoticeParam', function (ParameterLoader) {
        var o = {};

        // 栏目
        o['category'] = function (callback) {
            ParameterLoader.loadSysParam('NOTICE_CATEGORY', callback);
        };


        // 重要性
        o['important'] = function (callback) {
            ParameterLoader.loadSysParam('NOTICE_IMPORTANT', callback);
        };

        return o;
    });

})(angular);
