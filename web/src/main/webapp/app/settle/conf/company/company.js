/**
 * 文交所
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('settle.conf.company', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('CompanyService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/settle/conf/company/:method'), {}, {
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

    app.service('CompanyParam', function (ParameterLoader, CommonUtils, CompanyService) {
        var o = {
            /**
             * 查询有效的数据并返回一个树形对象，适用于ztree-single
             * @param callback 选中节点后的回调
             */
            validTree: function (callback) {
                return {
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            var promise = CompanyService.queryValid(function (data) {
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
        return o;
    });

    app.service('CompanyModal', function ($modal, ModalFactory, AlertFactory, CommonUtils, CompanyService) {
        var common = function (options, callback) {
            var defaults = {
                id: null,//id
                pageType: null,     // 必填项,页面类型add/modify/view
                callback: null     // 点击确定后要执行的函数
            };
            options = angular.extend({}, defaults, options);
            callback = callback || options.callback;
            var modal = $modal({
                template: CommonUtils.contextPathURL('/app/settle/conf/company/modal-company.html'),
                backdrop: 'static'
            });
            var $scope = modal.$scope;
            var pageType = $scope.pageType = options.pageType;
            var id = options.id;
            $scope.save = function () {
                var promise = CompanyService.save($scope.beans, function (data) {
                    AlertFactory.success('保存成功!');
                    angular.isFunction(callback) && callback();
                    $scope.$hide();
                });
                CommonUtils.loading(promise);
            };

            $scope.update = function () {
                var promise = CompanyService.update($scope.beans, function (data) {
                    AlertFactory.success('更新成功!');
                    angular.isFunction(callback) && callback();
                    $scope.$hide();
                });
                CommonUtils.loading(promise);
            };

            var load = function (id, callback) {
                var promise = CompanyService.get({id: id}, function (data) {
                    $scope.beans = data.data || {};
                    callback && callback($scope.beans);
                });
                CommonUtils.loading(promise);
            };

            if (pageType == 'add') {
                $scope.beans = {};
            } else if (pageType == 'modify') {
                load(id);
            } else {
                load(id, function () {
                    $('.modal-body').find('input,select,textarea').attr('disabled', 'disabled');
                    $('.modal-body').find('.icons.icon').remove();
                });
            }
        };
        return {
            add: function (options, callback) {
                var o = angular.extend({}, options, {pageType: 'add'});
                common(o, callback);
            },
            modify: function (options, callback) {
                if (!options.id) {
                    alert('更新页面加载失败!没有获得ID');
                    return;
                }
                var o = angular.extend({}, options, {pageType: 'modify'});
                common(o, callback);
            },
            view: function (options, callback) {
                if (!options.id) {
                    alert('明细页面加载失败!没有获得ID');
                    return;
                }
                var o = angular.extend({}, options, {pageType: 'view'});
                common(o, callback);
            }
        }
    });
})(angular);
