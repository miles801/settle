/**
 * 漏洞
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.bug.bug', [
        'ngResource',
        'eccrm.angular',
        'base.param',
        'eccrm.angularstrap'
    ]);

    app.service('BugService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/bug/bug/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 不带分页的列表查询
            query: {method: 'POST', params: {method: 'query'}, isArray: false},

            // 根据id查询信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            // 提交
            commit: {method: 'POST', params: {method: 'commit'}, isArray: false},

            // 整改
            repair: {method: 'POST', params: {method: 'repair', attachmentIds: '@attachmentIds'}, isArray: false},

            // 未通过审核
            approve: {method: 'POST', params: {method: 'approve-commit'}, isArray: false},


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

    app.service('BugParam', function (ParameterLoader) {
        var o = {};

        // 风险等级
        o['riskLevel'] = function (callback) {
            ParameterLoader.loadSysParam('BUG_RISK_LEVEL', callback);
        };

        // bug状态
        o['status'] = function (callback) {
            ParameterLoader.loadSysParam('BUG_STATUS', callback);
        };
        // 优先级
        o['priority'] = function (callback) {
            ParameterLoader.loadSysParam('BUG_PRIORITY', callback);
        };
        // 协助厂商
        o['vendor'] = function (callback) {
            ParameterLoader.loadSysParam('BUG_VENDOR', callback);
        };

        return o;
    });

    app.service('BugModal', function ($modal, CommonUtils, AlertFactory, ModalFactory, BugService, BugParam) {
        return {
            approve: function (id, view, callback) {
                if (!id) {
                    AlertFactory.error('未获取到漏洞ID，无法进行评审!');
                    return;
                }
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/itsm/bug/bug/modal-bug-approve.ftl.html'),
                    backdrop: 'static'
                });
                var $scope = modal.$scope;

                $scope.view = view;
                $scope.beans = {
                    id: id,
                    finish: true
                };

                // 提交评审
                $scope.commit = function () {
                    var promise = BugService.approve($scope.beans, function () {
                        AlertFactory.success('评审成功!');
                        CommonUtils.delay(function () {
                            modal.hide();
                        }, 2000);
                        callback && callback();
                    });
                    CommonUtils.loading(promise);
                };
                ModalFactory.afterShown(modal, function () {
                    if ($scope.view) {
                        $(':radio,textarea', '.modal').attr('disabled', 'disable');
                    }
                    var promise = BugService.get({id: id}, function (o) {
                        $('#certify').html(o.data.certify);
                        $scope.beans = o.data;
                    });
                    CommonUtils.loading(promise);
                });
            }
        }

    });


    app.filter('riskLevel', function (BugParam) {
        var levels = [
            {value: '1', name: '1'},
            {value: '2', name: '2'},
            {value: '3', name: '3'},
            {value: '4', name: '4'},
            {value: '5', name: '5'}
        ];
        return function (value) {
            for (var i = 0; i < levels.length; i++) {
                if (levels[i].value == value) {
                    return levels[i].name;
                }
            }
        };
    });
})(angular);
