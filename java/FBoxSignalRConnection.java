import com.github.signalr4j.client.Logger;
import com.github.signalr4j.client.hubs.HubProxy;
import com.google.gson.*;

import fbox.LoggerFactory;
import fbox.TokenManager;
import fbox.models.BoxStateChanged;
import fbox.signalr.SignalRConnectionBase;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class FBoxSignalRConnection extends SignalRConnectionBase {
    private final Gson gson;
    private final fbox.Logger logger;
    ConcurrentHashMap<Long, LongAdder> dmonIds = new ConcurrentHashMap<>();    //多少监控点

    private long lastDmonItemCount;
    private long lastDmonMsgCount;
    private long lastReportTime;
    private Proxy proxy;

    private LongAdder dmonItemCounter = new LongAdder();   //并发环境计数器
    private LongAdder dmonMsgCounter = new LongAdder();    //并发环境计数器



    public FBoxSignalRConnection(String hubUrl, String signalrClientId, TokenManager tokenManager, Proxy proxy, LoggerFactory loggerFactory) {

        super(hubUrl, signalrClientId, tokenManager, proxy, loggerFactory);

        this.logger = loggerFactory.createLogger("FBoxSignalRConnection");

        this.proxy = proxy;

        gson = new GsonBuilder().create();
        new Thread(() -> {
            //统计条目数线程
            for (; ; ) {
                try {
                    Thread.sleep(5000);       //线程睡眠5秒
                } catch (InterruptedException e) {  //捕获异常
                    e.printStackTrace();             //抛出异常
                }
                long currentTime = System.nanoTime();   //纳秒，一秒的10亿分之一，即等于10的负9次方秒。1纳秒=0.000001 毫秒
                long currentMsgCount = this.dmonMsgCounter.longValue();
                long currentItemCount = this.dmonItemCounter.longValue();
                long msgRate = (currentMsgCount - this.lastDmonMsgCount) * 1000000000 / (currentTime - this.lastReportTime);
                long itemRate = (currentItemCount - this.lastDmonItemCount) * 1000000000 / (currentTime - this.lastReportTime);
                this.logger.logInformation(String.format("Dmon id count: %d, item rate: %d, message rate: %d", this.dmonIds.size(), itemRate, msgRate));
                this.lastReportTime = currentTime;
                this.lastDmonMsgCount = currentMsgCount;
                this.lastDmonItemCount = currentItemCount;
            }
        }).start();
    }

    @Override
    public void connected() {
        super.connected();
        dmonIds.clear();
    }

    protected void onHubProxyDestroyed(HubProxy hubProxy){
        hubProxy.removeSubscription("dmonUpdateValue");    //服务器代理销毁这个订阅的实时数据推送事件
        hubProxy.removeSubscription("alarmTriggered");     //服务器代理销毁这个订阅的状态报警触发推送事件
        hubProxy.removeSubscription("alarmRecovered");     //服务器代理销毁这个订阅的报警还原推送事件
        hubProxy.removeSubscription("boxConnStateChanged"); //服务器代理销毁这个订阅的状态变更推送事件
    }
      //格式化时间
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


    @Override
    protected void onHubProxyCreated(HubProxy hubProxy) {
        //signalr实时数据推送事件，接收此事件数据前提条件，开启监控点数据推送控制接口（订阅）
        hubProxy.subscribe("dmonUpdateValue").addReceivedHandler(jsonElements -> {
            Global.threadPool.submit(() -> {
                    this.dmonMsgCounter.increment();
                    JsonArray items = jsonElements[1].getAsJsonArray();

//                    System.out.println(items);

                    String timestamp = "";
                    JsonArray array = new JsonArray();
                    for (JsonElement jsonElement : items) {
                        JsonObject object = new JsonObject();
                        JsonObject item = jsonElement.getAsJsonObject();
                        this.dmonIds.computeIfAbsent(item.get("id").getAsLong(), aLong -> new LongAdder()).increment();
                        this.dmonItemCounter.increment();

                        //私有云的输出的数据和公有云输出的数据不一致。

                        //私有云输出
//                        System.out.println(item);


                        //公有云输出

                        //收到的推送数据
                        String uid =item.get("id").getAsString();
                        String boxid=item.get("msg").getAsString();
                        String name = item.get("name").getAsString();
                        String value = item.get("value").getAsString();
                        long time = item.get("t").getAsLong();
                        String groupid=item.get("gid").getAsString();
                        String groupname=item.get("gname").getAsString();

                        timestamp = String.valueOf(sdf.format(time));
                        System.out.println("时间:"+sdf.format(new Date())+" ,监控点id: "+uid+",盒子号: "+boxid+",名称: "+name+",监控点值:"+value+",监控点分组id:"+groupid+",监控点分组名称:"+ groupname+",time:"+time+",timestamp:"+timestamp);

//                        SimpleDateFormat df = new SimpleDateFormat(" 系统时间：  yyyy-MM-dd HH:mm:ss:SSS");//设置日期格式
//                        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

                    };
//                  打印监控点的值集合，集合详细信息请看接口文档http://docs.flexem.net/fbox/zh-cn/tutorials/RealtimeDataPush.html
//                    System.out.printf("%s",jsonElements[1].getAsJsonArray());

            });
        });


         //signalr报警触发事件
//        hubProxy.subscribe("alarmTriggered").addReceivedHandler(jsonElements -> {
//            Global.threadPool.submit(() -> {
//                System.out.println("Alarm triggered: ");
//                for (com.google.gson.JsonElement jsonElement : jsonElements) {
//                    //报警推送消息全部打印。具体参数解释请看接口文档http://docs.flexem.net/fbox/zh-cn/tutorials/AlarmTiggerPush.html
//                    //System.out.println("\t" + jsonElement + "=============");
//                };
//                //System.out.println("\t" + jsonElements[1] + "++++++++");
////                System.out.printf("%s\t",jsonElements[0].getAsLong());
////                //打印报警条目的值集合
//               System.out.printf("%s\t",jsonElements[1].getAsJsonArray());
////                //打印boxUid
//                System.out.printf("%s\t",jsonElements[2].getAsString());
//            });
//        });

       //  signalr报警还原事件
//        hubProxy.subscribe("alarmRecovered").addReceivedHandler(jsonElements -> {
//            Global.threadPool.submit(() -> {
//                System.out.println("Alarm recovered: ");
//                for (com.google.gson.JsonElement jsonElement : jsonElements) {
//                    //报警推送消息全部打印。具体参数解释请看接口文档http://docs.flexem.net/fbox/zh-cn/tutorials/AlarmReductionPush.html
//                    //System.out.println("\t" + jsonElement);
//                };
//                //打印报警条目的值集合
//                System.out.printf("%s\t",jsonElements[1].getAsJsonArray());
////                //打印boxUid
////                System.out.printf("%d",jsonElements[2].getAsLong());
//            });
//        });
        // signalr盒子状态变更事件
        hubProxy.subscribe("boxConnStateChanged").addReceivedHandler(jsonElements -> {
            Global.threadPool.submit(() -> {
                System.out.println("Box state changed..................................");
                if (jsonElements.length <= 0)
                    return;
                BoxStateChanged[] stateChanges = gson.fromJson(jsonElements[0], BoxStateChanged[].class);
                this.logger.logInformation(String.format("receive count: %d", stateChanges.length));
                for (BoxStateChanged stateChange : stateChanges) {
                    // stateChange.state 为1、2是盒子上线事件。实时数据推送需要开点
                    //  System.out.printf("输出下",jsonElements[0].getAsJsonArray().toString());
                    if (stateChange.state == 1 || stateChange.state == 2) {
                        try {
                            // 盒子每次上线后，均需要开启FBox数据推送控制接口（订阅）
                          Global.commServer.executePost("box/" + stateChange.id + "/dmon/start", String.class);

                                // token有效期为两小时。若token过期后，demo会自动刷新token。
                                System.out.println("输出:"+ stateChange.id +"盒子状态:"+ stateChange.state);   //盒子id,多个盒子多个id,及盒子的状态

                        } catch (IOException e) {
//                            try {
//                                Global.commServer.executePost("box/" + stateChange.id + "/dmon/start", String.class);
//                            } catch (IOException e1) {
//                                System.out.printf("%s\n", stateChange.id);
//                            }
                            System.out.println(e);
                            e.printStackTrace();
                        }
                    }
                }
            });
        });


    }
}