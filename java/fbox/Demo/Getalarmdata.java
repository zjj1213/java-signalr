package fbox.Demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fbox.ConsoleLoggerFactory;
import fbox.ServerCaller;
import fbox.StaticCredentialProvider;
import fbox.TokenManager;

/**
 * @version 1.0
 *
 * @author: 曾晶晶
 * @Date: 2020/6/19 14:41
 * @Content-Type: application/json
 * @Description1: 获取报警分组列表
 * @请求方式： POST
 * @接口地址1 ：
 *
 *
 *
 * @Description2: 获取报警联系人列表
 * @请求方式： GET
 * @接口地址2： 
 *
 *
 *
 * @Description3: 获取报警条目列表
 * @请求方式： GET
 * @接口地址3 :
 *
 *
 * @Description4: 获取某些报警条目
 * @请求方式： POST
 * @接口地址4： 
 *
 */
public class Getalarmdata {

    public static void main(String[] args) throws Exception {


        ConsoleLoggerFactory loggerFactory = new ConsoleLoggerFactory();

        TokenManager tokenManager = new TokenManager(new StaticCredentialProvider(Global.clientId, Global.clientSecret, Global.username, Global.password), Global.idServerUrl, loggerFactory);

        ServerCaller commServer = new ServerCaller(tokenManager, Global.commServerApiUrl, Global.signalrClientId, loggerFactory);
        ServerCaller appServer = new ServerCaller(tokenManager, Global.appServerApiUrl, Global.signalrClientId, loggerFactory);
        ServerCaller hdataServer = new ServerCaller(tokenManager, Global.hdataServerApiUrl, Global.signalrClientId, loggerFactory);

        Global.commServer = commServer;
        Global.appServer = appServer;
        Global.hdataServer = hdataServer;


        try {


            /**
             *  获取报警分组列表
             */
                 JSONObject jsonObject=new JSONObject();
                 jsonObject.put("boxId","129");

                 JSONArray ret1=Global.commServer.executePost(String.format("v2/alarm/group/get"),jsonObject,JSONArray.class);
                 System.out.println(ret1);

            /**
             *获取报警联系人列表
             */

            JSONArray ret2 = Global.commServer.executeGet(String.format("v2/contacts"), JSONArray.class);
            System.out.println(ret2);


            /**
             * 获取报警条目列表
             */

            JSONArray ret3=Global.commServer.executeGet(String.format("v2=%s","300015050009"),JSONArray.class);
            System.out.println(ret3);


            /**
             *获取某些报警条目
             */



        } catch (Exception e) {

            e.printStackTrace();

        }


    }


}
