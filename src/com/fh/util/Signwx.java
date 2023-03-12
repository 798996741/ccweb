package com.fh.util;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;  




import com.fh.entity.OAuthInfo;

import net.sf.json.JSONObject;

public class Signwx {
	public static String APP_ID="wx936933c9ca392057";
	//public static String APP_ID="wx10bc22e8517a3e40";
	public static String APPSECRET="6f572ca8f5ef51979ebc44faea896a9e";
	
	//public static String APPSECRET="6996c67741df2cbb0001642f04fdb2f3";
	
    public static void main(String[] args) {
    	Signwx signwx=new Signwx();
    	
    	//System.out.println(signwx.getTicket(signwx.getAccess_token(APP_ID, APPSECRET)));
        //String jsapi_ticket = "jsapi_ticket";

        //String url = "http://example.com";
        //Map<String, String> ret = sign_x(jsapi_ticket, url);
       // for (Map.Entry entry : ret.entrySet()) {
           // System.out.println(entry.getKey() + ", " + entry.getValue());
        //}
    };

    public static Map<String, String> sign_x(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    
    /**
     * 
     * @param APP_ID 
     * @param APPSECRET
     * @return
     */
    public static String getAccess_token( String APP_ID,String APPSECRET) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + APP_ID+ "&secret=" + APPSECRET;
        String accessToken = null;
        try {
    	   URL urlGet = new URL(url);
           HttpURLConnection http = (HttpURLConnection) urlGet
                   .openConnection();
           http.setRequestMethod("GET"); // 必须是get方式请求
         
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); 

            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson =JSONObject.fromObject(message);
            accessToken = demoJson.getString("access_token");
            System.out.println(accessToken);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }
    
    
    public static String getTicket(String access_token) {
        String ticket = null;
      //  String access_token =ACCESS_TOKEN; //有效期为7200秒
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";
         
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson =JSONObject.fromObject(message);
           // JSONObject demoJson = new JSONObject(message);
           ticket = demoJson.getString("ticket");
            //System.out.println(ticket);
            is.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
         
        return ticket;
    }
    
    
    
    /*
    * 跳转到指定页面获取微信access_token代码
    * @param string $appid：微信公众号的appid(具有网页授权接口)
    * @param string $redirecturl：指定跳转的获取access_token的url
    * @param string $scope：snsapi_userinfo为获取用户基本信息，snsapi_base为获取用户openid
    */
     
    
    public static String getopenid(String appid,String redirecturl,String access_token) {
        String ticket = null;
      //  String access_token =ACCESS_TOKEN; //有效期为7200秒
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+redirecturl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
         
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            System.out.println("message:"+message);
           // JSONObject demoJson =JSONObject.fromObject(message);
           // JSONObject demoJson = new JSONObject(message);
            //ticket = demoJson.getString("ticket");
            //System.out.println(ticket);
            is.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
         
        return ticket;
    }
   
    
    public static OAuthInfo getOAuthOpenId(String appid, String secret, String code ) {
        OAuthInfo oAuthInfo = null;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&scope=snsapi_base&grant_type=authorization_code";
       System.out.println(url);
        try {
	        URL urlGet = new URL(url);
	        HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
	        http.setRequestMethod("GET"); // 必须是get方式请求
	        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	        http.setDoOutput(true);
	        http.setDoInput(true);
	        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
	        System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
	        http.connect();
	        InputStream is = http.getInputStream();
	        int size = is.available();
	        byte[] jsonBytes = new byte[size];
	        is.read(jsonBytes);
	        String message = new String(jsonBytes, "UTF-8");
	        JSONObject jsonObject =JSONObject.fromObject(message);
	        // 如果请求成功
	        if (null != jsonObject) {
	            try {
	                oAuthInfo = new OAuthInfo();
	                //oAuthInfo.setAccessToken(jsonObject.getString("access_token"));
	               // oAuthInfo.setExpiresIn(jsonObject.getInt("expires_in"));
	                //oAuthInfo.setRefreshToken(jsonObject.getString("refresh_token"));
	                oAuthInfo.setOpenId(jsonObject.getString("openid"));
	                oAuthInfo.setSession_key(jsonObject.getString("session_key"));
	               // oAuthInfo.setScope(jsonObject.getString("scope"));
	            } catch (Exception e) {
	                oAuthInfo = null;
	                // 获取token失败
	                System.out.println("网页授权获取openId失败 errcode:{} errmsg:{}"+jsonObject);
	            }
	        }
        } catch (Exception e) {
            e.printStackTrace();
        }    
        return oAuthInfo;
    }

}
