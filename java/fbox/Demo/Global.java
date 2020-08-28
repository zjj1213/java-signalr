package fbox.Demo;

import fbox.ServerCaller;

import java.net.Proxy;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Global {
    public static Proxy proxy = null;
    
    public static ExecutorService threadPool = Executors.newCachedThreadPool();
    public static ServerCaller commServer;
    public static ServerCaller appServer;
    public static ServerCaller hdataServer;

     //以下是服务器地址，私有云请根据实际情况修改
   public static final String idServerUrl = "https://account.flexem.com/core/";
    public static String appServerApiUrl = "http://fbox360.com/api/client/";
   public static final String commServerApiUrl = "";
   public static final String commServerSignalRUrl = "http://fbcs101.fbox360.com/push";
   public static String hdataServerApiUrl = "";
   public static String signalrClientId = UUID.randomUUID().toString();

    //FBox客户端注册的账号
    public static String username = "";
    public static String password = "";


    // 获取API账号请咨询对接的销售。
    public static String clientId ="";
    public static String clientSecret ="";

}
