package com.bc.study.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by BlackCat.
 * Date 2017/8/23.
 * Time 18:40
 */
public class ESUtiles {
    /**
     * es节点名称
     * 类似数据库的表名
     */
    private static final String INDEX_NAME = "navicat_test_user";
    /**
     *es节点类型
     */
    private static final String TYPE_NAME = "test_user";
    /**
     *固定key
     */
    private static final String CLUSTER_KEY = "cluster.name";
    /**
     *es集群的名称
     *
     * 改为自己es节点的名称
     */
    private static final String CLUSTER_VALUE = "my-application";
    /**
     *es集群的host
     *
     * 改为自己es节点的host
     */
    private static final String ES_HOST = "192.168.1.178";
    /**
     *es集群的端口
     *
     * 改为自己es节点的端口
     */
    private static final Integer ES_PORT = 9300;

    public static String getEsHost() {
        return ES_HOST;
    }
    public static Integer getEsPort() {
        return ES_PORT;
    }
    public static String getClusterKey() {
        return CLUSTER_KEY;
    }
    public static String getClusterValue() {
        return CLUSTER_VALUE;
    }
    public static String getIndexName() {
        return INDEX_NAME;
    }
    public static String getTypeName() {
        return TYPE_NAME;
    }

    /**
     * @Description: 创建es客户端，类似jdbc的Connection对象
     * @ClassName: getESClient
     * @Param: []
     * @Return: org.elasticsearch.client.Client
     * @Author: BlackCat
     * @DateTime: 2017/8/23 18:43
     */
    public static Client getESClient(){
        TransportClient client = null;
        try {
            Settings settings = Settings.builder()
                .put(ESUtiles.getClusterKey(), ESUtiles.getClusterValue())
                .build();

            client =
                    new PreBuiltTransportClient(settings)
                            .addTransportAddress(
                                    new InetSocketTransportAddress(
                                            InetAddress.getByName(ESUtiles.getEsHost()),
                                            ESUtiles.getEsPort()
                                    )
                            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println("---------Create Client SUCCESS-------");

        return client;
    }

    /**
     * @Description:销毁es客户端
     * @ClassName: closeClient
     * @Param: []
     * @Return: void
     * @Author: BlackCat
     * @DateTime: 2017/8/23 18:57
     */
    public static void closeClient(Client client){
        if (client != null)
        {
            client.close();
        }
        System.out.println("-------Close Client SUCCESS-----");
    }

}
