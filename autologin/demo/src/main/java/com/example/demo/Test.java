package com.example.demo;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;

public class Test {
    public static CloseableHttpClient getHttpClient() {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            //不进行主机名验证
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(builder.build(),
                    NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", sslConnectionSocketFactory)
                    .build();

            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(100);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .setDefaultCookieStore(new BasicCookieStore())
                    .setConnectionManager(cm).build();
            return httpclient;
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * StringEntity
     * @throws IOException
     *//*
    public static StringEntity testStringEntity() throws IOException{
        StringBuilder sb = new StringBuilder();
        //获取系统的信息集合，这个集合是不可以修改的
        Map<String, String> nev = System.getenv();
        for(Map.Entry<String, String> entry : nev.entrySet()){
            sb.append(entry.getKey()).append("=")
                    .append(entry.getValue()).append("\n");
        }
        String content = sb.toString();
        System.out.println(content);
        //创建只带字符串参数的
        StringEntity entity = new StringEntity(content);
        //创建带字符创参数和字符编码的
        StringEntity entity2 = new StringEntity(content, "UTF-8");
        return entity2;
    }*/

    public static HttpResponse request(String uri,String method, Map<String, Object> params, Map<String, String> headers) throws IOException {
        HttpClient client = getHttpClient();
        if ("GET".equalsIgnoreCase(method)){
            return gHttpResponse(client, uri,params, headers);
        }else{
            return pHttpResponse(client,uri,params, headers);
        }
    }

    public static HttpResponse gHttpResponse(HttpClient client,
                                             String uri,
                                             Map<String, Object> params,
                                             Map<String, String> headers) throws IOException {

        String fullUrl = buildUrlWithParams(uri, params);
        HttpGet request = new HttpGet(fullUrl);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
        HttpResponse httpResponse = client.execute(request);

        return httpResponse;
    }

    public static HttpResponse pHttpResponse(HttpClient client,
                                             String uri ,
                                             Map<String, Object> params,
                                             Map<String, String> headers) throws IOException {

        String fullUrl = buildUrlWithParams(uri, params);
        HttpPost request = new HttpPost(fullUrl);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
        // 设置2个post参数，一个是scope、一个是q
        List<NameValuePair> parameters = new ArrayList<>(0);
        if (headers != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
        // 将请求实体设置到httpPost对象中

        request.setEntity(formEntity);
        HttpResponse httpResponse = client.execute(request);

        return httpResponse;
    }

    public static String getCookies(HttpResponse httpResponse){
        Header[] cookies = httpResponse.getAllHeaders();
        for(Header head:cookies){
            if(head.getName().equalsIgnoreCase("set-cookie")){
                return head.getValue();
            }
        }
        return null;
    }

    public  static void saveImg(InputStream data,String fileName) throws  IOException{
        if(data == null){
            System.out.println("没有图片数据返回");
            return;
        }
        BufferedInputStream in = new BufferedInputStream(data);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\code\\"+fileName+".gif"));
        //byte b[]= new byte[1024];
        int i;
        while((i=in.read())!=-1){
            out.write(i);
        }
        out.flush();
        out.close();
        in.close();

    }

    /**
     * GET 方法拼接URL
     * @param uri
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String buildUrlWithParams(String uri, Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(uri);
        if (null != params && !params.isEmpty()) {
            if (!uri.contains("?")) {
                urlBuilder.append("?");
            }
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                String valueStr = null == value ? "" : value.toString();
                if (!urlBuilder.toString().endsWith("?")) {
                    urlBuilder.append("&");
                }
                urlBuilder.append(key).append("=").append(URLEncoder.encode(valueStr, "utf-8"));
            }
        }
        String fullUrl = urlBuilder.toString();
        return fullUrl;
    }

    public static String getText(HttpResponse response)throws IOException {
        String result = null;
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity, "UTF-8");
            }
        }
        return result;
    }

        public static void main (String args []){
            try {
                String login = "https://h.kfun444.com/Account/Login";
                HttpResponse httpResponse = Test.request(login, "get", null, null);
                String cookie = getCookies(httpResponse);
                System.out.println(cookie);
                String result = Test.getText(httpResponse);
                System.out.println(result);
                String __RequestVerificationToken= "__RequestVerificationToken\" type=\"hidden\" value=\"";
                String code1 = result.substring(result.indexOf(__RequestVerificationToken) + __RequestVerificationToken.length() + 1);
                System.out.println(code1);
                __RequestVerificationToken = code1.substring(0,code1.indexOf("\""));
                System.out.println(__RequestVerificationToken);

                String url = "https://h.kfun444.com/Account/Captcha";
                Map<String, Object> params = new HashMap<>();
                params.put("CaptchaError", "False");
                Map<String, String> header = new HashMap<>();
                header.put("DNT", "1");
                header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                header.put("Origin", "https://h.kfun444.com");
                header.put("Referer", "https://h.kfun444.com/Account/Login");
                header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
                header.put("X-Requested-With", "XMLHttpRequest");
                /*
                 * 请求登录页面
                 */
                httpResponse = Test.request(url, "post", params, header);
                result = Test.getText(httpResponse);
                System.out.println(result);
                /*
                 * 获取验参数
                 */
                String code = result.substring(result.indexOf("value") + "value".length() + 1, result.indexOf("/"));
                code = code.trim().substring(1, code.length() - 1);
                code = code.substring(0, code.length() - 1);
                System.out.println(code);
                String cookieCode = getCookies(httpResponse);
                header.put("Cookie", cookieCode);
                /*
                 * 获取验证码
                 */
                String imgAddress = "https://h.kfun444.com/DefaultCaptcha/Generate";
                Map<String, Object> params1 = new HashMap<>();
                params1.put("t", code);
                httpResponse = Test.request(imgAddress, "get", params1, header);

                InputStream input = httpResponse.getEntity().getContent();
                String imgName = "k_"+System.currentTimeMillis();
                Test.saveImg(input,imgName);
                String loginURL= "https://h.kfun333.com/Account/LoginVerify";
                Scanner scan= new Scanner(System.in);
                String CaptchaInputText= "";
                if (scan.hasNext()) {
                    CaptchaInputText = scan.next();
                }
                String loginId = "aaa2220";
                String password = "a123456";
                //String __RequestVerificationToken = __RequestVerificationToken;
                String CaptchaDeText=code;
                String SkipTradeAgreement= "true";
                LoginUtil.postForm(loginURL,loginId,password,
                        __RequestVerificationToken,
                        CaptchaInputText,
                        CaptchaDeText,SkipTradeAgreement,cookie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
