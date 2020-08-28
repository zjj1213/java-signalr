package fbox.Demo;

import com.alibaba.fastjson.JSONObject;
import fbox.ConsoleLoggerFactory;
import fbox.ServerCaller;
import fbox.StaticCredentialProvider;
import fbox.TokenManager;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Description: 监控点值写入
 * @author: 曾晶晶
 * @Date: 2020/6/18 17:26
 * @Content-Type: application/json
 * @请求方式：Post
 * @接口地址: 
 */
public class WriteValue {


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

         JSONObject  obj =new JSONObject();
           obj.put("name","fff");
           obj.put("groupname","默认组");
           obj.put("type",0);
           obj.put("value","13");
         System.out.println(obj);


//        JsonObject obj1=new JsonObject();
//        obj1.put("name","监控点3");
//        obj1.put("groupname","默认组");
//        obj1.put("value",1);
//        obj1.put("type","0");

//        obj.put("names", new String[]{"4X1","4X1","4X1","4X1"});
//        obj.put("groupnames", new String[]{"分组1","分组2","分组3","报警"});
//        obj.put("ids",new String[]{"181672979458953271","181672979458953249","181672979457904665","181672979462099037"});

//        SimpleDateFormat df = new SimpleDateFormat("  yyyy-MM-dd HH:mm:ss:SSS");//设置日期格式
//        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

         JSONObject ret=Global.commServer.executePost(String.format("v2/dmon/value?boxNo=%s","123319070003"),obj,JSONObject.class);

         System.out.println(ret);

     }catch (Exception e){

           e.printStackTrace();
     }


    }

}
