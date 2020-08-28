package fbox.Demo;

import fbox.ConsoleLoggerFactory;
import fbox.ServerCaller;
import fbox.StaticCredentialProvider;
import fbox.TokenManager;

/**
 * @version 1.0
 * @Description:   获取FBox监控点列表接口
 * @author: 曾晶晶
 * @Date: 2020/6/17 16:06
 * @Content-Type:  application/json
 * @请求方式：  GET
 * @接口地址 ：
 */
public class GetDmondata {


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

            String ret = Global.commServer.executeGet(String.format("v2/","57657"),String.class);
            System.out.println(ret);
;
        }catch (Exception e){

             e.printStackTrace();

        }

    }


}
