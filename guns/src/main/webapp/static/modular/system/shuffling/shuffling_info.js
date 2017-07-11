/**
 * 初始化shuffling详情对话框
 */
var ShufflingInfoDlg = {
    shufflingInfoData : {}
};

/**
 * 清除数据
 */
ShufflingInfoDlg.clearData = function() {
    this.shufflingInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShufflingInfoDlg.set = function(key, val) {
    this.shufflingInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShufflingInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ShufflingInfoDlg.close = function() {
    parent.layer.close(window.parent.Shuffling.layerIndex);
}

/**
 * 收集数据
 */
ShufflingInfoDlg.collectData = function() {
    this.set('id').set('title').set('num').set('path');
}

/**
 * 提交添加
 */
ShufflingInfoDlg.addSubmit = function() {
//
//    this.clearData();
//    this.collectData();
	alert(1)
	var options = {
		    success: function(data) {
		    	 Feng.success("添加成功!");
	    	        window.parent.Shuffling.table.refresh();
	    	        ShufflingInfoDlg.close();    
		    },
		    error: function(data){
	    		 Feng.error("添加失败!" + data.responseJSON.message + "!");
	    		}
	};
    $('#shufflingAdd').ajaxSubmit(options);  
    alert(2)
//    //提交信息
//    var ajax = new $ax(Feng.ctxPath + "/shuffling/add", function(data){
//        Feng.success("添加成功!");
//        window.parent.Shuffling.table.refresh();
//        ShufflingInfoDlg.close();
//    },function(data){
//        Feng.error("添加失败!" + data.responseJSON.message + "!");
//    });
//    ajax.set(this.shufflingInfoData);
//    ajax.start();
}

/**
 * 提交修改
 */
ShufflingInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/shuffling/update", function(data){
        Feng.success("修改成功!");
        window.parent.Shuffling.table.refresh();
        ShufflingInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.shufflingInfoData);
    ajax.start();
}

$(function() {
   
});
