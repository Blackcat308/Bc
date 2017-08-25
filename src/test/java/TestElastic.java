import com.bc.study.dao.UserDao;
import com.bc.study.elasticsearch.ESUtiles;
import com.bc.study.entity.User;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.profile.ProfileShardResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BlackCat.
 * Date 2017/8/23.
 * Time 17:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class TestElastic {

    @Resource
    private UserDao dao;

    /**
     * @Description: 与本地数据库同步
     * @ClassName: testPagehelper
     * @Param: []
     * @Return: void
     * @Author: BlackCat
     * @DateTime: 2017/8/23 18:27
     */
    @Test
    public void testESAsyncToJDBC(){
        Client esClient = ESUtiles.getESClient();
        List<User> userList = dao.getList();
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            map.put("userId",user.getUserId());
            map.put("userName",user.getUserName());
            map.put("userAge",user.getUserAge());
            map.put("userSex",user.getUserSex());
            IndexResponse response =
                    esClient.prepareIndex().setIndex(ESUtiles.getIndexName())
                    .setType(ESUtiles.getTypeName())
                    .setSource(map).setId(user.getUserId().toString())
                    .execute().actionGet();

            System.out.println("添加成功"+response.toString());
        }
        ESUtiles.closeClient(esClient);
    }

    /**
     * @Description:es搜索相应信息显示为高亮
     * @ClassName: TestESHighlight
     * @Param: []
     * @Return: void
     * @Author: BlackCat
     * @DateTime: 2017/8/23 19:11
     */
    @Test
    public void TestESHighlight(){
        Client client = ESUtiles.getESClient();

        MatchQueryBuilder matchQuery =
                QueryBuilders.matchQuery("userName", "1");

        HighlightBuilder builder = new HighlightBuilder();
        builder.preTags("<h2>");
        builder.preTags("</h2>");
        builder.field("userName");

        SearchResponse response =
                client.prepareSearch("navicat_test_user")
                .setQuery(matchQuery)
                .highlighter(builder)
                .execute().actionGet();
       System.out.println(response.toString());
        SearchHits hits = response.getHits();
        System.out.println(hits.getHits());
       // System.out.println("共搜到："+hits.getTotalHits()+"条结果！");
        for (SearchHit hit:hits
                ) {
            System.out.println(hit.getSource());
            System.out.println("Map方式打印高亮内容");
            System.out.println(hit.getHighlightFields());
            System.out.println("遍历高亮集合，打印高亮片段");
            Text[] texts = hit.getHighlightFields().get("userName").getFragments();
            System.out.println(texts.toString());
            for (Text text:texts
                    ) {
                System.out.println(text.string());
            }
        }
        ESUtiles.closeClient(client);
    }

}
