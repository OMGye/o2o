package com.controller;

import com.common.Const;
import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.AdminInfo;
import com.pojo.EngineerInfo;
import com.service.EngineerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by upupgogogo on 2018/11/16.下午3:39
 */
@Controller
@RequestMapping("/engineer/")
public class EngineerInfoController {


    @Autowired
    private EngineerInfoService engineerInfoService;

    @RequestMapping(value = "engineerInfo/register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, EngineerInfo engineerInfo){
        String path = request.getSession().getServletContext().getRealPath("upload");
        return engineerInfoService.register(engineerInfo,file,path);
    }

    @RequestMapping(value = "engineerInfo/login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(EngineerInfo engineerInfo, HttpSession session){
        ServerResponse<EngineerInfo> response = engineerInfoService.login(engineerInfo);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
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

}