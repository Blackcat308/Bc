package com.bc.study.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by BlackCat.
 * Date 2017/8/22.
 * Time 16:53
 */
public class Es5Highlight {

    /**
     * @Description: 搜索相应信息显示高亮
     * @ClassName: main
     * @Param: [args]
     * @Return: void
     * @Author: BlackCat
     * @DateTime: 2017/8/23 18:31
     */
    public static void main(String[] args) throws UnknownHostException {

        Settings settings =
                Settings.builder()
                        .put("cluster.name", "my-application")
                        .build();


        TransportClient client =
                new PreBuiltTransportClient(settings).addTransportAddress(
                new InetSocketTransportAddress(
                        InetAddress.getByName("192.168.1.178"), 9300
                )
        );

        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("title", "*");

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<h2>");
        highlightBuilder.preTags("</h2>");
        highlightBuilder.field("title");

        SearchResponse response = client.prepareSearch("blog")
                .setQuery(matchQuery)
                .highlighter(highlightBuilder)
                .execute().actionGet();

        SearchHits hits = response.getHits();
        System.out.println("共搜到："+hits.getTotalHits()+"条结果！");

        for (SearchHit hit:hits
             ) {
            System.out.println("String方式打印文档搜索内容：");
            System.out.println(hit.getSourceAsString());
            System.out.println("Map方式打印高亮内容");
            System.out.println(hit.getHighlightFields());

            System.out.println("遍历高亮集合，打印高亮片段");
            Text[] texts = hit.getHighlightFields().get("title").getFragments();
            for (Text text:texts
                 ) {
                System.out.println(text.string());
            }
        }
    }
}
