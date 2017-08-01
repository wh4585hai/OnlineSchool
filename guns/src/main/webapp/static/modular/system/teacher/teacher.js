/**
 * teacher管理初始化
 */
var Teacher = {
    id: "TeacherTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Teacher.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: 'name', field: 'name',  align: 'center', valign: 'middle'},
        {title: 'country', field: 'country',  align: 'center', valign: 'middle'},
        {title: 'language', field: 'language',  align: 'center', valign: 'middle'},
        {title: 'picture', field: 'picture',  align: 'center', valign: 'middle'},
        {title: 'age', field: 'age',  align: 'center', valign: 'middle'},
        {title: 'introduction', field: 'introduction',  align: 'center', valign: 'middle'},
        {title: 'phone', field: 'phone',  align: 'center', valign: 'middle'},
        {title: 'email', field: 'email',  align: 'center', valign: 'middle'},
        {title: 'isshow', field: 'isshow',  align: 'center', valign: 'middle'},
        {title: 'qq', field: 'qq',  align: 'center', valign: 'middle'},
        {title: 'skype', field: 'skype',  align: 'center', valign: 'middle'},
        {title: 'createdate', field: 'createdate',  align: 'center', valign: 'middle'},
        {title: 'updatedate', field: 'updatedate',  align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Teacher.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Teacher.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加teacher
 */
Teacher.openAddTeacher = function () {
	
	window.location=Feng.ctxPath + '/teacher/teacher_add';
/*    var index = layer.open({
        type: 2,
        title: '添加teacher',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/teacher/teacher_add'
    });
    this.layerIndex = index;*/
};

/**
 * 打开查看teacher详情
 */
Teacher.openTeacherDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'teacher详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teacher/teacher_update/' + Teacher.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除teacher
 */
Teacher.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/teacher/delete", function (data) {
            Feng.success("删除成功!");
            Teacher.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("teacherId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询teacher列表
 */
Teacher.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Teacher.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Teacher.initColumn();
    var table = new BSTable(Teacher.id, "/teacher/list", defaultColunms);
    table.setPaginationType("client");
    Teacher.table = table.init();
});
