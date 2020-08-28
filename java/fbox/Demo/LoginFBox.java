package fbox.Demo;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *@Description:   登录接口
 *@author: 曾晶晶
 *@Date: 2020/7/13 16:54
 *@Content-Type: application/x-www-form-urlencoded
 *@请求方式：   post
 *@version 1.0
 * @接口地址 : 
 */
public class LoginFBox {

    public static void main(String[] args)throws InterruptedException {

       String url="https://baiud.com/core/connect/token";
       String username="";
       String password="";
       String scope="";
       String client_id="";
       String  client_secret="";
       String grant_type="";

   String param ="username="+username+"&password="+password+"&client_id="+client_id+"&client_secret="+client_secret+"&grant_type="+grant_type+"&scope="+scope;

       try {

           long currentTime=System.currentTimeMillis();

          scope= URLEncoder.encode(scope, "utf-8");

          String ret= HttpRequest.post(url)
                  .timeout(3000)
                  .body(param, ContentType.FORM_URLENCODED.toString())
                  .header("Content-Type","application/x-www-form-urlencoded")
                  .execute()
                  .body();
          long endTime = System.currentTimeMillis();
          long resTime=(endTime - currentTime)/1000;

          System.out.println("耗时:"+resTime +",ret" + ret);





       } catch (UnsupportedEncodingException e) {

          e.printStackTrace();

       }

       System.out.println(".....................................................................");
       System.out.println("输出参数"+param);


    }













}
