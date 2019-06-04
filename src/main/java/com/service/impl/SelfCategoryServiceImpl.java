package com.service.impl;

import com.common.ServerResponse;
import com.dao.QuantityInfoMapper;
import com.dao.SelfCategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.QuantityInfo;
import com.pojo.SelfCategory;
import com.service.SelfCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by upupgogogo on 2019/5/19.下午2:29
 */
public class SelfCategoryServiceImpl implements SelfCategoryService {

    @Autowired
    private SelfCategoryMapper selfCategoryMapper;
    @Override
    public ServerResponse add(SelfCategory selfCategory) {
        if (selfCategory == null || selfCategory.getSelfCategoryName() == null || selfCategory.getSelfCategoryPrice() == null )
            return ServerResponse.createByErrorMessage("参数不能为空");

        SelfCategory category = selfCategoryMapper.selectByName(selfCategory.getSelfCategoryName());
        if (category != null)
            return ServerResponse.createByErrorMessage("改分类名称已存在");

        int row = selfCategoryMapper.insert(selfCategory);
        if (row > 0)
            return ServerResponse.createBySuccess("新增成功");
        return ServerResponse.createByErrorMessage("新增失败");
    }

    @Override
    public ServerResponse delete(Integer selfCategoryId) {
        if (selfCategoryId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        int row = selfCategoryMapper.deleteByPrimaryKey(selfCategoryId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse update(SelfCategory selfCategory) {
        if (selfCategory == null || selfCategory.getSelfCategoryId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        int row = selfCategoryMapper.updateByPrimaryKeySelective(selfCategory);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("self_category_id desc");
        List<SelfCategory> list = selfCategoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse getById(Integer selfCategoryId) {
        if (selfCategoryId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        SelfCategory selfCategory = selfCategoryMapper.selectByPrimaryKey(selfCategoryId);
        return ServerResponse.createBySuccess(selfCategory);
    }
}
