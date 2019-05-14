package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.NoticeMapper;
import com.pojo.Notice;
import com.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by upupgogogo on 2019/5/14.下午2:15
 */
public class NoticeServiceImpl implements NoticeService{

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public ServerResponse addNotice(Notice notice) {
        if (notice == null || notice.getNoticeTitle() == null || notice.getNoticeContent() == null || notice.getNoticeType() == null)
            return ServerResponse.createByErrorMessage("部分参数为空");

        if (notice.getNoticeType() != Const.Notice.CUSTOMER && notice.getNoticeType() != Const.Notice.ENGINEER)
            return ServerResponse.createByErrorMessage("公告所属类型传参有误");

        int row = noticeMapper.insert(notice);
        if (row > 0)
            return ServerResponse.createBySuccess("新增公告成功");
        return ServerResponse.createByErrorMessage("新增公告失败");
    }

    @Override
    public ServerResponse deleteNotice(Integer noticeId) {
        if (noticeId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
        if (notice == null)
            return ServerResponse.createByErrorMessage("找不到该公告");

        int row = noticeMapper.deleteByPrimaryKey(noticeId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse updateNotice(Notice notice) {
        if (notice == null || notice.getNoticeId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        int row = noticeMapper.updateByPrimaryKeySelective(notice);
        if (row > 0)
            return ServerResponse.createBySuccess("更新成功");
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse list(Integer type, int pageSize, int pageNum) {
        return null;
    }

    @Override
    public ServerResponse getById(Integer notice) {
        return null;
    }
}
