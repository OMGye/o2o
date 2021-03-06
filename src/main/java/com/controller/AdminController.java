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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
        session.setMaxInactiveInterval(30 * 60);
        ServerResponse<AdminInfo> response = adminInfoService.login(adminInfo);
        if(response.isSuccess()){

            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "resetpassword.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<AdminInfo> resetPassword(HttpSession session, String newPassword, String oldPassword){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            if (adminInfo.getPassword().equals(oldPassword)){
                return adminInfoService.rePassword(adminInfo.getAdminId(),newPassword);
            }
            return ServerResponse.createByErrorMessage("原密码错误");
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
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

    @RequestMapping(value = "engineerInfo/admincheck.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse engineerAdminCheck(HttpSession session, Integer adminCheck, Integer engineerId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerInfoService.adminCheck(adminCheck,engineerId);
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
    public ServerResponse allPrice(HttpSession session, Integer type) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.getAllPrice(type);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "customerInfo/deductoraddprice.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse customerDeductOrAddPrice(HttpSession session, Integer type, BigDecimal price, Integer customerId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return customerInfoService.deductOrAddMoney(type,price,customerId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "customerInfo/admincheck.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse customerAdminCheck(HttpSession session, Integer adminCheck, Integer customerId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return customerInfoService.adminCheck(adminCheck,customerId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "engineerInfo/deductoraddprice.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse engineerDeductOrAddPrice(HttpSession session, Integer type, BigDecimal price, Integer engineerId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerInfoService.deductOrAddMoney(type, price, engineerId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/changestate.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse changeOrderState(HttpSession session, Integer type, Integer orderId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.changeOrderState(type, orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/download.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse downloadTar(HttpSession session, String startTime, String endTime) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.createTarByDate(startTime,endTime);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/delete.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse deleteTar(HttpSession session, String startTime, String endTime) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.deleteTarByDate(startTime,endTime);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/adminchecklist.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse adminCheckList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminCheckList(pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/admincheck.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminCheck(HttpSession session, Integer orderId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminCheck(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/admincheckrefuse.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminCheckRefuse(HttpSession session, Integer orderId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminCheckRefuse(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "orderInfo/adminhelpreturnorder.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminHelpReturnOrder(HttpSession session, Integer orderId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminHelpReturnOrder(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/adminhelpuploadfile.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminHelpUploadFile(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, Integer orderId,HttpServletRequest request) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            String path = request.getSession().getServletContext().getRealPath("upload");
            return orderInfoService.adminHelpUploadFile(orderId,file,path);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/adminhelpcheck.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminHelpCheck(HttpSession session, Integer orderId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminHelpCheck(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/adminhelpreturntoenginner.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminHelpReturnToEnginner(HttpSession session, Integer orderId, String dec) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminHelpReturnToEnginner(orderId, dec);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/adminhelpreturntoqaeenginner.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminHelpReturnToQaeEnginner(HttpSession session, Integer orderId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminHelpReturnToQaeEnginner(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/adminhelpcustomerreturntoenginner.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminHelpCustomerReturnToEnginner(HttpSession session, Integer orderId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminHelpCustomerReturnToEnginner(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/adminHelpCancleOrder.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminHelpCancleOrder(HttpSession session, Integer orderId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminHelpCancleOrder(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/adminhelpfinishorder.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminHelpFinishOrder(HttpSession session, Integer orderId) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return orderInfoService.adminHelpFinishOrder(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "engineerInfo/selectbyphonelike.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse selectEngineerByPhoneLike(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, String phone) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return engineerInfoService.selectByPhoneLike(pageNum,pageSize,phone);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


        @RequestMapping(value = "customerInfo/selectbyphonelike.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse selectCustomerByPhoneLike(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, String phone) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return customerInfoService.selectByPhoneLike(pageNum,pageSize,phone);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }




    @RequestMapping(value = "adminInfo/adduser.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addAdminUser(HttpSession session, AdminInfo adminInfo) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return adminInfoService.addUser(adminInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "adminInfo/deleteuser.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteAdminUser(HttpSession session, Integer adminId) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return adminInfoService.deleteUser(adminId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "adminInfo/updateuser.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateAdminUser(HttpSession session, AdminInfo adminInfo) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return adminInfoService.updateUser(adminInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "adminInfo/getuserbyid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getAdminInfoUserById(HttpSession session, Integer adminId) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return adminInfoService.getById(adminId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "adminInfo/listuser.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listAdminUser(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return adminInfoService.list(pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @Autowired
    private NoticeService noticeService;
    @RequestMapping(value = "noticeInfo/addnotice.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addNotice(HttpSession session, Notice notice) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return noticeService.addNotice(notice);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "noticeInfo/deletenotice.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteNotice(HttpSession session, Integer noticeId) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return noticeService.deleteNotice(noticeId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "noticeInfo/updatenotice.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updatenotice(HttpSession session, Notice notice) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return noticeService.updateNotice(notice);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "noticeInfo/getnoticebyid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getNoticeById(HttpSession session, Integer noticeId) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return noticeService.getById(noticeId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "noticeInfo/listnotice.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listAdminUser(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, Integer type) {
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return noticeService.list(type, pageSize, pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }




    @Autowired
    private SelfCategoryService selfCategoryService;
    @RequestMapping(value = "selfCategoryInfo/addselfcategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addSelfCategory(HttpSession session, SelfCategory selfCategory) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return selfCategoryService.add(selfCategory);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfCategoryInfo/deleteselfcategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteSelfCategory(HttpSession session, Integer selfCategoryId) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return selfCategoryService.delete(selfCategoryId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfCategoryInfo/updateselfcategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateSelfCategory(HttpSession session, SelfCategory selfCategory) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return selfCategoryService.update(selfCategory);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfCategoryInfo/getselfcategorybyid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getSelfCategoryById(HttpSession session, Integer selfCategoryId) {
        AdminInfo currentAdminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (currentAdminInfo != null){
            return selfCategoryService.getById(selfCategoryId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfCategoryInfo/listselfcategory.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listSelfCategory(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
            return selfCategoryService.list(pageSize, pageNum);

    }


    @Autowired
    private SelfQuantityInfoService selfQuantityInfoService;

    @RequestMapping(value = "selfQuantityInfo/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteSelfQuantityInfo(HttpSession session, Integer quantityId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfQuantityInfoService.delete(quantityId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "selfQuantityInfo/update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateSelfQuantityInfo(HttpSession session, SelfQuantityInfo quantityInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfQuantityInfoService.update(quantityInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "selfQuantityInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listSelfQuantityInfo(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        return selfQuantityInfoService.list(pageSize,pageNum);
    }

    @RequestMapping(value = "selfQuantityInfo/add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addSelfQuantityInfo(HttpSession session, SelfQuantityInfo quantityInfo){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfQuantityInfoService.add(quantityInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "selfQuantityInfo/getbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getByIdSelfQuantityInfo(HttpSession session, Integer quantityId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfQuantityInfoService.getById(quantityId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }


    @Autowired
    private SelfOrderService selfOrderService;

    @RequestMapping(value = "selfOrder/selforderlist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse adminOrderList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize,Integer state, Integer firstCategory, String selfCategoryName){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfOrderService.adminOrderList(pageSize,pageNum,state
            ,firstCategory,selfCategoryName);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfOrder/getselforderbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getSelfOrderById(HttpSession session, Integer orderId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfOrderService.getOrderById(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfOrder/getselforderansquebyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getSelfOrderAnsQueById(HttpSession session, Integer orderId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfOrderService.listOrderAnsqueInfoByOrderId(pageNum,pageSize,orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfOrder/admindeal.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminDeal(HttpSession session, Integer orderId, BigDecimal customerPrice, BigDecimal engineerPrice){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfOrderService.adminDeal(orderId,customerPrice,engineerPrice);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfOrder/admincancleselforder.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminCancleSelfOrder(HttpSession session, Integer orderId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfOrderService.adminCancleSelfOrder(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfOrder/admincancletocreate.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminCancleToCreate(HttpSession session, Integer orderId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfOrderService.adminCancleToCreate(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfOrder/adminfinishselforder.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminFinishSelfOrder(HttpSession session, Integer orderId){
        AdminInfo adminInfo = (AdminInfo)session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null){
            return selfOrderService.adminFinishSelfOrder(orderId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfOrder/exportexcelinfo.do", method = RequestMethod.GET)
    public void exportExcelInfo(HttpSession session, HttpServletResponse response, String startTime, String endTime) {
        AdminInfo adminInfo = (AdminInfo) session.getAttribute(Const.CURRENT_USER);
        if (adminInfo != null) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=selfOrder.xlsx;charset=UTF-8");
            XSSFWorkbook workbook = selfOrderService.exportExcelInfo(startTime, endTime);
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
}
