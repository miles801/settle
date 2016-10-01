(function (window, angular, $) {
    var app = angular.module('settle.report', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'settle.report.groupVip'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory, GroupVipParam) {
        var month = 1;
        var year = 0;
        // 参数：文交所
        $scope.companys = [{name: '全部'}];
        GroupVipParam.company(function (o) {
            $scope.companys.push.apply($scope.companys, o);
            $scope.company = o[0].value;
            $scope.initTab();
        });
        var initTree = function () {
            var setting = {
                view: {showIcon: false},
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        month = treeNode.month;
                        year = treeNode.year;
                        $scope.initTab();
                    }
                }
            };
            var now = new Date();
            month = now.getMonth() + 1;
            year = now.getFullYear();
            var data = [{id: 0, name: year + '年', children: [], open: true, year: year}];
            if (month < 3) {
                data.push({
                    id: -1,
                    name: (year - 1) + '年',
                    children: [],
                    open: true,
                    year: year - 1
                });
                data[1].children = [
                    {id: 100, name: 1 + '月', year: year - 1, month: 1},
                    {id: 101, name: 2 + '月', year: year - 1, month: 2},
                    {id: 102, name: 3 + '月', year: year - 1, month: 3},
                    {id: 103, name: 4 + '月', year: year - 1, month: 4},
                    {id: 104, name: 5 + '月', year: year - 1, month: 5},
                    {id: 105, name: 6 + '月', year: year - 1, month: 6},
                    {id: 106, name: 7 + '月', year: year - 1, month: 7},
                    {id: 107, name: 8 + '月', year: year - 1, month: 8},
                    {id: 108, name: 9 + '月', year: year - 1, month: 9},
                    {id: 109, name: 10 + '月', year: year - 1, month: 10},
                    {id: 110, name: 11 + '月', year: year - 1, month: 11},
                    {id: 111, name: 12 + '月', year: year - 1, month: 12}
                ];
            }
            for (var i = 1; i <= month; i++) {
                (function (value) {
                    data[0].children.push({id: value, name: value + '月', year: year, month: value});
                })(i);
            }
            var treeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
            treeObj.selectNode(treeObj.getNodeByTId("treeDemo_" + (month + 1)));
        };

        initTree();
        $scope.initTab = function () {
            var company = $scope.company;
            if (!company) {
                AlertFactory.warning('请选择文交所!');
                return;
            }
            // 删除之前的
            $('#tab').html('');
            $(window.parent.document.body).find('ul.nav-tabs').hide();
            var data = [
                {
                    title: '团队会员',
                    url: 'app/settle/report/groupVip/groupVip_list.jsp?month=' + month + '&year=' + year + '&company=' + company
                },
                {
                    title: '团队佣金',
                    url: 'app/settle/report/groupBonus/groupBonus_list.jsp?month=' + month + '&year=' + year + '&company=' + company
                },
                {
                    title: '会员活跃度',
                    url: 'app/settle/report/groupVip/groupVip_activity.jsp?month=' + month + '&year=' + year + '&company=' + company
                },
                {
                    title: '集合原始报表',
                    url: 'app/settle/report/groupVip/groupVip_all.jsp?month=' + month + '&year=' + year + '&company=' + company
                },
                {
                    title: '返佣',
                    url: 'app/settle/report/groupVip/groupVip_bonus.jsp?month=' + month + '&year=' + year + '&company=' + company
                },
                {title: '汇总', url: 'app/settle/report/groupVip/groupVip_all2.jsp?month=' + month + '&year=' + year}
            ];
            angular.forEach(data, function (o, i) {
                CommonUtils.addTab({
                    isRoot: i == 0,
                    title: o.title,
                    canClose: false,
                    active: i == 0,
                    targetObj: window.self,
                    targetElm: '#tab',
                    url: o.url
                });
            });
        };
    });

})(window, angular, jQuery);