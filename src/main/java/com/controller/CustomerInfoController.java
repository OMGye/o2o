package com.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.common.Const;
import com.common.ServerResponse;
import com.pojo.AdminInfo;
import com.pojo.BasicPriceInfo;
import com.pojo.CustomerInfo;
import com.pojo.OrderInfo;
import com.service.*;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by upupgogogo on 2018/11/17.下午2:36
 */
@Controller
@RequestMapping("/customer/")
public class CustomerInfoController {


    @Autowired
    private CustomerInfoService customerInfoService;

    @RequestMapping(value = "customerInfo/register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(CustomerInfo customerInfo) {
        return customerInfoService.register(customerInfo);
    }

    @RequestMapping(value = "customerInfo/login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(CustomerInfo customerInfo, HttpSession session) {
        ServerResponse<CustomerInfo> response = customerInfoService.login(customerInfo);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "customerInfo/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(CustomerInfo customerInfo, HttpSession session) {
        CustomerInfo curCustomerInfo = (CustomerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curCustomerInfo != null) {
            if (customerInfo.getCustomerId() != null)
                if (curCustomerInfo.getCustomerId().intValue() != customerInfo.getCustomerId().intValue())
                    return ServerResponse.createByErrorMessage("您已越权");
            return customerInfoService.update(customerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "customerInfo/findpassword.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(CustomerInfo customerInfo) {
        return customerInfoService.getPassword(customerInfo);
    }

    @RequestMapping(value = "customerInfo/comfirm.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse comfirm(CustomerInfo customerInfo) {
        return customerInfoService.comfirmUserNameAndEmail(customerInfo);
    }

    @RequestMapping(value = "customerInfo/getcustomerbyid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getEngineerById(HttpSession session) {
        CustomerInfo curCustomerInfo = (CustomerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curCustomerInfo != null) {
            return customerInfoService.getEngineerInfoById(curCustomerInfo.getCustomerId());
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @Autowired
    private BasicPriceInfoService basicPriceInfoService;

    @RequestMapping(value = "basicPriceInfo/getbasicprice.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addBasicPriceInfo(HttpSession session, BasicPriceInfo basicPriceInfo) {
        CustomerInfo curCustomerInfo = (CustomerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curCustomerInfo != null) {
            return basicPriceInfoService.getPrice(basicPriceInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @Autowired
    private PriceTogetherInfoService priceTogetherInfoService;

    @RequestMapping(value = "priceTogetherInfo/list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listPriceTogetherInfo(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        CustomerInfo curCustomerInfo = (CustomerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curCustomerInfo != null) {
            return priceTogetherInfoService.list(pageSize, pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }


    @Autowired
    private OtherParamInfoService otherParamInfoService;

    @RequestMapping(value = "otherParamInfo/list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listOtherParamInfo(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        CustomerInfo curCustomerInfo = (CustomerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curCustomerInfo != null) {
            return otherParamInfoService.list(pageSize, pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @Autowired
    private OrderInfoService orderInfoService;

    @RequestMapping(value = "orderInfo/createorder.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse createOrder(HttpSession session, OrderInfo orderInfo, Integer rushId, Integer[] params) {
        CustomerInfo curCustomerInfo = (CustomerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curCustomerInfo != null) {
            return orderInfoService.createOrder(orderInfo, curCustomerInfo, params, rushId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

}
