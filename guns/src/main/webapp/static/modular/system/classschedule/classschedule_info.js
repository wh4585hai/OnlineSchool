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
 * 收集数据
 */
ClassscheduleInfoDlg.collectData = function() {
	debugger;
	var weeks = "";
    $('input:checkbox[name=weeks]:checked').each(function(i){
     if(0==i){
    	 weeks = $(this).val();
     }else{
    	 weeks += (","+$(this).val());
     }
    });
    this.set('studentid');
    this.set('teacherid');
    this.set('date');
    this.set('orderid');
    this.set('coursetime');
    this.set('materialid');
    this.set('starttime');
    this.set('months');
    this.classscheduleInfoData['weeks'] = weeks;
    
   
}

/**
 * 收集修改数据
 */
ClassscheduleInfoDlg.collectUpdateData = function() {
	this.set('id');
    this.set('studentid');
    this.set('teacherid');
    this.set('orderid');
    this.set('date');
    this.set('coursetime');
    this.set('materialid');
    this.set('starttime');
    this.set('status');
    this.set('isdelay');
    this.set('remark');
    this.set('delayreason');
  
    
   
}

/**
 * 提交添加
 */
ClassscheduleInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    console.log(this.classscheduleInfoData);
    
  //提交信息
    var ajax = new $ax(Feng.ctxPath + "/classschedule/add", function(data){
        Feng.success("添加成功!");
        window.location=Feng.ctxPath + '/classschedule/manager';
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    console.log(this.classscheduleInfoData);
    ajax.set(this.classscheduleInfoData);
    ajax.start();

/*    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/classschedule/add", function(data){
        Feng.success("添加成功!");
        window.parent.Classschedule.table.refresh();
        ClassscheduleInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.classscheduleInfoData);
    ajax.start();*/
}

/**
 * 提交修改
 */
ClassscheduleInfoDlg.editSubmit = function() {
	debugger;

    this.clearData();
    this.collectUpdateData();
    console.log(this.classscheduleInfoData);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/classschedule/update", function(data){
        Feng.success("修改成功!");
        window.parent.Classschedule.table.refresh();
        ClassscheduleInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.classscheduleInfoData);
    ajax.start();
    
}

/**
 * 签到修改
 */
ClassscheduleInfoDlg.checkInSubmit = function() {
	debugger;

    this.clearData();
    this.collectUpdateData();
    console.log(this.classscheduleInfoData);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/classschedule/checkin", function(data){
        Feng.success("修改成功!");
        window.parent.Classschedule.table.refresh();
        ClassscheduleInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.classscheduleInfoData);
    ajax.start();
    
}

$(function() {
	var avatarUp = new $WebUpload("file");
    avatarUp.init();
	$("#teacherid").val($("#teacheridvalue").val());
	$("#coursetime").val($("#coursetimevalue").val());
	$("#materialid").val($("#materialidvalue").val());
	$("#status").val($("#statusvalue").val());
	$("#isdelay").val($("#isdelayvalue").val());
	

});
