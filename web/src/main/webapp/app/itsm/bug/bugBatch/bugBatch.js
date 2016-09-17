/**
 * 批次
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.bug.bugBatch', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('BugBatchService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/bug/bugBatch/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save', attachmentIds: '@attachmentIds'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update', attachmentIds: '@attachmentIds'}, isArray: false},

            // 不带分页的列表查询
            query: {method: 'POST', params: {method: 'query'}, isArray: false},

            // 导入数据
            importData: {method: 'POST', params: {method: 'import', attachmentIds: '@attachmentIds'}, isArray: false},

            // 根据id查询信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            // 查询批次的漏洞详情
            bugs: {method: 'POST', params: {method: 'bugs'}, isArray: false},

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

            // 删除指定批次指定IP的数据
            deleteByIp: {
                method: 'DELETE',
                params: {method: 'delete-ip', ip: '@ip', code: '@batchId'},
                isArray: false
            },

            // 根据id字符串（使用逗号分隔多个值）
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });

    app.service('BugBatchModal', function ($modal, CommonUtils, BugBatchService, BugBatchParam) {
        return {
            pick: function (options, callback) {
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/itsm/bug/bugBatch/modal-batch.ftl.html'),
                    backdrop: 'static'
                });
                var $scope = modal.$scope;
                options = options || {};
                $scope.condition = angular.extend({}, options.condition);
                callback = callback || options.callback;


                // 参数：扫描类型
                $scope.scanTypes = [{name: '全部'}];
                BugBatchParam.scanType(function (o) {
                    $scope.scanTypes.push.apply($scope.scanTypes, o);
                });

                // 参数：扫描器
                $scope.scanMachines = [{name: '全部'}];
                BugBatchParam.scanMachine(function (o) {
                    $scope.scanMachines.push.apply($scope.scanMachines, o);
                });


                // 分页对象
                $scope.pager = {
                    limit: 5,
                    fetch: function () {
                        return CommonUtils.promise(function (defer) {
                            var obj = angular.extend({
                                start: $scope.pager.start,
                                limit: $scope.pager.limit
                            }, $scope.condition);
                            var promise = BugBatchService.pageQuery(obj, function (data) {
                                data = data.data || {};
                                $scope.beans = data;
                                defer.resolve(data.total);
                            });
                            CommonUtils.loading(promise);
                        });
                    },
                    finishInit: function () {
                        this.query();
                    }
                };

                // 清空查询条件
                $scope.clear = function () {
                    $scope.condition = {};
                };

                // 查询
                $scope.query = function () {
                    $scope.pager.query();
                };

                // 点击确认
                $scope.confirm = function () {
                    if (angular.isFunction(callback)) {
                        callback.call($scope, $scope.selected);
                        modal.hide();
                    }
                }
            }
        }
    });

    app.service('BugBatchParam', function (ParameterLoader) {
        var o = {};

        // 扫描类型
        o['scanType'] = function (callback) {
            ParameterLoader.loadSysParam('BUG_SCAN_TYPE', callback);
        };


        // 扫描器
        o['scanMachine'] = function (callback) {
            ParameterLoader.loadSysParam('BUG_SCAN_MACHINE', callback);
        };

        return o;
    });

})(angular);
