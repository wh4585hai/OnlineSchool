package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 订单管理控制器
 *
 * @author fengshuonan
 * @Date 2017-09-20 20:11:35
 */
@Controller
@RequestMapping("/ordermanage")
public class OrdermanageController extends BaseController {

    private String PREFIX = "/system/ordermanage/";

    /**
     * 跳转到订单管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "ordermanage.html";
    }

    /**
     * 跳转到添加订单管理
     */
    @RequestMapping("/ordermanage_add")
    public String ordermanageAdd() {
        return PREFIX + "ordermanage_add.html";
    }

    /**
     * 跳转到修改订单管理
     */
    @RequestMapping("/ordermanage_update/{ordermanageId}")
    public String ordermanageUpdate(@PathVariable Integer ordermanageId, Model model) {
        return PREFIX + "ordermanage_edit.html";
    }

    /**
     * 获取订单管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增订单管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除订单管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改订单管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 订单管理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
