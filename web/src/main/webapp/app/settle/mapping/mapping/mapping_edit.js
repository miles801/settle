/**
 * 映射编辑
 * Created by Michael .
 */
(function (window, angular, $) {
    var app = angular.module('settle.mapping.mapping.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'settle.mapping.mapping'
    ]);

    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, MappingService, MappingParam) {

        var pageType = $('#pageType').val();
        var id = $('#id').val();

        $scope.back = CommonUtils.back;


        // 参数：文交所
        $scope.companys = [{name: '请选择...'}];
        MappingParam.company(function (o) {
            $scope.companys.push.apply($scope.companys, o);
        });

        // 参数：表名称
        $scope.names = [{name: '请选择...'}];
        MappingParam.name(function (o) {
            $scope.names.push.apply($scope.names, o);
        });

        // 当文交所或者表变更时执行
        $scope.onNameChange = function () {
            var name = $scope.beans.name;
            $scope.tables = [];
            if (name) {
                MappingParam.tables(name, function (o) {
                    $scope.tables = o.data || [];
                    if ($scope.beans.content) {
                        var contents = $scope.beans.content.split('@');
                        angular.forEach($scope.tables, function (table) {
                            for (var i = 0; i < contents.length; i++) {
                                if (contents[i].indexOf(table.value) > -1) {
                                    table.index = parseInt(contents[i].substr(contents[i].indexOf(':') + 1));
                                    break;
                                }
                            }
                        });
                    }
                });
            }
        };

        // 保存
        $scope.save = function (createNew) {
            var names = [];
            var error = [];
            angular.forEach($scope.tables || [], function (o) {
                var index = o.index;
                if (o.description == 1 && !index) {
                    error.push('[' + o.name + ']是必填项!');
                    return;
                }
                if (!index) {
                    return;
                }

                names.push(o.value + ":" + index);
            });
            if (error.length > 0) {
                AlertFactory.error(error.join('   ;   '));
                return;
            }
            $scope.beans.content = names.join('@');
            var promise = MappingService.save($scope.beans, function (data) {
                AlertFactory.success('保存成功!');
                CommonUtils.addTab('update');
                if (createNew === true) {
                    $scope.beans = {};
                } else {
                    $scope.form.$setValidity('committed', false);
                    CommonUtils.delay($scope.back, 2000);
                }
                $scope.beans.content = null;    // 清空
            });
            CommonUtils.loading(promise);
        };


        // 更新
        $scope.update = function () {
            var names = [];
            var error = [];
            angular.forEach($scope.tables || [], function (o) {
                var index = o.index;
                if (o.description == 1 && !index) {
                    error.push('[' + o.name + ']是必填项!');
                    return;
                }
                if (!index) {
                    return;
                }
                names.push(o.value + ":" + index);
            });
            if (error.length > 0) {
                AlertFactory.error(error.join('   ;   '));
                return;
            }
            $scope.beans.content = names.join('@');
            var promise = MappingService.update($scope.beans, function (data) {
                AlertFactory.success('更新成功!');
                $scope.form.$setValidity('committed', false);
                CommonUtils.addTab('update');
                CommonUtils.delay($scope.back, 2000);
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 加载数据
        $scope.load = function (id, callback) {
            var promise = MappingService.get({id: id}, function (data) {
                $scope.beans = data.data || {};

                // 触发事件
                $scope.onNameChange();

                callback && callback();
            });
            CommonUtils.loading(promise, 'Loading...');
        };


        if (pageType == 'add') {
            $scope.beans = {};
        } else if (pageType == 'modify') {
            $scope.load(id);
        } else if (pageType == 'detail') {
            $scope.load(id);
            $('input,textarea,select').attr('disabled', 'disabled');
            $('span.add-on>.icons').remove();
        } else {
            AlertFactory.error($scope, '错误的页面类型');
        }
    });
})(window, angular, jQuery);