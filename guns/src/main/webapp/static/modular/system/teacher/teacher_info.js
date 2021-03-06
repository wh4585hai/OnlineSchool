/**
 * 初始化teacher详情对话框
 */
var TeacherInfoDlg = {
    teacherInfoData : {},
    content: null
};

/**
 * 清除数据
 */
TeacherInfoDlg.clearData = function() {
    this.teacherInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeacherInfoDlg.set = function(key, val) {
    this.teacherInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeacherInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TeacherInfoDlg.close = function() {
	 window.location=Feng.ctxPath + '/teacher';
   // parent.layer.close(window.parent.Teacher.layerIndex);
}

/**
 * 收集数据
 */
TeacherInfoDlg.collectData = function() {
    this.set('id');
    this.set('age');
    this.set('userId');
    this.set('country');
    this.set('language');
    this.set('picture');
    this.set('qq');
    this.set('isshow');
    this.teacherInfoData['content'] =  CKEDITOR.instances.content.getData();
   // this.set('introduction');
    this.set('skype');
    this.set('birthday');
}

/**
 * 提交添加
 */
TeacherInfoDlg.addSubmit = function() {
	CKupdate();
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teacher/add", function(data){
        Feng.success("添加成功!");
        window.location=Feng.ctxPath + '/teacher';
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    console.log(this.teacherInfoData);
    ajax.set(this.teacherInfoData);
    ajax.start();
}
/**
 * 提交添加
 */
TeacherInfoDlg.upload = function() {
	var options = {
		    success: function(data) {
		    	 Feng.success("上传成功!");
		    	 window.location=Feng.ctxPath + '/teacher';   
		    },
		    error: function(data){
	    		 Feng.error("上传失败!" + data.responseJSON.message + "!");
	    		 window.location=Feng.ctxPath + '/teacher';
	    		}
	};
    $('#teacherUpload').ajaxSubmit(options); 
}
/**
 * 提交修改
 */
TeacherInfoDlg.editSubmit = function() {
	CKupdate();
	console.log(CKEDITOR.instances.content.getData())
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teacher/update", function(data){
        Feng.success("修改成功!");
        window.location=Feng.ctxPath + '/teacher';
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    console.log(this.teacherInfoData);
    ajax.set(this.teacherInfoData);
    ajax.start();
}
function CKupdate() {
    for (instance in CKEDITOR.instances)
        CKEDITOR.instances[instance].updateElement();
}

$(function() {
	 var avatarUp = new $WebUpload("picture");
	    avatarUp.init();
	//初始化性别选项
   $("#teacherid").val($("#teacheridvalue").val());
   $("#country").val($("#countryvalue").val());
   $("#language").val($("#languagevalue").val());
 //  $("#isshow").val($("#isshowvalue").val());
    
 //初始化是否是菜单
   if($("#isshowvalue").val() == undefined){
       $("#isshow").val(0);
   }else{
       $("#isshow").val($("#isshowvalue").val());
   }

});
