/**
 * 课程表管理初始化
 */
var Classschedule = {
    id: "ClassscheduleTable",	//表格id
    seItem: null,		//选中的条目
    seItems: null,
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Classschedule.initColumn = function () {
    return [
        {field: 'selectItem', radio: false},
        {title: 'id', field: 'id',visible: false, align: 'center', valign: 'middle'},
        {title: '学生', field: 'studentName',  align: 'center', valign: 'middle',sortable: true},
        {title: '教师', field: 'teacherName',  align: 'center', valign: 'middle'},
        {title: '上课日期', field: 'formatedate',  align: 'center', valign: 'middle',sortable: true},
        {title: '星期', field: 'weekfordate',  align: 'center', valign: 'middle',sortable: true},
        {title: '开始时间', field: 'starttime', align: 'center', valign: 'middle', sortable: true},
        {title: '课程时长', field: 'courseTimeName',align: 'center', valign: 'middle'},
        {title: '教材', field: 'meterialName',  align: 'center', valign: 'middle'},
        {title: '上课方式', field: 'classapproach',  align: 'center', valign: 'middle'},
        {title: '联系方式', field: 'classnumber',  align: 'center', valign: 'middle'},
        {title: '文件', field: 'file',  align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle',sortable: true},
        {title: '时否延期', field: 'isdelayName', visible: true, align: 'center', valign: 'middle'},
        {title: '延期原因', field: 'delayreason', visible: true, align: 'center', valign: 'middle'},
        {title: '课评', field: 'remark', visible: true, align: 'center', valign: 'middle'},
        {title: '学生反馈', field: 'comment', visible: true, align: 'center', valign: 'middle'}
        
     
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
    	var str="";
    	for(i=0;i<selected.length;i++){
    		if(i==selected.length-1){
    			str+=selected[i].id;
    		}else{
    			str+=selected[i].id+",";
    		}
    	}
    	Classschedule.seItems = str;
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
    queryData['teachername'] = $("#teachername").val();
    queryData['datefrom'] = $("#classdatafrom").val();
    queryData['dateto'] = $("#classdatato").val();
    Classschedule.table.refresh({query: queryData});
};

/**
 * 点击添加课程表
 */
Classschedule.openAddClassschedule = function () {
    var index = layer.open({
        type: 2,
        title: '添加课程表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/classschedule/classschedule_add'
    });
    this.layerIndex = index;
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
            content: Feng.ctxPath + '/classschedule/classschedule_update/' + Classschedule.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除课程表
 */
Classschedule.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/classschedule/delete", function (data) {
            Feng.success("删除成功!");
            Classschedule.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("classscheduleIds",this.seItems);
        ajax.set("classscheduleId",this.seItem.id);
        ajax.start();
    }
};



$(function () {

    var defaultColunms = Classschedule.initColumn();
    var table = new BSTable(Classschedule.id, "/classschedule/list", defaultColunms);
    table.setPaginationType("server");

    Classschedule.table = table.init();
});
