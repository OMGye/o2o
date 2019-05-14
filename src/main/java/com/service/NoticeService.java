package com.service;

import com.common.ServerResponse;
import com.pojo.AdminInfo;
import com.pojo.Notice;

/**
 * Created by upupgogogo on 2019/5/14.下午2:12
 */
public interface NoticeService {


    ServerResponse addNotice(Notice notice);

    ServerResponse deleteNotice(Integer noticeId);

    ServerResponse updateNotice(Notice notice);

    ServerResponse list(Integer type, int pageSize, int pageNum);

    ServerResponse getById(Integer notice);
}
