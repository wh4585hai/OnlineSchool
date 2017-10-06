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
         {title: '学生', field: 'studentName',  align: 'center', valign: 'middle',sortable: true},
         {title: '教师', field: 'teacherName',  align: 'center', valign: 'middle'},
         {title: '上课日期', field: 'date',  align: 'center', valign: 'middle'},
         {title: '开始时间', field: 'starttime', align: 'center', valign: 'middle', sortable: true},
         {title: '课程时长', field: 'courseTimeName',align: 'center', valign: 'middle'},
         {title: '教材', field: 'meterialName',  align: 'center', valign: 'middle'},
         {title: '上课方式', field: 'classapproach',  align: 'center', valign: 'middle'},
         {title: '联系方式', field: 'classnumber',  align: 'center', valign: 'middle'},
         {title: '文件', field: 'file',  align: 'center', valign: 'middle'},
         {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
         {title: '时否延期', field: 'isdelayName', visible: true, align: 'center', valign: 'middle'},
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
    queryData['studentid'] = $("#studentid").val();
    queryData['datefrom'] = $("#classdatafrom").val();
    queryData['dateto'] = $("#classdatato").val();
    Classschedule.table.refresh({query: queryData});
};



$(function () {
    var defaultColunms = Classschedule.initColumn();
    var table = new BSTable(Classschedule.id, "/classschedule/listforstudent", defaultColunms);
    table.setPaginationType("client");
    Classschedule.table = table.init();
});
