/**
 * 初始化部门详情对话框
 */
var StudentFrontInfoDlg = {
	studentFrontInfoData : {},
    validateFields: {
        account: {
            validators: {
                notEmpty: {
                    message: '账号不能为空'
                },
                stringLength: {
                    min: 6,
                    max: 30,
                    message: '用户名长度必须在6到30之间'
                },
                threshold :  6 ,
                remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                    url: 'existStudent.html',//验证地址
                    message: '用户已存在',//提示消息
                    delay :  500,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                    type: 'POST'//请求方式
            
                    /**自定义提交数据，默认值提交当前input value
                     *  data: function(validator) {
                          return {
                              password: $('[name="passwordNameAttributeInYourForm"]').val(),
                              whatever: $('[name="whateverNameAttributeInYourForm"]').val()
                          };
                       }
                     */
                },
                regexp: {
                    regexp: /^[a-zA-Z0-9_\.@]+$/,
                    message: '用户名由数字字母下划线和.组成'
                }
            }
        },
        password: {
        	message: '密码无效',
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                stringLength: {
                    min: 6,
                    max: 30,
                    message: '密码长度必须在6到30之间'
                },
                identical: {//相同
                    field: 'password', //需要进行比较的input name值
                    message: '两次密码不一致'
                },
                different: {//不能和用户名相同
                    field: 'account',//需要进行比较的input name值
                    message: '不能和用户名相同'
                },
                regexp: {
                    regexp: /^[a-zA-Z0-9_\.]+$/,
                    message: '只能使用数字英文下划线和.的字符串组合'
                }
            }
        },
        repassword: {
            message: '密码无效',
            validators: {
                notEmpty: {
                    message: '确认密码不能为空'
                },
                stringLength: {
                    min: 6,
                    max: 30,
                    message: '密码长度必须在6到30之间'
                },
                identical: {//相同
                    field: 'password',
                    message: '两次密码不一致'
                },
                different: {//不能和用户名相同
                    field: 'account',
                    message: '不能和用户名相同'
                },
                regexp: {//匹配规则
                    regexp: /^[a-zA-Z0-9_\.]+$/,
                    message: '只能使用数字英文下划线和.的字符串组合'
                }
            }
        },
        nickName: {
        	message: '昵称无效',
            validators: {
                notEmpty: {
                    message: '昵称不能为空'
                },
        regexp: {//匹配规则
            regexp: /^[a-zA-Z 0-9]+$/,
            message: '昵称请填写英文'
        }
        }
        },   
        realName: {
            validators: {
                notEmpty: {
                    message: '真实名字不能为空'
                }
            }
        },
        contact: {
            validators: {
                notEmpty: {
                    message: 'qq或skype不能为空'
                }
            }
        }
    }
};
/**
 * 清除数据
 */
StudentFrontInfoDlg.clearData = function () {
    this.studentFrontInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StudentFrontInfoDlg.set = function (key, val) {
    this.studentFrontInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StudentFrontInfoDlg.get = function (key) {
    return $("#" + key).val();
};
StudentFrontInfoDlg.collectData = function () {
    this.set('id').set('account').set('password')
        .set('nickname').set('realname').set('contact');
    if($("input[name='contact_mode']:checked").val()=="qq"){
    	$("#qq").val($("#contact").val());
    }else{
    	$("#skype").val($("#contact").val());
    }
    this.set('qq').set('skype');
};
/**
 * 验证数据是否为空
 */
StudentFrontInfoDlg.validate = function () {
   // $('#studentInfoForm').data("bootstrapValidator").resetForm();
    $('#studentInfoForm').bootstrapValidator('validate');
    return $("#studentInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
StudentFrontInfoDlg.addSubmit = function() {
	if(!$('#agreelicense').is(':checked')) {
		alert("请同意服务协议");
		return;
	    // do something
	}
    this.clearData();
    this.collectData();
    
    
	if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/front/registerStudnet", function(data){
        Feng.success("注册成功!3秒后自动跳转");
        setTimeout(function(){
        	window.location=Feng.ctxPath + "/front/login_s.html";
        },3000);//延时3秒 
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.studentFrontInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StudentFrontInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/student/update", function(data){
        Feng.success("修改成功!");
        window.parent.Student.table.refresh();
        StudentInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.studentInfoData);
    ajax.start();
}

$(function() {
	 Feng.initValidator("studentInfoForm", StudentFrontInfoDlg.validateFields);
});
