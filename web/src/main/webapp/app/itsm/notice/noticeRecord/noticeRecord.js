/**
 * 公告阅读记录
 * Created by Michael .
 */
(function (angular) {
    var app = angular.module('itsm.notice.noticeRecord', [
        'ngResource',
        'base.param',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('NoticeRecordService', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/itsm/notice/noticeRecord/:method'), {}, {
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
                params: {
                    method: 'pageQuery',
                    limit: '@limit',
                    start: '@start',
                    orderBy: '@orderBy',
                    reverse: '@reverse'
                },
                isArray: false
            },

            // 根据id字符串（使用逗号分隔多个值）
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });

    app.service('NoticeRecordModal', function ($modal, CommonUtils, AlertFactory, ModalFactory, NoticeRecordService) {
        return {
            /**
             * 查看阅读记录
             * @param id 文件ID
             */
            readHistory: function (id) {
                if (!id) {
                    AlertFactory.error('公告ID不能为空!');
                    return;
                }
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/itsm/notice/noticeRecord/modal-noticeRecord.ftl.html'),
                    backdrop: 'static'
                });
                var $scope = modal.$scope;
                $scope.condition = {
                    notice: id,
                    hasRead: true,
                    orderBy: 'readTime',
                    reverse: true
                };

                $scope.query = function () {
                    $scope.pager.query();
                };


                $scope.pager = {
                    limit: 10,
                    fetch: function () {
                        var param = angular.extend({start: this.start, limit: this.limit}, $scope.condition);
                        return CommonUtils.promise(function (defer) {
                            var promise = NoticeRecordService.pageQuery(param, function (data) {
                                param = null;
                                $scope.beans = data.data || {total: 0};
                                defer.resolve($scope.beans);
                            });
                            CommonUtils.loading(promise, 'Loading...');
                        });
                    },
                    finishInit: function () {
                        this.query();
                    }
                };

            }
        }

    });

    app.service('NoticeRecordParam', function (ParameterLoader) {
        var o = {};
        return o;
    });

})(angular);
