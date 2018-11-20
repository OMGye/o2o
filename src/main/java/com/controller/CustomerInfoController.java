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
import com.util.AlipayConfig;
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

    @RequestMapping(value = "orderInfo/pay.do", method = RequestMethod.GET)
    public void pay(HttpSession session, Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);


        String out_trade_no = null;
        ServerResponse serverResponse = orderInfoService.getOrderById(orderId);
        OrderInfo orderInfo = (OrderInfo)serverResponse.getData();
        try {
            //商户订单号，商户网站订单系统中唯一订单号，必填
            out_trade_no = new String((""+orderInfo.getOrderId()).getBytes("ISO-8859-1"), "UTF-8");

            //付款金额，必填
            String total_amount = new String("0.01".getBytes("ISO-8859-1"), "UTF-8");
            //订单名称，必填
            String subject = new String((""+orderInfo.getOrderPrice()).getBytes("ISO-8859-1"), "UTF-8");
            //商品描述，可空
            String body = new String("".getBytes("ISO-8859-1"), "UTF-8");

            alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                    + "\"total_amount\":\"" + total_amount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"body\":\"" + body + "\","
                    + "\"timeout_express\":\"1m\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();

            //输出
            PrintWriter out = response.getWriter();
            out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节


    }

    @RequestMapping(value = "orderInfo/callback.do", method = RequestMethod.GET)
    @ResponseBody
    public Object callBack(HttpSession session, Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                PrintWriter out = response.getWriter();
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                boolean signVerified = AlipaySignature.rsaCheckV2(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
                params.put(name, valueStr);
                if(signVerified) {//验证成功

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
      return null;
    }

}
