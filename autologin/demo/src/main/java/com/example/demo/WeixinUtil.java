package com.example.demo;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;

import static com.example.demo.ImageUtil.*;

/**
 * 公众平台通用接口工具类
 *
 */
public class WeixinUtil {

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return String
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        String result = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            /**
             * application/x-www-form-urlencoded; charset=UTF-8
             * origin: https://h.kfun444.com
             * referer: https://h.kfun444.com/Account/Login
             * user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36
             * x-requested-with: XMLHttpRequest
             * cookies:__RequestVerificationToken=DTMloe2O12P4awO9MoLTzY61NTx87PUu178l3t8v164QtZWlvZZUaPFbB9SOkNlfdPPLAEvJhQ7DhP1NR-HS68p8Gc7jqVLZJaf0vIIte6w1; ASP.NET_SessionId=xf53nxgebpibs0qmsroqczpz; .AspNet.ApplicationCookie=VfyUj9NTQJxmuEd9_yEJNwtGuwvaY1gRFFGPRL5I-7zwP4whXmrs0uQf5PkRuxbssH4EGn2S6MRtz0x1fbZL82bnWTDbpdCoAMMz0JA-Xi-oRO5Eh0-r8yMD-rP04n5QKGaoffy0wXCdzezrWEjcCOVRUGhq6h3gqBJNzcrFvLFYAWZnMOAVdaQHJJSaZr0Wk2tEMNdY7J-67vFnsK-GcxqvTwy4IHzWcHzHiU7q5ViYangBr95NDYjWzU2uYsLbx_5Rdh7NcBaf7U9lbqtMn6Pd2d5AH2nQ_M00IdNeo6iV28Dc-QwO8QZl7_LOFpFPeUtSMeBke0y4djdoMuQjntI1z6w6v5UmziY0pEhp5RcKGxjMy39YwcToqtxgm9mEjCKIVIyLmkSje-AHYdCkojmhnNDhqbtIZfzUqn1qH1JSSUB4OHgUekHsDzdXMtkEF_4zw75iPFJ2gkQeFdIcNs6pa0nVQCYBTLS1gfyxozNOrR4bJn3BrN54alHRSxf3TDD6XI4-gBQbm5hjxpJiST6N9A-Zu4PUtS1pGgKoqCs; token=2857d83b1efcd3aac6d15d5764a4eb47; random=1865
             */
            httpUrlConn.setRequestProperty("content-type","application/x-www-form-urlencoded; charset=UTF-8");
            httpUrlConn.setRequestProperty("origin","https://h.kfun444.com");
            httpUrlConn.setRequestProperty("referer","https://h.kfun444.com/Account/Login");
            httpUrlConn.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
            httpUrlConn.setRequestProperty("x-requested-with","XMLHttpRequest");
            //httpUrlConn.setRequestProperty("cookies","__RequestVerificationToken=DTMloe2O12P4awO9MoLTzY61NTx87PUu178l3t8v164QtZWlvZZUaPFbB9SOkNlfdPPLAEvJhQ7DhP1NR-HS68p8Gc7jqVLZJaf0vIIte6w1; ASP.NET_SessionId=xf53nxgebpibs0qmsroqczpz; .AspNet.ApplicationCookie=VfyUj9NTQJxmuEd9_yEJNwtGuwvaY1gRFFGPRL5I-7zwP4whXmrs0uQf5PkRuxbssH4EGn2S6MRtz0x1fbZL82bnWTDbpdCoAMMz0JA-Xi-oRO5Eh0-r8yMD-rP04n5QKGaoffy0wXCdzezrWEjcCOVRUGhq6h3gqBJNzcrFvLFYAWZnMOAVdaQHJJSaZr0Wk2tEMNdY7J-67vFnsK-GcxqvTwy4IHzWcHzHiU7q5ViYangBr95NDYjWzU2uYsLbx_5Rdh7NcBaf7U9lbqtMn6Pd2d5AH2nQ_M00IdNeo6iV28Dc-QwO8QZl7_LOFpFPeUtSMeBke0y4djdoMuQjntI1z6w6v5UmziY0pEhp5RcKGxjMy39YwcToqtxgm9mEjCKIVIyLmkSje-AHYdCkojmhnNDhqbtIZfzUqn1qH1JSSUB4OHgUekHsDzdXMtkEF_4zw75iPFJ2gkQeFdIcNs6pa0nVQCYBTLS1gfyxozNOrR4bJn3BrN54alHRSxf3TDD6XI4-gBQbm5hjxpJiST6N9A-Zu4PUtS1pGgKoqCs; token=2857d83b1efcd3aac6d15d5764a4eb47; random=1865");
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串


            BufferedInputStream bis = new BufferedInputStream(httpUrlConn.getInputStream());
            BufferedImage bm = ImageIO.read(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bm, "gif", bos);
            bos.flush();
            byte[] data = bos.toByteArray();

            result = byte2hex(data);
            bos.close();

            // inputStream = null;
            httpUrlConn.disconnect();

            // jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            ce.printStackTrace();
            // log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            e.printStackTrace();
            // log.error("https request error:{}", e);
        }
        return result;
    }

    public static void main(String[] args) throws Exception{
        String requestUrl = "https://h.kfun444.com/Account/Captcha";
        String requestMethod = "POST";
        //String outputStr = null;
        String outputStr = "{ \"CaptchaError\": \"False\" }";
        String result = WeixinUtil.httpRequest(requestUrl, requestMethod, outputStr);
        System.out.println("result:" + result);
        byte []data = hex2byte(result);

        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File("D:/163.gif");
        if(!imageFile.exists()){
            imageFile.createNewFile();
        }
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();
    }


}

 