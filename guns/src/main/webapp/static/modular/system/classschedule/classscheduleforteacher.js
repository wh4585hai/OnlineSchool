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
         {title: 'Student Name', field: 'studentName',  align: 'center', valign: 'middle',sortable: true},
         {title: 'Teacher Name', field: 'teacherName',  align: 'center', valign: 'middle'},
         {title: 'Course Date', field: 'formatedate',  align: 'center', valign: 'middle',sortable: true},
         {title: 'Week', field: 'weekfordate',  align: 'center', valign: 'middle',sortable: true},
         {title: 'Start Time', field: 'starttime', align: 'center', valign: 'middle', sortable: true},
         {title: 'Course Time', field: 'courseTimeName',align: 'center', valign: 'middle'},
         {title: 'Material', field: 'meterialName',  align: 'center', valign: 'middle'},
         {title: 'Class Way', field: 'classapproach',  align: 'center', valign: 'middle'},
         {title: 'Contact Way', field: 'classnumber',  align: 'center', valign: 'middle'},
         {title: 'File', field: 'file',  align: 'center', valign: 'middle'},
         {title: 'Status', field: 'statusName', visible: true, align: 'center', valign: 'middle',sortable: true},
         {title: 'Delay', field: 'isdelayName', visible: true, align: 'center', valign: 'middle'},
         {title: 'Delay Reason', field: 'delayreason', visible: true, align: 'center', valign: 'middle'},
         {title: 'Course Comment', field: 'remark', visible: true, align: 'center', valign: 'middle'},
         {title: 'Students Feedback', field: 'comment', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Classschedule.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("please select a row in the table list！");
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
    var queryData = {};
    queryData['studentname'] = $("#studentname").val();
    queryData['datefrom'] = $("#classdatafrom").val();
    queryData['dateto'] = $("#classdatato").val();
    Classschedule.table.refresh({query: queryData});
};

/**
 * 打开查看课程表详情
 */
Classschedule.openClassscheduleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'Course Detail',
            area: ['800px', '600px'], //宽高
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
    table.setPaginationType("server");
    Classschedule.table = table.init();
});
