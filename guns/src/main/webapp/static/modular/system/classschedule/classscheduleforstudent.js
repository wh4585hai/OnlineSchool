/**
 * 初始化课程表详情对话框
 */
var ClassscheduleInfoDlg = {
    classscheduleInfoData : {}
};

/**
 * 清除数据
 */
ClassscheduleInfoDlg.clearData = function() {
    this.classscheduleInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClassscheduleInfoDlg.set = function(key, val) {
    this.classscheduleInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ClassscheduleInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ClassscheduleInfoDlg.close = function() {
    parent.layer.close(window.parent.Classschedule.layerIndex);
}





/**
 * 提交添加
 */
ClassscheduleInfoDlg.search = function() {
	var teachername =$("#studentid").val();
	var classdatafrom =$("#classdatafrom").val();
	var classdatato =$("#classdatato").val();
	window.location=Feng.ctxPath + '/front/to_my_lesson.html?id='+teachername+'&datefrom='+classdatafrom+'&dateto='+classdatato; 
}




$(function() {
	var avatarUp = new $WebUpload("file");
    avatarUp.init();
	$("#teachername").val($("#teachernameValue").val());

	

});
