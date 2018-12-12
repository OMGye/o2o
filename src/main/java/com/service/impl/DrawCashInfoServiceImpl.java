package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.BillInfoMapper;
import com.dao.CustomerInfoMapper;
import com.dao.DrawCashInfoMapper;
import com.dao.EngineerInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.service.DrawCashInfoService;
import com.util.DateTimeUtil;
import com.util.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by upupgogogo on 2018/12/7.下午2:27
 */
@Service("drawCashInfoService")
public class DrawCashInfoServiceImpl implements DrawCashInfoService {

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private EngineerInfoMapper engineerInfoMapper;

    @Autowired
    private BillInfoMapper billInfoMapper;

    @Autowired
    private DrawCashInfoMapper drawCashInfoMapper;

    @Override
    public ServerResponse add(DrawCashInfo drawCashInfo, Integer userId, Integer type, String userName) {

        if (drawCashInfo.getDrawAccount() == null)
            return ServerResponse.createByErrorMessage("参数为空");

        if (type == 0){
            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(userId);
            if (customerInfo.getCustomerBalance().doubleValue() > 0){
                drawCashInfo.setDrawCashMoney(customerInfo.getCustomerBalance());
                drawCashInfo.setUserId(customerInfo.getCustomerId());
                drawCashInfo.setUserType(0);
                drawCashInfo.setUserName(customerInfo.getCustomerName());
                drawCashInfo.setState(Const.Draw.UN_DEAL);
                int row = drawCashInfoMapper.insert(drawCashInfo);
                if (row > 0){
                    customerInfo.setCustomerBalance(new BigDecimal(0));
                    customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

                    BillInfo billInfo = new BillInfo();
                    billInfo.setUserId(customerInfo.getCustomerId());
                    billInfo.setUserName(customerInfo.getCustomerName());
                    billInfo.setBillDec("提现操作");
                    billInfo.setBillMoney(drawCashInfo.getDrawCashMoney());
                    billInfo.setUserType(0);

                    billInfoMapper.insert(billInfo);
                    return ServerResponse.createBySuccess("提现成功");
                }
                return ServerResponse.createByErrorMessage("操作失败");
            }
        }

        if (type == 1){
            EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(userId);
            if (engineerInfo.getEngineerBalance().doubleValue() > 0){
                drawCashInfo.setDrawCashMoney(engineerInfo.getEngineerBalance());
                drawCashInfo.setUserId(engineerInfo.getEngineerId());
                drawCashInfo.setUserType(1);
                drawCashInfo.setUserName(engineerInfo.getEngineerName());
                drawCashInfo.setState(Const.Draw.UN_DEAL);
                int row = drawCashInfoMapper.insert(drawCashInfo);
                if (row > 0){
                    engineerInfo.setEngineerBalance(new BigDecimal(0));
                    engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);

                    BillInfo billInfo = new BillInfo();
                    billInfo.setUserId(engineerInfo.getEngineerId());
                    billInfo.setUserName(engineerInfo.getEngineerName());
                    billInfo.setBillDec("提现操作");
                    billInfo.setBillMoney(drawCashInfo.getDrawCashMoney());
                    billInfo.setUserType(1);

                    billInfoMapper.insert(billInfo);
                    return ServerResponse.createBySuccess("提现成功");
                }
                return ServerResponse.createByErrorMessage("操作失败");
            }
        }
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse list(int pageSize, int pageNum, Integer userId, Integer type, Integer state) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("create_time desc");
        List<DrawCashInfo> list = drawCashInfoMapper.list(userId,type,state);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse check(Integer drawCashId) {
        if (drawCashId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        DrawCashInfo drawCashInfo = drawCashInfoMapper.selectByPrimaryKey(drawCashId);
        if (drawCashInfo != null){
            drawCashInfo.setState(Const.Draw.DEAL);
            int row = drawCashInfoMapper.updateByPrimaryKeySelective(drawCashInfo);
            if (row > 0)
                return ServerResponse.createBySuccess("操作成功");
        }
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse getById(Integer drawCashId) {
        if (drawCashId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        DrawCashInfo drawCashInfo = drawCashInfoMapper.selectByPrimaryKey(drawCashId);
        return ServerResponse.createBySuccess(drawCashInfo);
    }


    @Override
    public XSSFWorkbook exportExcelInfo(String startTime, String endTime) {
        if (startTime == null || endTime == null)
            return null;
        try {
            //根据ID查找数据
            Date startDate = DateTimeUtil.strToDate(startTime,"yyyy-MM-dd");
            Date endDate = DateTimeUtil.strToDate(endTime, "yyyy-MM-dd");
            List<DrawCashInfo> drawCashInfoList = drawCashInfoMapper.selectByTime(startDate, endDate);

            XSSFWorkbook xssfWorkbook = null;
            List<ExcelBean> excel = new ArrayList<>();
            Map<Integer,List<ExcelBean>> map = new LinkedHashMap<>();
            //设置标题栏
            excel.add(new ExcelBean("提现记录id","drawCashId",0));
            excel.add(new ExcelBean("用户id","userId",0));
            excel.add(new ExcelBean("用户类型","userType",0));
            excel.add(new ExcelBean("用户名","userName",0));
            excel.add(new ExcelBean(" 提现金额","drawCashMoney",0));
            excel.add(new ExcelBean("提现备注","drawDec",0));
            excel.add(new ExcelBean("账户","drawAccount",0));

            excel.add(new ExcelBean("提现时间","createTime",0));
            map.put(0, excel);
            String sheetName ="drawCash";
            xssfWorkbook = ExcelUtil.createExcelFile(DrawCashInfo.class, drawCashInfoList, map, sheetName);
            return xssfWorkbook;
        }catch (Exception e){
            return null;
        }
    }
}
