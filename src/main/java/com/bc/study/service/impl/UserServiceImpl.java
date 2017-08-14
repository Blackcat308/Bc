package com.bc.study.service.impl;

import com.bc.study.dao.UserDao;
import com.bc.study.entity.User;
import com.bc.study.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BlackCat.
 * Date 2017/8/12.
 * Time 18:10
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao dao;

    @Override
    public Map<Object, Object> getList(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<User> userList = dao.getList();
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        HashMap<Object, Object> hashMap = new HashMap<>();

        hashMap.put("userList",userList);
        hashMap.put("page",userPageInfo);
        return hashMap;
    }
}
