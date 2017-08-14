package com.bc.study.controller;

import com.bc.study.entity.User;
import com.bc.study.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by BlackCat.
 * Date 2017/8/12.
 * Time 18:10
 */
@Controller
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping("list")
    public String getList(HttpServletResponse response, HttpServletRequest request,
    @RequestParam(required = true,defaultValue = "1") Integer page,
    @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        Map<Object, Object> map = service.getList(page, pageSize);
        List<User> userList= (List<User>) map.get("userList");
        PageInfo<User> p = (PageInfo<User>) map.get("page");
        request.setAttribute("userList", userList);
        request.setAttribute("p", p);
        return "list";
    }
}
