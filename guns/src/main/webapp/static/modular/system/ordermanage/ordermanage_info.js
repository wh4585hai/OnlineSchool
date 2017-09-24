/**
 * 初始化订单管理详情对话框
 */
var OrdermanageInfoDlg = {
    ordermanageInfoData : {}
};

/**
 * 清除数据
 */
OrdermanageInfoDlg.clearData = function() {
    this.ordermanageInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrdermanageInfoDlg.set = function(key, val) {
    this.ordermanageInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrdermanageInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
OrdermanageInfoDlg.close = function() {
    parent.layer.close(window.parent.Ordermanage.layerIndex);
}

/**
 * 收集数据
 */
OrdermanageInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
OrdermanageInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ordermanage/add", function(data){
        Feng.success("添加成功!");
        window.parent.Ordermanage.table.refresh();
        OrdermanageInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ordermanageInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
OrdermanageInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ordermanage/update", function(data){
        Feng.success("修改成功!");
        window.parent.Ordermanage.table.refresh();
        OrdermanageInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ordermanageInfoData);
    ajax.start();
}

$(function() {

});
