package com.controller;

import com.common.Const;
import com.common.ServerResponse;
import com.pojo.AdminInfo;
import com.pojo.BasicPriceInfo;
import com.pojo.EngineerRankInfo;
import com.pojo.PriceTogetherInfo;
import com.service.AdminInfoService;
import com.service.BasicPriceInfoService;
import com.service.EngineerRankInfoService;
import com.service.PriceTogetherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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
}
