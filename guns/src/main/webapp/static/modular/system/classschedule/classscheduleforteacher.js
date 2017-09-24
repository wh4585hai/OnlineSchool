/**
 * 课程表管理初始化
 */
var Classschedule = {
    id: "ClassscheduleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Classschedule.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '学生', field: 'studentid',  align: 'center', valign: 'middle',sortable: true},
        {title: '教师', field: 'teacherid',  align: 'center', valign: 'middle'},
        {title: '上课日期', field: 'date',  align: 'center', valign: 'middle'},
        {title: '开始时间', field: 'starttime', align: 'center', valign: 'middle', sortable: true},
        {title: '课程时长', field: 'coursetime',  visible: false,align: 'center', valign: 'middle'},
        {title: '教材', field: 'materialid',  align: 'center', valign: 'middle'},
        {title: '文件', field: 'file',  align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
        {title: '是都延期', field: 'isdelay', visible: true, align: 'center', valign: 'middle'},
        {title: '延期原因', field: 'delayreason', visible: true, align: 'center', valign: 'middle'},
        {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Classschedule.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Classschedule.seItem = selected[0];
        return true;
    }
};


/**
 * 查询课程表列表
 */
Classschedule.search = function () {
	debugger;
    var queryData = {};
    queryData['studentname'] = $("#studentname").val();
    queryData['classdata'] = $("#classdata").val();
    Classschedule.table.refresh({query: queryData});
};

/**
 * 打开查看课程表详情
 */
Classschedule.openClassscheduleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '课程表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/classschedule/classschedule_checkin/' + Classschedule.seItem.id
        });
        this.layerIndex = index;
    }
};


$(function () {
    var defaultColunms = Classschedule.initColumn();
    var table = new BSTable(Classschedule.id, "/classschedule/listforteacher", defaultColunms);
    table.setPaginationType("client");
    Classschedule.table = table.init();
});