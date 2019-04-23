package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.EngineerInfo;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * Created by upupgogogo on 2018/11/14.下午4:35
 */
public interface EngineerInfoService {

    ServerResponse<EngineerInfo> register(EngineerInfo engineerInfo, MultipartFile file, String path);

    ServerResponse<PageInfo> list(int pageSize, int pageNum, Integer state);

    ServerResponse<EngineerInfo> getEngineerInfoById(Integer engineerId);

    ServerResponse check(EngineerInfo engineerInfo);

    ServerResponse<EngineerInfo> login(String engineerName, String password);

    ServerResponse update(EngineerInfo engineerInfo);

    ServerResponse getPassword(EngineerInfo engineerInfo);

    ServerResponse comfirmUserNameAndEmail(EngineerInfo engineerInfo);

    ServerResponse<PageInfo> selectByIdLike(int pageNum, int pageSize, Integer engineerId);

    ServerResponse<PageInfo> selectByPhoneLike(int pageNum, int pageSize, String phone);

    ServerResponse deductOrAddMoney(Integer type, BigDecimal price, Integer engineer);

    ServerResponse adminCheck(Integer adminCheck, Integer engineerId);


    ServerResponse selectDetailById(Integer engineerId);


}
