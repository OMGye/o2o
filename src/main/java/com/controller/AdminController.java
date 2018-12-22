package com.controller;

import com.common.Const;
import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.service.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 * Created by upupgogogo on 2018/11/12.下午3:53
 */
@Controller
@RequestMapping("/admin/")
public class AdminController {



    @Autowired
    private AdminInfoService adminInfoService;


    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<AdminInfo> login(AdminInfo adminInfo, HttpSession session){
        ServerResponse<AdminInfo> response = adminInfoService.login(adminInfo);
        if(response.isSuccess()){

            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }


    @Autowired
    private EngineerRankInfoService engineerRankInfoService;

    @RequestMapping(value = "engineerrankinfo/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteEngineerRankInfo(HttpSession session, Integer engineerRankId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerRankInfoService.delete(engineerRankId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "engineerrankinfo/update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateEngineerRankInfo(HttpSession session, EngineerRankInfo engineerRankInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerRankInfoService.update(engineerRankInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "engineerrankinfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listEngineerRankInfo(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerRankInfoService.list(pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "engineerrankinfo/add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addEngineerRankInfo(HttpSession session, EngineerRankInfo engineerRankInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerRankInfoService.add(engineerRankInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "engineerrankinfo/getbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getByIdEngineerRankInfo(HttpSession session, Integer engineerRankId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerRankInfoService.getById(engineerRankId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }



    @Autowired
    private BasicPriceInfoService basicPriceInfoService;

    @RequestMapping(value = "basicPriceInfo/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteBasicPriceInfo(HttpSession session, Integer basicPriceId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return basicPriceInfoService.delete(basicPriceId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "basicPriceInfo/update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateBasicPriceInfo(HttpSession session, BasicPriceInfo basicPriceInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return basicPriceInfoService.update(basicPriceInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "basicPriceInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listBasicPriceInfo(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return basicPriceInfoService.list(pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "basicPriceInfo/add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addBasicPriceInfo(HttpSession session, BasicPriceInfo basicPriceInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return basicPriceInfoService.add(basicPriceInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "basicPriceInfo/getbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getByIdBasicPriceInfo(HttpSession session, Integer basicPriceId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return basicPriceInfoService.getById(basicPriceId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }


    @Autowired
    private PriceTogetherInfoService priceTogetherInfoService;

    @RequestMapping(value = "priceTogetherInfo/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deletePriceTogetherInfo(HttpSession session, Integer priceTogetherId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return priceTogetherInfoService.delete(priceTogetherId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "priceTogetherInfo/update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updatePriceTogetherInfo(HttpSession session, PriceTogetherInfo priceTogetherInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return priceTogetherInfoService.update(priceTogetherInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "priceTogetherInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listPriceTogetherInfo(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return priceTogetherInfoService.list(pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "priceTogetherInfo/add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addPriceTogetherInfo(HttpSession session, PriceTogetherInfo priceTogetherInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return priceTogetherInfoService.add(priceTogetherInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "priceTogetherInfo/getbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getByIdPriceTogetherInfo(HttpSession session, Integer priceTogetherId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return priceTogetherInfoService.getById(priceTogetherId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }


    @Autowired
    private PriceDeductInfoService priceDeductInfoService;

    @RequestMapping(value = "priceDeductInfo/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deletePriceDeductInfo(HttpSession session, Integer priceDeductId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return priceDeductInfoService.delete(priceDeductId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "priceDeductInfo/update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updatePriceDeductInfo(HttpSession session, PriceDeductInfo priceDeductInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return priceDeductInfoService.update(priceDeductInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "priceDeductInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listPriceDeductInfo(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
            return priceDeductInfoService.list(pageSize,pageNum);
    }

    @RequestMapping(value = "priceDeductInfo/add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addPriceDeductInfo(HttpSession session, PriceDeductInfo priceDeductInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return priceDeductInfoService.add(priceDeductInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "priceDeductInfo/getbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getByIdPriceDeductInfo(HttpSession session, Integer priceDeductId){
            return priceDeductInfoService.getById(priceDeductId);
    }


    @Autowired
    private OtherParamInfoService otherParamInfoService;

    @RequestMapping(value = "otherParamInfo/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteOtherParamInfo(HttpSession session, Integer paramId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return otherParamInfoService.delete(paramId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "otherParamInfo/update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateOtherParamInfo(HttpSession session, OtherParamInfo otherParamInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return otherParamInfoService.update(otherParamInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "otherParamInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listOtherParamInfo(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return otherParamInfoService.list(pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "otherParamInfo/add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addOtherParamInfo(HttpSession session, OtherParamInfo otherParamInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return otherParamInfoService.add(otherParamInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "otherParamInfo/getbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getByIdOtherParamInfo(HttpSession session, Integer paramId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return otherParamInfoService.getById(paramId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }






    @Autowired
    private O2oInfoService o2oInfoService;

    @RequestMapping(value = "o2oInfo/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteO2oInfo(HttpSession session, Integer o2oId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return o2oInfoService.delete(o2oId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "o2oInfo/update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateO2oInfo(HttpSession session, O2oInfo o2oInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return o2oInfoService.update(o2oInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "o2oInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listO2oInfo(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
            return o2oInfoService.list(pageSize,pageNum);
    }

    @RequestMapping(value = "o2oInfo/add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addO2oInfo(HttpSession session, O2oInfo o2oInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return o2oInfoService.add(o2oInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "o2oInfo/getbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getByIdO2oInfo(HttpSession session, Integer o2oId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return o2oInfoService.getById(o2oId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }




    @Autowired
    private QuantityInfoService quantityInfoService;

    @RequestMapping(value = "quantityInfo/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteQuantityInfo(HttpSession session, Integer quantityId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return quantityInfoService.delete(quantityId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "quantityInfo/update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateQuantityInfo(HttpSession session, QuantityInfo quantityInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return quantityInfoService.update(quantityInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "quantityInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listQuantityInfo(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
            return quantityInfoService.list(pageSize,pageNum);
    }

    @RequestMapping(value = "quantityInfo/add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addQuantityInfo(HttpSession session, QuantityInfo quantityInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return quantityInfoService.add(quantityInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "quantityInfo/getbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getByIdQuantityInfo(HttpSession session, Integer quantityId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return quantityInfoService.getById(quantityId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }



    @Autowired
    private EngineerInfoService engineerInfoService;

    @RequestMapping(value = "engineerInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> engineerlist(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize, Integer state){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerInfoService.list(pageSize,pageNum,state);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "engineerInfo/getengineerbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse engineerGetEngineerById(HttpSession session, Integer engineerId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerInfoService.getEngineerInfoById(engineerId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "engineerInfo/check.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse engineerCheck(HttpSession session, EngineerInfo engineerInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerInfoService.check(engineerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }





    @Autowired
    private CustomerInfoService customerInfoService;

    @RequestMapping(value = "customerInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> customerInfoList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize, Integer state){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return customerInfoService.list(pageSize,pageNum,state);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "customerInfo/getcustomerbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse customerGetEngineerById(HttpSession session, Integer customerId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return customerInfoService.getEngineerInfoById(customerId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "customerInfo/check.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse check(HttpSession session, CustomerInfo customerInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return customerInfoService.check(customerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @Autowired
    private OrderInfoService orderInfoService;

    @RequestMapping(value = "orderInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize, Integer state, Integer firstCategory, Integer orderQae){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminOrderList(pageSize,pageNum,state,firstCategory,orderQae);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/getorderbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> getOrderById(HttpSession session, Integer orderId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.getOrderById(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/dealorder.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse dealOrder(HttpSession session, Integer orderId, BigDecimal customerPrice, BigDecimal engineerPrice, BigDecimal engineerQaePrice){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.dealOrder(orderId,customerPrice,engineerPrice,engineerQaePrice);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }



    @Autowired
    private BillInfoService billInfoService;

    @RequestMapping(value = "billInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> billInfoList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize, Integer userId, Integer userType){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return billInfoService.list(pageSize, pageNum, userId, userType);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @Autowired
    private PayInfoService payInfoService;

    @RequestMapping(value = "payInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> payInfoList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize, Integer userId, String userName){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return payInfoService.list(pageSize, pageNum, userId, userName);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @Autowired
    private IncomeInfoService incomeInfoService;
    @RequestMapping(value = "incomeInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> incomeInfoList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize, Integer orderId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return incomeInfoService.list(pageSize, pageNum,orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "orderInfo/listansorque.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listAnsOrQue(HttpSession session, Integer orderId,  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.listOrderAnsqueInfoByOrderId(pageNum,pageSize,orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }



    @Autowired
    private DrawCashInfoService drawCashInfoService;

    @RequestMapping(value = "drawCashInfo/list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpSession session, Integer type, Integer userId, Integer state,  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return drawCashInfoService.list(pageSize, pageNum, userId, type, state);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "drawCashInfo/check.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> check(HttpSession session, Integer drawCashId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return drawCashInfoService.check(drawCashId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "drawCashInfo/getbyid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> getById(HttpSession session, Integer drawCashId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return drawCashInfoService.getById(drawCashId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @Autowired
    private ProposeInfoService proposeInfoService;

    @RequestMapping(value = "proposeInfo/list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){

            return proposeInfoService.list(pageSize, pageNum);
    }

    @RequestMapping(value = "proposeInfo/getbyid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> getByProposeId(HttpSession session, Integer proposeId){

        return proposeInfoService.getById(proposeId);
    }

    @RequestMapping(value = "proposeInfo/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> delete(HttpSession session, Integer proposeId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return proposeInfoService.delete(proposeId,null,null);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }



    @RequestMapping(value = "orderInfo/export.do", method = RequestMethod.GET)
    public void export(HttpSession session, HttpServletResponse response, String startTime, String endTime) {
        AdminInfo adminInfo = (AdminInfo) session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=order.xlsx;charset=UTF-8");
            XSSFWorkbook workbook = orderInfoService.exportExcelInfo(startTime, endTime);
            try {
                OutputStream output = response.getOutputStream();
                BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
                workbook.write(bufferedOutPut);
                bufferedOutPut.flush();
                bufferedOutPut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping(value = "drawCashInfo/export.do", method = RequestMethod.GET)
    public void drawCashInfoExport(HttpSession session, HttpServletResponse response, String startTime, String endTime) {

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=drawCash.xlsx;charset=UTF-8");
        XSSFWorkbook workbook = drawCashInfoService.exportExcelInfo(startTime, endTime);
        try {
            OutputStream output = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
        @RequestMapping(value = "orderInfo/getByOrderIdLike.do", method = RequestMethod.GET)
        @ResponseBody
        public ServerResponse<PageInfo> selectByIdLike(HttpSession session, Integer orderId,  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
            AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
            if (adminInfo != null){
                return orderInfoService.selectByIdLike(pageNum,pageSize,orderId);
            }
            return ServerResponse.createByErrorMessage("请登入管理员账户");
        }

    @RequestMapping(value = "orderInfo/getByOtherIdLike.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> selectByOtherIdLike(HttpSession session, Integer id,Integer type, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.selectByOtherIdLike(pageNum,pageSize,id,type);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "customerInfo/getByCustomerIdLike.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> selectCustomerByIdLike(HttpSession session, Integer customerId,  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return customerInfoService.selectByIdLike(pageNum,pageSize,customerId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "engineerInfo/getByEngineerIdLike.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> selectEngineerByIdLike(HttpSession session, Integer engineerId,  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerInfoService.selectByIdLike(pageNum,pageSize,engineerId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "billInfo/getByBillIdLike.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> selectBillInfoByIdLike(HttpSession session, Integer billId,  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return billInfoService.selectByIdLike(pageNum,pageSize,billId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "priceInfo/allprice.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse export(HttpSession session, Integer type) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.getAllPrice(type);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    
}
