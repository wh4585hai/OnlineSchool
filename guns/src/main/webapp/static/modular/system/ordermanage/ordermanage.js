/**
 * 订单管理管理初始化
 */
var Ordermanage = {
    id: "OrdermanageTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Ordermanage.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Ordermanage.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Ordermanage.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加订单管理
 */
Ordermanage.openAddOrdermanage = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ordermanage/ordermanage_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看订单管理详情
 */
Ordermanage.openOrdermanageDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ordermanage/ordermanage_update/' + Ordermanage.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除订单管理
 */
Ordermanage.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/ordermanage/delete", function (data) {
            Feng.success("删除成功!");
            Ordermanage.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ordermanageId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询订单管理列表
 */
Ordermanage.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Ordermanage.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Ordermanage.initColumn();
    var table = new BSTable(Ordermanage.id, "/ordermanage/list", defaultColunms);
    table.setPaginationType("client");
    Ordermanage.table = table.init();
});
