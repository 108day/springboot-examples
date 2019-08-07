/*
package com.example.demo;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginUtil {

    //模拟登录方式2
    public static void postForm(String uri,
                                String LoginID,
                                String Password,
                                String __RequestVerificationToken,
                                String  CaptchaInputText,
                                String CaptchaDeText,
                                String SkipTradeAgreement,
                                String cookie){
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("__RequestVerificationToken", __RequestVerificationToken);
            params.put("LoginID", LoginID);
            params.put("Password", Password);
            params.put("CaptchaDeText", CaptchaDeText);
            params.put("CaptchaInputText", CaptchaInputText);
            params.put("SkipTradeAgreement", SkipTradeAgreement);
            Map<String, String> header = new HashMap<>();
            header.put("Cookie", cookie);
            header.put("DNT", "1");
            header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            header.put("Origin", "https://h.kfun444.com");
            header.put("Referer", "https://h.kfun444.com/Account/Login");
            header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
            header.put("X-Requested-With", "XMLHttpRequest");

            HttpResponse httpResponse = Test.request(uri, "post", params, header);
            Test.getCookies(httpResponse);
            System.out.println(cookie11);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
*/
