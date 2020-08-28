import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fbox.ConsoleLoggerFactory;
import fbox.ServerCaller;
import fbox.StaticCredentialProvider;
import fbox.TokenManager;
import fbox.models.*;

import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final long SLEEP_TIME = 3000;
    public static void main(String[] args) {

        ConsoleLoggerFactory loggerFactory = new ConsoleLoggerFactory();

        // 指定连接服务器的凭据参数
        TokenManager tokenManager = new TokenManager(new StaticCredentialProvider(Global.clientId, Global.clientSecret, Global.username, Global.password), Global.idServerUrl, loggerFactory);

        ServerCaller commServer = new ServerCaller(tokenManager, Global.commServerApiUrl, Global.signalrClientId, loggerFactory);
        ServerCaller appServer = new ServerCaller(tokenManager, Global.appServerApiUrl, Global.signalrClientId, loggerFactory);
        ServerCaller hdataServer = new ServerCaller(tokenManager, Global.hdataServerApiUrl, Global.signalrClientId, loggerFactory);

        Global.commServer = commServer;
        Global.appServer = appServer;
        Global.hdataServer = hdataServer;

        //建立signalr实例
        FBoxSignalRConnection fboxSignalR = new FBoxSignalRConnection(Global.commServerSignalRUrl, Global.signalrClientId, tokenManager, Global.proxy, loggerFactory);

        // 连接SignalR推送通道
        fboxSignalR.start();

        System.out.println("Box list:.................................");

        try {
 

        } catch (Exception e) {
            System.out.printf(e.getMessage());
            System.out.printf("Write OK!");
        }


//        Scanner s = new Scanner(System.in);
////        s.nextLine();
//        System.out.println(s);
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "8888");
//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyPort", "8888");

    }
}
