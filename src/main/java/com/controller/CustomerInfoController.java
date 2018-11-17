package com.controller;

import com.common.Const;
import com.common.ServerResponse;
import com.pojo.CustomerInfo;
import com.service.CustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

/**
 * Created by upupgogogo on 2018/11/17.下午2:36
 */
@Controller
@RequestMapping("/customer/")
public class CustomerInfoController {


    @Autowired
    private CustomerInfoService customerInfoService;

    @RequestMapping(value = "customerInfo/register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(CustomerInfo customerInfo){
        return customerInfoService.register(customerInfo);
    }

    @RequestMapping(value = "customerInfo/login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(CustomerInfo customerInfo, HttpSession session){
        ServerResponse<CustomerInfo> response = customerInfoService.login(customerInfo);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "customerInfo/update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(CustomerInfo customerInfo, HttpSession session){
        CustomerInfo curCustomerInfo = (CustomerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curCustomerInfo != null){
            if (customerInfo.getCustomerId() != null)
                if(curCustomerInfo.getCustomerId().intValue() != customerInfo.getCustomerId().intValue())
                    return ServerResponse.createByErrorMessage("您已越权");
            return customerInfoService.update(customerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "customerInfo/findpassword.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(CustomerInfo customerInfo){
        return customerInfoService.getPassword(customerInfo);
    }

    @RequestMapping(value = "customerInfo/comfirm.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse comfirm(CustomerInfo customerInfo){
        return customerInfoService.comfirmUserNameAndEmail(customerInfo);
    }

    @RequestMapping(value = "customerInfo/getengineerbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getEngineerById(HttpSession session){
        CustomerInfo curCustomerInfo = (CustomerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curCustomerInfo != null){
            return customerInfoService.getEngineerInfoById(curCustomerInfo.getCustomerId());
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

}
