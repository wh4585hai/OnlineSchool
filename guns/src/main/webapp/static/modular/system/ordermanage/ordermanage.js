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
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '学生姓名', field: 'studentName', visible: true, align: 'center', valign: 'middle'},
        {title: '教材名称', field: 'meterialName', visible: true, align: 'center', valign: 'middle'},
        {title: '课程名称', field: 'courseName', visible: true, align: 'center', valign: 'middle'},
        {title: '购买期限', field: 'months', visible: true, align: 'center', valign: 'middle'},
        {title: '课程时长', field: 'courseTimeName', visible: true, align: 'center', valign: 'middle'},
        {title: '开始日期', field: 'date', visible: true, align: 'center', valign: 'middle'},
        {title: '开始时间', field: 'starttime', visible: true, align: 'center', valign: 'middle'},
        {title: '上课星期', field: 'weeks', visible: true, align: 'center', valign: 'middle'},
        {title: '订单金额', field: 'amount', visible: true, align: 'center', valign: 'middle'},
        {title: '创建日期', field: 'createdate', visible: true, align: 'center', valign: 'middle'},
        {title: '上课方式', field: 'classapproachName', visible: true, align: 'center', valign: 'middle'},
        {title: '上课号码', field: 'classnumber', visible: true, align: 'center', valign: 'middle'},
        {title: '生日', field: 'birthday', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'sexName', visible: true, align: 'center', valign: 'middle'},
        {title: '支付状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'}
        
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
	window.location=Feng.ctxPath + '/ordermanage/ordermanage_add';
   
};

/**
 * 打开查看订单管理详情
 */
Ordermanage.openOrdermanageDetail = function () {
	
	if (this.check()) {
    	window.location=Feng.ctxPath + '/ordermanage/ordermanage_update/' + Ordermanage.seItem.id;
       
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
        ajax.set("id",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询订单管理列表
 */
Ordermanage.search = function () {
    var queryData = {};
    queryData['studentid'] = $("#studentid").val();
    queryData['date'] = $("#date").val();
    Ordermanage.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Ordermanage.initColumn();
    var table = new BSTable(Ordermanage.id, "/ordermanage/list", defaultColunms);
    table.setPaginationType("client");
    Ordermanage.table = table.init();
});
