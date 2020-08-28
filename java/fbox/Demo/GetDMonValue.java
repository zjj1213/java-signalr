package fbox.Demo;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import fbox.ConsoleLoggerFactory;
import fbox.ServerCaller;
import fbox.StaticCredentialProvider;
import fbox.TokenManager;

import java.io.IOException;

/**
 * @version 1.0
 * @Description: 获取监控点值接口
 * @author: 曾晶晶
 * @Date: 2020/6/17 15:05
 * @Content-Type:　 application/json
 * @请求方式： Ｐost
 * @接口地址: 
 */
public class GetDMonValue {


    public static void main(String[] args) {

        ConsoleLoggerFactory loggerFactory = new ConsoleLoggerFactory();


        TokenManager tokenManager = new TokenManager(new StaticCredentialProvider(Global.clientId, Global.clientSecret, Global.username, Global.password), Global.idServerUrl, loggerFactory);
        ServerCaller commServer = new ServerCaller(tokenManager, Global.commServerApiUrl, Global.signalrClientId, loggerFactory);
        ServerCaller appServer = new ServerCaller(tokenManager, Global.appServerApiUrl, Global.signalrClientId, loggerFactory);
        ServerCaller hdataServer = new ServerCaller(tokenManager, Global.hdataServerApiUrl, Global.signalrClientId, loggerFactory);

        Global.commServer = commServer;
        Global.appServer = appServer;
        Global.hdataServer = hdataServer;

        try {
            JSONObject obj = new JSONObject();
            obj.put("names",new String[]{"ggg"});
            obj.put("groupnames",new String[]{"ttt"});

            System.out.println(obj);

            JsonArray objects = Global.commServer.executePost(String.format("v2/dmon/value/get?boxNo=%s", "300015050009"), obj, JsonArray.class);

            System.out.println(objects);


        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    }
