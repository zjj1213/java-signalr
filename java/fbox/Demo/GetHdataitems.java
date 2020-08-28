package fbox.Demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fbox.ConsoleLoggerFactory;
import fbox.ServerCaller;
import fbox.StaticCredentialProvider;
import fbox.TokenManager;
import fbox.models.GetByChannelHdataArgs;
import fbox.models.HdataChannelDef;
import fbox.models.HdataDef;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @version 1.0
 * @author: 曾晶晶
 * @Date: 2020/6/19 9:39
 * @Content-Type:  application/json
 * @Description1: 获取历史记录条目接口
 * @请求方式1：  GET
 * @接口地址 1：
 *
 *
 * @Description2: 获取某些历史记录条目接口
 * @请求方式 2： POST
 * @接口地址 2： 
 */
public class GetHdataitems {


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

            //返回的是用户组对象
            HdataDef[] hdataDefs =Global.commServer.executeGet(String.format("v2/hdataitems?boxNo=%s","300015050009"), HdataDef[].class);
            //用户组对象转json串
           String json=JSON.toJSONString(hdataDefs);
            System.out.println(json);

           JSONObject tosjson =JSON.parseObject(json);
            System.out.println(tosjson);


//            List<HdataChannelDef> channels= Arrays.stream(hdataDefs).flatMap(x -> Arrays.stream(x.channels)).collect(Collectors.toList());
//            List<Long> channelIds = channels.stream().map(c -> c.uid).collect(Collectors.toList());
//            List<String> channelNames = channels.stream().map(c -> c.name).collect(Collectors.toList());
//            System.out.println("channels"+channels+"channelIds"+channelIds+"channelNames"+channelNames);

            /**
             *获取历史记录条目接口
             */
//            JSONArray ret=Global.commServer.executeGet(String.format("v2/hdataitems?boxNo=%s","300015050009"),JSONArray.class);
//            System.out.println(ret);

            /**
             * 获取某些历史记录条目接口
             */
//            JSONObject jsonObject=new JSONObject();
//            jsonObject.put("names",new String[]{"x"});
//
//            JSONArray ret1 =Global.commServer.executePost(String.format("v2/hdataitems/get?boxNo=%s","300015050009"),jsonObject,JSONArray.class);
//            System.out.println(ret1);





        }catch (Exception e){
            e.printStackTrace();
        }




    }












}
