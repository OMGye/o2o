package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.CustomerInfo;
import com.pojo.EngineerInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by upupgogogo on 2018/11/17.下午1:42
 */
public interface CustomerInfoService {

    ServerResponse register(CustomerInfo customerInfo);

    ServerResponse<PageInfo> list(int pageSize, int pageNum, Integer state);

    ServerResponse<CustomerInfo> getEngineerInfoById(Integer customerId);

    ServerResponse<CustomerInfo> login(CustomerInfo customerInfo);

    ServerResponse update(CustomerInfo customerInfo);

    ServerResponse getPassword(CustomerInfo customerInfo);

    ServerResponse comfirmUserNameAndEmail(CustomerInfo customerInfo);

    ServerResponse check(CustomerInfo customerInfo);

    ServerResponse engineerDefriend(Integer customerId, Integer engineerId);
}
