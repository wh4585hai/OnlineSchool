/**
 * course管理初始化
 */
var Course = {
    id: "CourseTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Course.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '标题', field: 'title', align: 'center', valign: 'middle', sortable: true},
        {title: '内容', field: 'content', align: 'center', valign: 'middle', sortable: true},
        {title: '发布者', field: 'createrName', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Course.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Course.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加course
 */
Course.openAddCourse = function () {
	window.location=Feng.ctxPath + '/course/course_add';
//    var index = layer.open({
//        type: 2,
//        title: '添加course',
//        area: ['800px', '420px'], //宽高
//        fix: false, //不固定
//       // maxmin: true,
//        iframe : {
//			src : Feng.ctxPath + '/course/course_add'
//		}
////        content: Feng.ctxPath + '/course/course_add'
//    });
//    this.layerIndex = index;
};

/**
 * 打开查看course详情
 */
Course.openCourseDetail = function () {
	 if (this.check()) {
	window.location=Feng.ctxPath + '/course/course_update/'+Course.seItem.id;
	}
//    if (this.check()) {
//        var index = layer.open({
//            type: 2,
//            title: 'course详情',
//            area: ['800px', '420px'], //宽高
//            fix: false, //不固定
//            maxmin: true,
//            content: Feng.ctxPath + '/course/course_update/' + Course.seItem.id
//        });
//        this.layerIndex = index;
//    }
};

/**
 * 删除course
 */
Course.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/course/delete", function (data) {
            Feng.success("删除成功!");
            Course.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("courseId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询course列表
 */
Course.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Course.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Course.initColumn();
    var table = new BSTable(Course.id, "/course/list", defaultColunms);
    table.setPaginationType("client");
    Course.table = table.init();
});
