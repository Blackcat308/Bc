package com.bc.study.controller;

import com.bc.study.elasticsearch.ESUtiles;
import com.bc.study.entity.User;
import com.bc.study.service.UserService;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
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

    /**
     * @Description: 显示用户列表
     * @ClassName: getList
     * @Param: [response, request, page, pageSize]
     * @Return: java.lang.String
     * @Author: BlackCat
     * @DateTime: 2017/8/23 20:29
     */
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

    /**
     * @Description: 去搜索页面
     * @ClassName: toSearch
     * @Param: [request, response]
     * @Return: java.lang.String
     * @Author: BlackCat
     * @DateTime: 2017/8/23 20:46
     */
    @RequestMapping("toSearch")
    public String toSearch(HttpServletRequest request,HttpServletResponse response,
                            @RequestParam(required = false,defaultValue = "*") String index){

        List<User> userList = service.toSearch(index);
        request.setAttribute("list",userList);
        return "search";
    }

    /**
     * @Description: 根据前台传入参数查询相应值返回高亮
     * @ClassName: getSearch
     * @Param: [request, response, index]
     * @Return: java.lang.String
     * @Author: BlackCat
     * @DateTime: 2017/8/23 20:32
     */
    @RequestMapping("Search")
    public String getSearch(HttpServletRequest request,HttpServletResponse response,
                            @RequestParam(required = false,defaultValue = "*") String index){

        return null;
    }
}
