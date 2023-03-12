package com.fh.util.weixin;
import net.sf.json.JSONObject;
public class WxApiUtil {
	 
   //通讯录秘钥
	public final static String contactsSecret = "uBhoSjg8LYLyLK4pBn0turIxzX7kThParp8w14DRTAE";

	    //企业应用的id，整型。可在应用的设置页面查看
	public final static int agentId = 1000005;
    /**
     * 企业ID
     */
    public static String CORPID = "wwe1de579339958660";
    /**
     * 应用ID
     */
    public static Integer AGENTID = 1000005;
    /**
     * 应用密码
     */
    public static String SECRET = "DN56liP41YlzMLc1qpzQuPTo1Y7s69ulkRWdbkZLrMs";
    /**
     *  //后台应用配置的API接收Token
     */
    public static String TOKEN = "nSWt01IFz5exyG6wxMIVe";
    /**
     *  //后台应用配置的API接收EncodingAESKey
     */
    public static String EncodingAESKey = "fZXWJ9MipEDoyFDKdXQjrynyX1gEYviUKGqRWQhNSeN";
    /**
     * 获得ACCESS_TOKEN的请求地址
     */
    public static String GET_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET";
    //js api
//    public static String JS_API = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=%s";
 
    public static String GET_USERID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
 
    /**
     * 获取access token
     * @return
     */
    public static AccessToken getAccessToken(){
        // 拼接请求地址
        String requestUrl = WxApiUtil.GET_ACCESS_TOKEN_URL;
        requestUrl = requestUrl.replace("ID", CORPID);
        requestUrl = requestUrl.replace("SECRET", SECRET);
        // 获取传回的信息
        JSONObject jsonObject = Weixin.httpRequst(requestUrl, "GET", null);
        if (jsonObject == null){
            new Exception("token内容为空");
        }
        AccessToken accessToken = new AccessToken();
        if (jsonObject.getInt("errcode") ==  0) {
          //  logger.info("accessToken {}",jsonObject);
           // logger.debug("accessToken {}",jsonObject);
 
            accessToken.setErrcode(jsonObject.getInt("errcode"));
            accessToken.setAccess_token(jsonObject.getString("access_token"));
            accessToken.setErrmsg(jsonObject.getString("errmsg"));
            accessToken.setExpires_in(jsonObject.getInt("expires_in"));
            return  accessToken;
        } else {
           // logger.error("{} get access error,msg:{}", jsonObject.toString());
        }
        return null;
    }
 
    /**
     * 获取userId
     * @return
     */
    public static String getUserId(String accessToken,String code){
        String requestUrl = GET_USERID_URL;
        // 拼接请求地址
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取传回的信息
        JSONObject jsonObject = Weixin.httpRequst(requestUrl, "GET", null);
        if (jsonObject.getInt("errcode") ==  0){
            //企业成员授权时返回 userId 非企业成员openId
            String userId = jsonObject.getString("UserId");
            return  userId;
        }
        if ( jsonObject.getInt("errcode") == 42001){
            String newAccessToken = getAccessToken().getAccess_token();
            getUserId(newAccessToken,code);
        } else {
            //logger.error("{} get userId error,msg:{}", jsonObject.toString());
        }
        return null;
    }
 
}

