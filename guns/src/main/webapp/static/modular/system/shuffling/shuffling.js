/**
 * shuffling管理初始化
 */
var Shuffling = {
    id: "ShufflingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Shuffling.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '标题', field: 'title', align: 'center', valign: 'middle', sortable: true},
        {title: '图片', field: 'path', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'num', align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Shuffling.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Shuffling.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加shuffling
 */
Shuffling.openAddShuffling = function () {
    var index = layer.open({
        type: 2,
        title: '添加shuffling',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/shuffling/shuffling_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看shuffling详情
 */
Shuffling.openShufflingDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'shuffling详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/shuffling/shuffling_update/' + Shuffling.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除shuffling
 */
Shuffling.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/shuffling/delete", function (data) {
            Feng.success("删除成功!");
            Shuffling.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("shufflingId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询shuffling列表
 */
Shuffling.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Shuffling.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Shuffling.initColumn();
    var table = new BSTable(Shuffling.id, "/shuffling/list", defaultColunms);
    table.setPaginationType("client");
    Shuffling.table = table.init();
});
