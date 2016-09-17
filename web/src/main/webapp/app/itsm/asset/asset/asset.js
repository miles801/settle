/**
 * 资产
 * Created by Michael on 2016-08-11 13:26:25.
 */
(function (angular) {
    var app = angular.module('itsm.asset', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('AssetService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/asset/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 批量启用
            enable: {method: 'POST', params: {method: 'enable', ids: '@ids'}, isArray: false},

            // 批量禁用
            disable: {method: 'POST', params: {method: 'disable', ids: '@ids'}, isArray: false},

            // 导入数据
            importData: {method: 'POST', params: {method: 'import', attachmentIds: '@attachmentIds'}, isArray: false},

            // 根据id查询信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            // 分页查询
            pageQuery: {
                method: 'POST',
                params: {method: 'pageQuery', limit: '@limit', start: '@start'},
                isArray: false
            },
            // 我的资产（分页）
            mine: {method: 'POST', params: {method: 'mine', limit: '@limit', start: '@start'}, isArray: false},

            // 根据id字符串（使用逗号分隔多个值）
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        });
    });
})(angular);
