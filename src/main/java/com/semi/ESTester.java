package com.semi;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class ESTester {

    private static final Logger log = Logger.getLogger(ESTester.class.getName());

    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("client.transport.sniff", true)
                .put("cluster.name", "elasticsearch")
                .build();

        Client client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        log.info("Connected successfully to elasticsearch!");

        String jsonObject = "{\"age\":21,\"dateOfBirth\":1546304461,"
                +"\"fullName\":\"Sulaymon Hursanov\"}";

        IndexResponse response = client.prepareIndex("people", "Doe")
                .setSource(jsonObject, XContentType.JSON).get();

        String id = response.getId();
        String index = response.getIndex();
        String type = response.getType();
        long version = response.getVersion();
        log.info("Response result "+response.getResult());
        log.info("Version : " +version);
        log.info("Index "+ index);
        log.info("Type " +type);
        log.info("id = " + id);
    }
}
