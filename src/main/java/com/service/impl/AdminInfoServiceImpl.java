package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.AdminInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.AdminInfo;
import com.pojo.BasicPriceInfo;
import com.service.AdminInfoService;
import com.sun.org.apache.bcel.internal.generic.I2F;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/12.下午4:10
 */
@Service("adminInfoService")
public class AdminInfoServiceImpl implements AdminInfoService {

    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Override
    public ServerResponse<AdminInfo> login(AdminInfo adminInfo) {
        if (adminInfo == null || adminInfo.getAdminName() == null || adminInfo.getPassword() == null)
            return ServerResponse.createByErrorMessage("您未输入账户或密码");
        System.out.printf(adminInfo.getAdminName());
        AdminInfo curAdminInfo = adminInfoMapper.selectByUserNameAndPassword(adminInfo.getAdminName(),adminInfo.getPassword());
        if (curAdminInfo == null)
            return ServerResponse.createByErrorMessage("账号或密码错误");

        return ServerResponse.createBySuccess(curAdminInfo);
    }

    @Override
    public ServerResponse rePassword(Integer id, String newPassword) {

        AdminInfo adminInfo = adminInfoMapper.selectByPrimaryKey(id);
        adminInfo.setPassword(newPassword);
        int row = adminInfoMapper.updateByPrimaryKeySelective(adminInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse addUser(AdminInfo adminInfo) {
        if (adminInfo == null || adminInfo.getPassword() == null|| adminInfo.getAdminName() == null)
            return ServerResponse.createByErrorMessage("部分参数不能为空");

        if (adminInfo.getAdminRank() != Const.AdminRank.BOSS && adminInfo.getAdminRank() != Const.AdminRank.EMPLOY && adminInfo.getAdminRank() != Const.AdminRank.MANAGER)
            return ServerResponse.createByErrorMessage("权限设置错误");

        int row = adminInfoMapper.insert(adminInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("添加成功");

        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse deleteUser(Integer adminId) {
        if (adminId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        int row = adminInfoMapper.deleteByPrimaryKey(adminId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse updateUser(AdminInfo adminInfo) {
        if (adminInfo == null || adminInfo.getAdminId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        int row = adminInfoMapper.updateByPrimaryKeySelective(adminInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("更新成功");
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("admin_id desc");
        List<AdminInfo> list = adminInfoMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse getById(Integer adminId) {
        if (adminId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        AdminInfo adminInfo = adminInfoMapper.selectByPrimaryKey(adminId);
        return ServerResponse.createBySuccess(adminInfo);
    }
}
