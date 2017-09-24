/**
 * 初始化部门详情对话框
 */
var StudentResetPWDFrontInfoDlg = {
	studentResetPWDFrontInfoData : {},
    validateFields: {
    	oldpassword: {
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
            regexp: {
                regexp: /^[a-zA-Z0-9_\.]+$/,
                message: '只能使用数字英文下划线和.的字符串组合'
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
                regexp: {//匹配规则
                    regexp: /^[a-zA-Z0-9_\.]+$/,
                    message: '只能使用数字英文下划线和.的字符串组合'
                }
            }
        }
    }
};
/**
 * 清除数据
 */
StudentResetPWDFrontInfoDlg.clearData = function () {
    this.studentResetPWDFrontInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StudentResetPWDFrontInfoDlg.set = function (key, val) {
    this.studentResetPWDFrontInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StudentResetPWDFrontInfoDlg.get = function (key) {
    return $("#" + key).val();
};
StudentResetPWDFrontInfoDlg.collectData = function () {
    this.set('oldpassword').set('password')
        .set('id');
};
/**
 * 验证数据是否为空
 */
StudentResetPWDFrontInfoDlg.validate = function () {
   // $('#studentInfoForm').data("bootstrapValidator").resetForm();
    $('#studentResetPWDInfoForm').bootstrapValidator('validate');
    return $("#studentResetPWDInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
StudentResetPWDFrontInfoDlg.modifyPWD = function() {
    this.clearData();
    this.collectData();
    
    
	if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/front/resetPWD", function(data){
        Feng.success("修改成功!");
        window.location=Feng.ctxPath + "/front/login_s.html";
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.studentResetPWDFrontInfoData);
    ajax.start();
}


$(function() {
	 Feng.initValidator("studentResetPWDInfoForm", StudentResetPWDFrontInfoDlg.validateFields);
});
