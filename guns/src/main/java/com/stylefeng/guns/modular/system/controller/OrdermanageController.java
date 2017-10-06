package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.OrderMapper;
import com.stylefeng.guns.common.persistence.model.ClassSchedule;
import com.stylefeng.guns.common.persistence.model.Orders;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.dao.DicUtilDao;
import com.stylefeng.guns.modular.system.dao.OrdermanageDao;
import com.stylefeng.guns.modular.system.warpper.ClassScheduleWrapper;
import com.stylefeng.guns.modular.system.warpper.OrderWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    @Resource
	private OrdermanageDao ordermanageDao;
	@Resource
    private DicUtilDao dicUtilDao;
	@Resource
	private OrderMapper orderMapper;

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
    public String ordermanageAdd(Model model) {
    	 Map dicMap = new HashMap();
    	 dicMap.put("meterialname", dicUtilDao.getMeterialName());
		 dicMap.put("coursename", dicUtilDao.getCourseName());
		 dicMap.put("calsstime", ConstantFactory.me().getDictList("课时价格"));
		 model.addAttribute("dicMap",dicMap); 
        return PREFIX + "ordermanage_add.html";
    }

    /**
     * 跳转到修改订单管理
     */
    @RequestMapping("/ordermanage_update/{id}")
    public String ordermanageUpdate(@PathVariable Integer id, Model model) {
    	
    	Orders order = this.orderMapper.selectById(id);
    	 Map dicMap = new HashMap();
    	 dicMap.put("meterialname", dicUtilDao.getMeterialName());
		 dicMap.put("coursename", dicUtilDao.getCourseName());
		 dicMap.put("calsstime", ConstantFactory.me().getDictList("课时价格"));
		 dicMap.put("statusname", ConstantFactory.me().getDictList("支付状态"));
		 model.addAttribute("dicMap",dicMap); 
		 model.addAttribute(order);
		 LogObjectHolder.me().set(order);
        return PREFIX + "ordermanage_edit.html";
    }

    /**
     * 获取订单管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String studentid,String date,Model model) {
    	 List<Map<String, Object>> list = this.ordermanageDao.list(studentid, date);
         return super.warpObject(new OrderWrapper(list));
    }

    /**
     * 新增订单管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid Orders orders,BindingResult result) {
    	
    	 orders.setCreateDate(new Date());
    	 orders.setStatus(0);
    	 orders.insert();
        return super.SUCCESS_TIP;
    }

    /**
     * 删除订单管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer id) {
    	this.orderMapper.deleteById(id);
        return SUCCESS_TIP;
    }


    /**
     * 修改订单管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid Orders order, BindingResult result) {
    	
    	Orders nOrders = new Orders();
      	 BeanUtils.copyProperties(order,nOrders);                       
      	nOrders.updateById();
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
