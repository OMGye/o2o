package com.service;

import com.common.ServerResponse;
import com.pojo.DrawCashInfo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by upupgogogo on 2018/12/7.下午2:24
 */
public interface DrawCashInfoService {

    ServerResponse add(DrawCashInfo drawCashInfo, Integer userId, Integer type, String userName);

    ServerResponse list(int pageSize, int pageNum, Integer userId, Integer type, Integer state);


    ServerResponse check(Integer drawCashId);

    ServerResponse getById(Integer drawCashId);

    XSSFWorkbook exportExcelInfo(String startTime, String endTime);
}
