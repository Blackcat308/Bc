package com.bc.study.elasticsearch;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;


public class TestEsClient {

	  public static void main(String[] args) {
		  // add();//添加
		 // del();//删除
		 // upd();//修改
		  sel();//查询
    }
	 	  //---------------------------------------------------------------------------------------------------------
	  
	  public static void add(){



		  Client client = ESUtiles.getESClient();
		  IndexResponse indexResponse =
		  client.prepareIndex().setIndex(ESUtiles.getIndexName())
		  .setType(ESUtiles.getTypeName())
		  .setSource("{\"prodId\":1,\"prodName\":\"ipad5\",\"prodDesc\":\"比你想的更强大\",\"catId\":1}")
		  .setId("1")
		  .execute()
		  .actionGet();
		  System.out.println("添加成功,isCreated="+indexResponse.toString());
		  ESUtiles.closeClient(client);
	  }
	  
	  public static void del(){
		  Client client = ESUtiles.getESClient();

		  DeleteResponse delResponse =
				  client.prepareDelete().setIndex(ESUtiles.getIndexName())
				  .setType(ESUtiles.getTypeName())
				  .setId("1")
				  .execute()
				  .actionGet();
				  System.out.println("del is found="+delResponse.toString());
	  }
	  
	  public static void upd(){
		  Client client = ESUtiles.getESClient();

		  GetResponse getResponse =
				  client.prepareGet().setIndex(ESUtiles.getIndexName())
				  .setType(ESUtiles.getTypeName())
				  .setId("1")
				  .execute()
				  .actionGet();
				  System.out.println("berfore update version="+getResponse.getVersion());
				  UpdateResponse updateResponse =
				  client.prepareUpdate().setIndex(ESUtiles.getIndexName())
				  .setType(ESUtiles.getTypeName())
				  .setDoc("{\"prodId\":1,\"prodName\":\"ipad5\",\"prodDesc\":\"比你想的更强大噢耶\",\"catId\":1}")
				  .setId("1")
				  .execute()
				  .actionGet();
				  System.out.println("更新成功，isCreated="+updateResponse.toString());
				  getResponse =
				  client.prepareGet().setIndex(ESUtiles.getIndexName())
				  .setType(ESUtiles.getTypeName())
				  .setId("1")

				   
				  .execute()
				  .actionGet();
				  System.out.println("get version="+getResponse.getVersion());
				  System.out.println("-----upd----ok-----");
	  }
	  
	  public static void sel(){
		  Client client = ESUtiles.getESClient();
		  //初始化查询条件
		  QueryBuilder query = QueryBuilders.matchQuery("prodName", "ipad5");
		  
		  SearchResponse SearchResponseresponse = client.prepareSearch(ESUtiles.getIndexName())
		  //设置查询条件,
		  .setQuery(query)
		  .setFrom(0).setSize(60)
		  .execute()
		  .actionGet();
		  /**
		  * SearchHits是SearchHit的复数形式，表示这个是一个列表
		  */
		  SearchHits shs = SearchResponseresponse.getHits();
		  System.out.println("总共有："+shs.hits().length);
		  for(SearchHit hit : shs){
		  System.out.println(hit.getSourceAsString());
		  }
		  client.close();
	  }

}
