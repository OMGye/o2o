package com.controller;

import com.common.Const;
import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.service.*;
import com.vo.EngineerRankVO;
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

/**
 * Created by upupgogogo on 2018/11/16.下午3:39
 */
@Controller
@RequestMapping("/engineer/")
public class EngineerInfoController {


    @Autowired
    private EngineerInfoService engineerInfoService;

    @Autowired
    private EngineerRankInfoService rankInfoService;

    @Autowired
    private CustomerInfoService customerInfoService;

    @RequestMapping(value = "engineerInfo/register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, EngineerInfo engineerInfo){
        String path = request.getSession().getServletContext().getRealPath("upload");
        return engineerInfoService.register(engineerInfo,file,path);
    }
    

    @RequestMapping(value = "engineerInfo/login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(String engineerName, String password, HttpSession session){
        //以秒为单位
        session.setMaxInactiveInterval(30 * 60);
        ServerResponse<EngineerInfo> response = engineerInfoService.login(engineerName, password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
            EngineerInfo curEngineerInfo = (EngineerInfo)response.getData();
            if (curEngineerInfo.getEngineerRank() != null){
                ServerResponse serverResponse = rankInfoService.getEngineerRank(curEngineerInfo.getEngineerRank());
                EngineerRankVO engineerRankVO = (EngineerRankVO) serverResponse.getData();
                if (engineerRankVO != null) {
                    engineerRankVO.setFirstCategory(curEngineerInfo.getEngineerClassfy());
                    session.setAttribute(Const.CURRENT_RANK, engineerRankVO);
                }
            }
        }
        return response;
    }

    @RequestMapping(value = "engineerInfo/logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "engineerInfo/update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(EngineerInfo engineerInfo, HttpSession session){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            if (engineerInfo.getEngineerId() != null){
                if(curEngineerInfo.getEngineerId().intValue() != engineerInfo.getEngineerId().intValue()){
                    return ServerResponse.createByErrorMessage("您已越权");
                }
            }
            return engineerInfoService.update(engineerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "engineerInfo/findpassword.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(EngineerInfo engineerInfo){
        return engineerInfoService.getPassword(engineerInfo);
    }

    @RequestMapping(value = "engineerInfo/comfirm.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse comfirm(EngineerInfo engineerInfo){
        return engineerInfoService.comfirmUserNameAndEmail(engineerInfo);
    }

    @RequestMapping(value = "engineerInfo/getengineerbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getEngineerById(HttpSession session){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return engineerInfoService.getEngineerInfoById(curEngineerInfo.getEngineerId());
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }




    @Autowired
    private OrderInfoService orderInfoService;
    @RequestMapping(value = "orderInfo/cancaughtlist.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse canCaughtorderList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null) {
            EngineerRankVO engineerRankVO = (EngineerRankVO) session.getAttribute(Const.CURRENT_RANK);
            if (engineerRankVO == null)
                return ServerResponse.createByErrorMessage("当前工程没有等级");
            return orderInfoService.engineerCaughtList(pageSize,pageNum,engineerRankVO);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/caughtcamorder.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse caughtOrder(HttpSession session, Integer orderId){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null) {
            EngineerRankVO engineerRankVO = (EngineerRankVO) session.getAttribute(Const.CURRENT_RANK);
            if (engineerRankVO == null)
                return ServerResponse.createByErrorMessage("当前工程师没有等级");
            return orderInfoService.caughtCamOrder(orderId,engineerRankVO,curEngineerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/orderuploadfile.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse orderUploadFile(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpSession session, Integer orderId, HttpServletRequest request){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null) {
            EngineerRankVO engineerRankVO = (EngineerRankVO) session.getAttribute(Const.CURRENT_RANK);
            if (engineerRankVO == null)
                return ServerResponse.createByErrorMessage("当前工程没有等级");
            String path = request.getSession().getServletContext().getRealPath("upload");
            return orderInfoService.orderUploadFile(orderId,curEngineerInfo,file,path);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "orderInfo/ownerlist.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> ownerList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, Integer state){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return orderInfoService.engineerList(pageSize,pageNum,curEngineerInfo,state);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "orderInfo/ownerqaelist.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> ownerQaeList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, Integer state){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return orderInfoService.engineerQaeList(pageSize,pageNum,curEngineerInfo,state);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/getorderbyid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> getOrderById(HttpSession session, Integer orderId){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            ServerResponse response = orderInfoService.getOrderById(orderId);
            OrderInfo orderInfo = (OrderInfo)response.getData();
            return response;
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "orderInfo/canqaecaughtlist.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse canCaughtQaeorderList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null) {
            EngineerRankVO engineerRankVO = (EngineerRankVO) session.getAttribute(Const.CURRENT_RANK);
            if (engineerRankVO == null)
                return ServerResponse.createByErrorMessage("当前工程师没有等级");
            if (engineerRankVO.getQAE() == 1)
                return orderInfoService.engineerQaeCaughtList(pageSize,pageNum,engineerRankVO);
            return ServerResponse.createByErrorMessage("您当前不能接审核类订单");
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "orderInfo/qaecaughtcamorder.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse caughtQaeOrder(HttpSession session, Integer orderId){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null) {
            EngineerRankVO engineerRankVO = (EngineerRankVO) session.getAttribute(Const.CURRENT_RANK);
            if (engineerRankVO == null || engineerRankVO.getQAE() == 0)
                return ServerResponse.createByErrorMessage("当前工程师没有等级或无法接审核类订单");
            return orderInfoService.caughtQaeOrder(orderId,engineerRankVO,curEngineerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/check.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse qaeCheck(HttpSession session, Integer orderId, Integer state, String refuseDec){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null) {
            EngineerRankVO engineerRankVO = (EngineerRankVO) session.getAttribute(Const.CURRENT_RANK);
            if (engineerRankVO == null || engineerRankVO.getQAE() == 0)
                return ServerResponse.createByErrorMessage("当前工程师没有等级或无法接审核类订单");
            return orderInfoService.qaeCheck(orderId, state, refuseDec, curEngineerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/engineercancle.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse engineerCancle(HttpSession session, Integer orderId){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return orderInfoService.engineerCancleOrder(orderId,curEngineerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/engineerqaecancle.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse engineerQaeCancle(HttpSession session, Integer orderId){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return orderInfoService.engineerCheckCancleOrder(orderId,curEngineerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/engineercomfirmorcomplain.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse comfirmOrComplain(HttpSession session, Integer orderId, Integer complain, String dec){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return orderInfoService.engineerComfirmDeductOrComplain(orderId,curEngineerInfo, complain, dec);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/engineerqaecomfirmorcomplain.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse qaeComfirmOrComplain(HttpSession session, Integer orderId, Integer complain, String dec){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return orderInfoService.engineerCheckComfirmDeductOrComplain(orderId,curEngineerInfo, complain, dec);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/addansorque.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addAnsOrQue(HttpSession session, Integer orderId, String orderAnsqueContent){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return orderInfoService.addAnsOrQue(orderId,curEngineerInfo.getEngineerId(),1, curEngineerInfo.getEngineerName(),orderAnsqueContent);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/uploadansorque.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadAnsOrQue(HttpSession session, Integer orderId, @RequestParam(value = "upload_file",required = false) MultipartFile file,  HttpServletRequest request){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            String path = request.getSession().getServletContext().getRealPath("upload");
            return orderInfoService.uploadAnsOrQue(orderId,curEngineerInfo.getEngineerId(),1, curEngineerInfo.getEngineerName(),file, path);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "orderInfo/listansorque.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listAnsOrQue(HttpSession session, Integer orderId,  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return orderInfoService.listOrderAnsqueInfoByOrderId(pageNum,pageSize,orderId,curEngineerInfo.getEngineerId(),1);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }



    @Autowired
    private BillInfoService billInfoService;

    @RequestMapping(value = "billInfo/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> billInfoList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return billInfoService.list(pageSize, pageNum, curEngineerInfo.getEngineerId(), 1);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @Autowired
    private DrawCashInfoService drawCashInfoService;

    @RequestMapping(value = "drawCashInfo/list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpSession session,  Integer state,  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return drawCashInfoService.list(pageSize, pageNum, curEngineerInfo.getEngineerId(), 1, state);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "drawCashInfo/add.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> add(HttpSession session, DrawCashInfo drawCashInfo, String password) {
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null) {
            if (curEngineerInfo.getPassword().equals(password)) {
                return drawCashInfoService.add(drawCashInfo, curEngineerInfo.getEngineerId(), 1, curEngineerInfo.getEngineerName());
            }
            return ServerResponse.createByErrorMessage("密码错误");
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @Autowired
    private ProposeInfoService proposeInfoService;

    @RequestMapping(value = "proposeInfo/add.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> add(HttpSession session, ProposeInfo proposeInfo){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null) {
            return proposeInfoService.add(proposeInfo,1,curEngineerInfo.getEngineerId());
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "proposeInfo/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> delete(HttpSession session, Integer proposeId){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null) {
            return proposeInfoService.delete(proposeId,curEngineerInfo.getEngineerId(),1);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "orderInfo/export.do", method = RequestMethod.GET)
    public void export(HttpSession session, HttpServletResponse response, String startTime, String endTime, Integer type) {
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=order.xlsx;charset=UTF-8");
            XSSFWorkbook workbook = null;
            if (type == 0){
           workbook = orderInfoService.customerOrEngineerExportExcelInfo(1, curEngineerInfo.getEngineerId(), startTime, endTime);
            }
            if (type == 1){
                workbook = orderInfoService.customerOrEngineerExportExcelInfo(2, curEngineerInfo.getEngineerId(), startTime, endTime);
            }
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

    @RequestMapping(value = "customerInfo/findattention.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse findAttention(HttpSession session, Integer customerId){
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return customerInfoService.selectDetailById(customerId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @Autowired
    private NoticeService noticeService;
    @RequestMapping(value = "noticeInfo/listnotice.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listAdminUser(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, Integer type) {
        type = Const.Notice.ENGINEER;
        return noticeService.list(type, pageSize, pageNum);
    }

    @RequestMapping(value = "noticeInfo/getnoticebyid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getNoticeById(Integer noticeId) {
        return noticeService.getById(noticeId);
    }

    @Autowired
    private SelfOrderService selfOrderService;

    @RequestMapping(value = "selfOrder/orderuploadfile.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse orderUploadFile(HttpSession session, Integer orderId , @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            String path = request.getSession().getServletContext().getRealPath("upload");
            return selfOrderService.orderUploadFile(orderId,curEngineerInfo,file,path);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "selfOrder/caughtorder.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse caughtOrder(Integer orderId,HttpSession session) {
        EngineerInfo curEngineerInfo = (EngineerInfo) session.getAttribute(Const.CURRENT_USER);
        if (curEngineerInfo != null){
            return selfOrderService.caughtOrder(orderId,curEngineerInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }
}
