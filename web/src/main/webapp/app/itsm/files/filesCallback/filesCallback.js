/**
 * 意见反馈
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.files.filesCallback', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('FilesCallbackService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/files/filesCallback/:method'), {}, {
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

    app.service('FilesCallbackParam', function (ParameterLoader) {
        var o = {};
        return o;
    });

    app.service('FilesCallbackModal', function ($modal, ModalFactory, AlertFactory, CommonUtils, FilesCallbackService) {
        return {
            add: function (options, callback) {
                if (!options.filesId) {
                    alert('页面加载失败!没有获得文件ID');
                    return;
                }
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/itsm/files/filesCallback/modal-filesCallback-edit.ftl.html'),
                    backdrop: 'static'
                });
                var $scope = modal.$scope;
                $scope.beans = {filesId: options.filesId};
                $scope.save = function () {
                    var promise = FilesCallbackService.save($scope.beans, function (data) {
                        AlertFactory.success('保存成功!');
                        angular.isFunction(callback) && callback();
                        $scope.$hide();
                    });
                    CommonUtils.loading(promise);
                };
            },
            view: function (options, callback) {
                var filesId = options.filesId;
                if (!filesId) {
                    alert('页面加载失败!没有获得文件ID');
                    return;
                }
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/itsm/files/filesCallback/modal-filesCallback-list.ftl.html'),
                    backdrop: 'static'
                });
                var $scope = modal.$scope;
                $scope.pager = {
                    limit: 5,
                    fetch: function () {
                        return CommonUtils.promise(function (defer) {
                            var promise = FilesCallbackService.pageQuery({
                                filesId: filesId,
                                start: this.start,
                                limit: this.limit
                            }, function (data) {
                                $scope.beans = data.data || {total: 0};
                                defer.resolve($scope.beans);
                            });
                            CommonUtils.loading(promise);
                        });
                    },
                    afterInit: function () {
                        this.query();
                    }
                };

                $scope.query = function () {
                    $scope.pager.query();
                };

                ModalFactory.afterShown(modal, $scope.query);
            }
        }
    });
})(angular);
