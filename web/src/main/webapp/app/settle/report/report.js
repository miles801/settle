(function (window, angular, $) {
    var app = angular.module('settle.report', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, AlertFactory, ModalFactory) {
        var month = 1;
        var initTree = function () {
            var setting = {
                view: {showIcon: false},
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        month = treeId;
                        initTab();
                    }
                }
            };
            var now = new Date();
            month = now.getMonth() + 1;
            var data = [];
            for (var i = 1; i <= month; i++) {
                (function (value) {
                    data.push({id: value, name: value + '月'})
                })(i);
            }
            var treeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
            treeObj.selectNode(treeObj.getNodes()[month - 1]);
        };

        initTree();
        var initTab = function () {
            // 删除之前的
            $('#tab').html('');
            $(window.parent.document.body).find('ul.nav-tabs').hide();
            var data = [
                {title: '团队会员', url: 'app/settle/report/groupVip/groupVip_list.jsp?month=' + month},
                {title: '团队佣金', url: 'app/settle/report/groupBonus/groupBonus_list.jsp?month=' + month},
                {title: '会员活跃度', url: 'app/settle/report/businessLog/businessLog_list.jsp?month=' + month},
                {title: '集合原始报表', url: 'app/settle/report/analysis/analysis_list.jsp?month=' + month},
                {title: '返佣', url: 'app/settle/report/groupVip/groupVip_list.jsp?month=' + month},
                {title: '汇总', url: 'app/settle/report/groupVip/groupVip_list.jsp?month=' + month}
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

        initTab();
    });

})(window, angular, jQuery);