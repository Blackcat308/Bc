package com.bc.study.service.impl;

import com.bc.study.dao.UserDao;
import com.bc.study.elasticsearch.ESUtiles;
import com.bc.study.entity.User;
import com.bc.study.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<User> toSearch(String index) {
        Client client = ESUtiles.getESClient();
        MatchQueryBuilder matchQuery =
                QueryBuilders.matchQuery("userName", index);

        HighlightBuilder builder = new HighlightBuilder();
        builder.preTags("<span style=\"color: #de1818\">");
        builder.preTags("</span>");
        builder.field("userName");

        SearchResponse response =
                client.prepareSearch(ESUtiles.getIndexName())
                .setQuery(matchQuery)
                .highlighter(builder)
                .execute().actionGet();

        SearchHits hits = response.getHits();
        List<User> list = new ArrayList<User>();  //搜索出相应信息集合
        for (SearchHit hit:hits
             ) {
            Map<String, Object> source = hit.getSource();
            Integer userId = (Integer) source.get("userId");
            String userName = (String) source.get("userName");
            String userSex = (String) source.get("userSex");
            Integer userAge = (Integer) source.get("userAge");
            User user = new User();
            user.setUserId(userId);
            user.setUserName(userName);
            user.setUserAge(userAge);
            user.setUserSex(userSex);
            list.add(user);
        }
        return list;
    }
}
