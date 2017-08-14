package com.bc.study.dao;

import com.bc.study.entity.User;

import java.util.List;

/**
 * Created by BlackCat.
 * Date 2017/8/12.
 * Time 18:13
 */
public interface UserDao {
    List<User> getList();
}
