package com.tensquare.sms.listener.玩玩;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;



public class 单机发验证码玩玩 {
    public static void main(String[] args) {

              发一次短信();
             // 连续发短信(10);

    }

    private static void 发一次短信() {
        SmsUtil smsUtil = new SmsUtil();
        // String 想说的话  ="abcdefghijkhjgjhgjhg";//最多发20个字母 不能中文
        String 想说的话     ="happy_birthdayTo_hll";//最多发20个字母
       // String 想说的话     ="ling_I_miss_you";//最多发20个字母
       // String 想说的话     ="happy_birthdayTo_hll";//最多发20个字母

        //String 想说的话     ="a_kai_haiqi_lo_daima";//最多发20个字母
        //
       try {
         // smsUtil.sendSms("18688454920","SMS_164279069","大芦网","{\"code\":\""+想说的话+"\"}");
          smsUtil.sendSms("18688454920","SMS_164279140","灵山网","{\"code\":\""+想说的话+"\"}");
          //
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    public static  void  连续发短信(int a){
           for (int i = 0; i <a; i++) {
            发一次短信();
            System.out.println(i);
           }
    }
}
