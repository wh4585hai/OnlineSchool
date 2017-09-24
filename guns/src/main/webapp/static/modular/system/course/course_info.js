/**
 * 初始化Course详情对话框
 */
var CourseInfoDlg = {
    courseInfoData : {}
};

/**
 * 清除数据
 */
CourseInfoDlg.clearData = function() {
    this.courseInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseInfoDlg.set = function(key, val) {
    this.courseInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CourseInfoDlg.close = function() {
    parent.layer.close(window.parent.Course.layerIndex);
}

/**
 * 收集数据
 */
CourseInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
CourseInfoDlg.addSubmit = function() {
//
//    this.clearData();
//    this.collectData();
//
//    //提交信息
//    var ajax = new $ax(Feng.ctxPath + "/Course/add", function(data){
//        Feng.success("添加成功!");
//        window.parent.Course.table.refresh();
//        CourseInfoDlg.close();
//    },function(data){
//        Feng.error("添加失败!" + data.responseJSON.message + "!");
//    });
//    ajax.set(this.CourseInfoData);
//    ajax.start();
	CKupdate();
	var options = {
		    success: function(data) {
		    	 Feng.success("添加成功!");
		    	 window.location=Feng.ctxPath + '/course';
		    },
		    error: function(data){
	    		 Feng.error("添加失败!" + data.responseJSON.message + "!");
	    		}
	};
    $('#courseAdd').ajaxSubmit(options);  
}

/**
 * 提交修改
 */
CourseInfoDlg.editSubmit = function() {
	CKupdate();
	var options = {
		    success: function(data) {
		    	 Feng.success("修改成功!");
		    	 window.location=Feng.ctxPath + '/course';
		    },
		    error: function(data){
	    		 Feng.error("修改失败!" + data.responseJSON.message + "!");
	    		}
	};
    $('#courseUpdate').ajaxSubmit(options);  

//    this.clearData();
//    this.collectData();
//
//    //提交信息
//    var ajax = new $ax(Feng.ctxPath + "/Course/update", function(data){
//        Feng.success("修改成功!");
//        window.parent.Course.table.refresh();
//        CourseInfoDlg.close();
//    },function(data){
//        Feng.error("修改失败!" + data.responseJSON.message + "!");
//    });
//    ajax.set(this.CourseInfoData);
//    ajax.start();
}
function CKupdate() {
    for (instance in CKEDITOR.instances)
        CKEDITOR.instances[instance].updateElement();
}
$(function() {
	 CKEDITOR.replace('content');
});
