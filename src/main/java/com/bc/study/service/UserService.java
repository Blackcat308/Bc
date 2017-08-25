package com.bc.study.service;

import com.bc.study.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by BlackCat.
 * Date 2017/8/12.
 * Time 18:10
 */
public interface UserService {
    Map<Object, Object> getList(Integer page, Integer pageSize);

    List<User> toSearch(String index);
}
