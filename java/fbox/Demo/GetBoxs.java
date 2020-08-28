package fbox.Demo;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import fbox.ConsoleLoggerFactory;
import fbox.ServerCaller;
import fbox.StaticCredentialProvider;
import fbox.TokenManager;

/**
 * @version 1.0
 * @Description:  获取FBox列表获取
 * @author: 曾晶晶
 * @Date: 2020/6/17 15:33
 * @Content-Type:   application/json
 * @请求方式： GET
 */
public class GetBoxs {

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

                 JsonArray ret = Global.appServer.executeGet(String.format("/api/client/box/grouped"),JsonArray.class);
                  System.out.println(ret);

              }catch (Exception e){
                         e.printStackTrace();

              }



    }












}
