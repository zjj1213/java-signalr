package fbox.Demo;

import com.alibaba.fastjson.JSONObject;
import fbox.ConsoleLoggerFactory;
import fbox.ServerCaller;
import fbox.StaticCredentialProvider;
import fbox.TokenManager;

/**
 * @version 1.0
 * @Description:  获取历史记录数据接口
 * @author: 曾晶晶
 * @Date: 2020/6/17 15:07
 * @Content-Type:  application/json
 * @请求方式：   Post
 * @接口地址： 
 */
public class GetHdata {


    public static void main(String[] args) {

        ConsoleLoggerFactory loggerFactory = new ConsoleLoggerFactory();

        TokenManager tokenManager = new TokenManager(new StaticCredentialProvider(Global.clientId, Global.clientSecret, Global.username, Global.password), Global.idServerUrl, loggerFactory);

        ServerCaller commServer = new ServerCaller(tokenManager, Global.commServerApiUrl, Global.signalrClientId, loggerFactory);
        ServerCaller appServer = new ServerCaller(tokenManager, Global.appServerApiUrl, Global.signalrClientId, loggerFactory);
        ServerCaller hdataServer = new ServerCaller(tokenManager, Global.hdataServerApiUrl, Global.signalrClientId, loggerFactory);

        Global.commServer = commServer;
        Global.appServer = appServer;
        Global.hdataServer = hdataServer;



        try{

            JSONObject json =new JSONObject();
            json.put("type",0);
            json.put("format",0);
            json.put("g",0);
            json.put("begin","1591770780000");
            json.put("end","1591771320000");
            json.put("names",new String[]{"ccccc"});
            json.put("ids",new String[]{"179845263443516025"});
            System.out.println(json);

            JSONObject  object = Global.hdataServer.executePost(String.format("/v2/hdata/get"),json, JSONObject.class);
            System.out.println(object);

        }catch (Exception e){

            e.printStackTrace();
        }



    }
}