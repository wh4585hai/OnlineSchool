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
	window.location=Feng.ctxPath + '/ordermanage';
}

/**
 * 收集数据
 */
OrdermanageInfoDlg.collectData = function() {
	debugger;
	
	var weeks = "";
	var timesperweek=0;
    $('input:checkbox[name=weeks]:checked').each(function(i){
     if(0==i){
    	 weeks = $(this).val();
     }else{
    	 weeks += (","+$(this).val());
    	
     }
     timesperweek = timesperweek+1;
    });
    var classnumber="";
    if($("#classapproach :checked").val()=="1"){
    	classnumber=$("#QQNumber").val();
		
	}else if($("#classapproach :checked").val()=="2"){
		
		classnumber=$("#skypNumber").val();
	} 
    
    var amount="";
    var months =$("#months").val();
    var coursetime =$("#coursetime").val();
    amount = months*timesperweek*coursetime*4;
    
    this.set('studentid');
    this.set('courseid');
    this.set('months');
    this.set('coursetime');
    this.set('materialid');
    this.set('date');
    this.set('starttime');
    this.set('classapproach');
    this.set('birthday');
    this.set('sex');
    this.ordermanageInfoData['amount'] = amount;
    this.ordermanageInfoData['classnumber'] = classnumber;
    this.ordermanageInfoData['weeks'] = weeks;
   
    
}

OrdermanageInfoDlg.collectDataManager = function() {
	debugger;
	
	var weeks = "";
	var timesperweek=0;
    $('input:checkbox[name=weeks]:checked').each(function(i){
     if(0==i){
    	 weeks = $(this).val();
     }else{
    	 weeks += (","+$(this).val());
    	
     }
     timesperweek = timesperweek+1;
    });
   
    var amount="";
    var months =$("#months").val();
    var coursetime =$("#coursetime").val();
    amount = months*timesperweek*coursetime*4;
    this.set('id');
    this.set('studentid');
    this.set('courseid');
    this.set('months');
    this.set('coursetime');
    this.set('materialid');
    this.set('date');
    this.set('starttime');
    this.set('classapproach');
    this.set('birthday');
    this.set('status');
    this.set('sex');
    this.set('classnumber');
    this.ordermanageInfoData['amount'] = amount; 
    this.ordermanageInfoData['weeks'] = weeks;
   
    
}
/**
 * 提交添加
 */
OrdermanageInfoDlg.addSubmit = function() {

	debugger;
	
    this.clearData();
    this.collectData();
    $("#amount").val(this.ordermanageInfoData.amount);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ordermanage/add	", function(data){
        Feng.success("预约成功!");
        $(".erweimaDiv").show();
        
        /*window.parent.Ordermanage.table.refresh();
        OrdermanageInfoDlg.close();*/
    },function(data){
        Feng.error("预约失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ordermanageInfoData);
    ajax.start();
}
/**
 * 提交添加
 */
OrdermanageInfoDlg.addSubmitManager = function() {

	debugger;
	
    this.clearData();
    this.collectDataManager();
   

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ordermanage/add	", function(data){
        Feng.success("保存成功!");
  
        window.location=Feng.ctxPath + '/ordermanage';

    },function(data){
        Feng.error("下单失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ordermanageInfoData);
    ajax.start();
}

OrdermanageInfoDlg.closePay = function() {
	   var id = $("#studentid").val();
	 window.location=Feng.ctxPath + '/front/to_my_order?id='+id;
}

OrdermanageInfoDlg.showPay = function(data) {
	debugger;
	 $("#amount").val(data);
	 $(".erweimaDiv").show();
	 
}
/**
 * 提交修改
 */
OrdermanageInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectDataManager();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ordermanage/update", function(data){
        Feng.success("修改成功!");
        window.location=Feng.ctxPath + '/ordermanage';
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ordermanageInfoData);
    ajax.start();
}

$(function() {
	
	debugger
	  $("#courseid").val($("#courseidValue").val());
	  $("#coursetime").val($("#coursetimeValue").val());
	  $("#materialid").val($("#materialidValue").val());
	  $("#classapproach").val($("#classapproachValue").val());
	  $("#sex").val($("#sexValue").val());
	  $("#status").val($("#statusValue").val());
	  var week = $("#weeksValue").val();
	  var weeks = week.split(',');	  	 		    
	  var boxes =  $('input:checkbox[name=weeks]');
	  for(i=0;i<boxes.length;i++){
		     for(j=0;j<weeks.length;j++){
		         if(boxes[i].value == weeks[j]){
		             boxes[i].checked = true;
		             break;
		         }
		     }
		 }
		
	 
	  

});
