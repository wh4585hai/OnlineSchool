/**
 * material管理初始化
 */
var Material = {
    id: "MaterialTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Material.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '教材名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '教材文件', field: 'path', align: 'center', valign: 'middle', sortable: true},
    ];
};

/**
 * 检查是否选中
 */
Material.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Material.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加material
 */
Material.openAddMaterial = function () {
    var index = layer.open({
        type: 2,
        title: '添加material',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/material/material_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看material详情
 */
Material.openMaterialDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'material详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/material/material_update/' + Material.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除material
 */
Material.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/material/delete", function (data) {
            Feng.success("删除成功!");
            Material.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("materialId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询material列表
 */
Material.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Material.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Material.initColumn();
    var table = new BSTable(Material.id, "/material/list", defaultColunms);
    table.setPaginationType("client");
    Material.table = table.init();
});
