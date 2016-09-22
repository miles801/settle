/**
 * 短信充值记录
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('settle.sms.smsBuy', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('SmsBuyService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/settle/sms/smsBuy/:method'), {}, {
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

    app.service('SmsBuyParam', function (ParameterLoader) {
        var o = {};
        return o;
    });

    app.service('SmsBuyModal', function ($modal, ModalFactory, AlertFactory, CommonUtils, SmsBuyService) {
        return {
            add: function (callback) {
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/settle/sms/smsBuy/modal-smsBuy.html'),
                    backdrop: 'static'
                });
                var $scope = modal.$scope;
                $scope.beans = {
                    occurTime: new Date().getTime()
                };
                $scope.save = function () {
                    var promise = SmsBuyService.save($scope.beans, function (data) {
                        AlertFactory.success('保存成功!');
                        angular.isFunction(callback) && callback();
                        $scope.$hide();
                    });
                    CommonUtils.loading(promise);
                };
            }
        }
    });
})(angular);
