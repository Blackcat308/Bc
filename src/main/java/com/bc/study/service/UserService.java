package com.bc.study.service;

import java.util.Map;

/**
 * Created by BlackCat.
 * Date 2017/8/12.
 * Time 18:10
 */
public interface UserService {
    Map<Object, Object> getList(Integer page, Integer pageSize);
}
