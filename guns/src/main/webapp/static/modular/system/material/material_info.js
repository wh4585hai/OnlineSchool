/**
 * 初始化material详情对话框
 */
var MaterialInfoDlg = {
    materialInfoData : {}
};

/**
 * 清除数据
 */
MaterialInfoDlg.clearData = function() {
    this.materialInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MaterialInfoDlg.set = function(key, val) {
    this.materialInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MaterialInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MaterialInfoDlg.close = function() {
    parent.layer.close(window.parent.Material.layerIndex);
}

/**
 * 收集数据
 */
MaterialInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
MaterialInfoDlg.addSubmit = function() {
//
//    this.clearData();
//    this.collectData();
//
//    //提交信息
//    var ajax = new $ax(Feng.ctxPath + "/material/add", function(data){
//        Feng.success("添加成功!");
//        window.parent.Material.table.refresh();
//        MaterialInfoDlg.close();
//    },function(data){
//        Feng.error("添加失败!" + data.responseJSON.message + "!");
//    });
//    ajax.set(this.materialInfoData);
//    ajax.start();
	CKupdate();
	var options = {
		    success: function(data) {
		    	 Feng.success("添加成功!");
		    	 window.location=Feng.ctxPath + '/material';
		    },
		    error: function(data){
	    		 Feng.error("添加失败!" + data.responseJSON.message + "!");
	    		}
	};
    $('#materialAdd').ajaxSubmit(options);  
}

/**
 * 提交修改
 */
MaterialInfoDlg.editSubmit = function() {
	CKupdate();
	console.log($("#content").val())
	var options = {
		    success: function(data) {
		    	 Feng.success("修改成功!");
		    	 window.location=Feng.ctxPath + '/material';
		    },
		    error: function(data){
	    		 Feng.error("修改失败!" + data.responseJSON.message + "!");
	    		}
	};
    $('#materialUpdate').ajaxSubmit(options);  

//    this.clearData();
//    this.collectData();
//
//    //提交信息
//    var ajax = new $ax(Feng.ctxPath + "/material/update", function(data){
//        Feng.success("修改成功!");
//        window.parent.Material.table.refresh();
//        MaterialInfoDlg.close();
//    },function(data){
//        Feng.error("修改失败!" + data.responseJSON.message + "!");
//    });
//    ajax.set(this.materialInfoData);
//    ajax.start();
}
function CKupdate() {
    for (instance in CKEDITOR.instances)
        CKEDITOR.instances[instance].updateElement();
}
$(function() {
	 CKEDITOR.replace('content');
});
