/**
 * 初始化course详情对话框
 */
var CourseInfoDlg = {
    courseInfoData : {},
    content: null,
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                }
            }
        }
    }
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
	 window.location=Feng.ctxPath + '/course';
//    parent.layer.close(window.parent.Course.layerIndex);
}
/**
 * 验证数据是否为空
 */
CourseInfoDlg.validate = function () {
    $('#courseInfoForm').data("bootstrapValidator").resetForm();
    $('#courseInfoForm').bootstrapValidator('validate');
    return $("#courseInfoForm").data('bootstrapValidator').isValid();
};
/**
 * 收集数据
 */
CourseInfoDlg.collectData = function() {
    this.set('id');
    this.set('title');
    this.courseInfoData['content'] =  CKEDITOR.instances.content.getData();
    if ($("#isHome").is(":checked")) {
    	  this.courseInfoData['isHome']=1;
    }else{
    	  this.courseInfoData['isHome']=0;
    }
   // this.set('isHome');
}

/**
 * 提交添加
 */
CourseInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/course/add", function(data){
        Feng.success("添加成功!");
//        window.parent.Course.table.refresh();
      //  CourseInfoDlg.close();
        window.location=Feng.ctxPath + '/course';
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    console.log(this.courseInfoData)
    ajax.set(this.courseInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CourseInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/course/update", function(data){
        Feng.success("修改成功!");
        window.location=Feng.ctxPath + '/course';
//        window.parent.Course.table.refresh();
//        CourseInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseInfoData);
    ajax.start();
}

$(function() {
	 Feng.initValidator("courseInfoForm", CourseInfoDlg.validateFields);

});
