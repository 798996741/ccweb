package com.yulun.controller.peechToText;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONObject;

import javax.jms.*;
import java.net.URLDecoder;

public class shengchan {

    public static  final String Activemq_URL="tcp://10.30.2.42:61616";
    public static  final String QUEUE_NAME="peechtotext";

    public static void main(String[] args) throws Exception {
        /*System.out.println("生产者开始生产");
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory("admin","admin",Activemq_URL);
        Connection connection=  activeMQConnectionFactory.createConnection();
        connection.start();
        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue=session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer=session.createProducer(queue);

        for (int i=1;i<=3;i++){
            JSONObject json=new JSONObject();
            json.put("textType","0");
            json.put("phoneId","50"+i);
            json.put("callRecord","你好50"+i);
            TextMessage textMessage=session.createTextMessage(json.toString());
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息发送完毕！");*/
        String str="\u6d77\u53e3\u98de\u5f80\u5317\u4eac";
        System.out.println(URLDecoder.decode(str, "UTF-8"));
    }
}
