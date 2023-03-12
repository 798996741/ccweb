package com.yulun.utils.msg;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class httpclient {

	/*入参说明
	 * @author hjl
	 * @date 2020-01-05
	 * param url 请求地址
	 * param jsonObject	请求的json数据
	 * param encoding	编码格式
	 * 
	 * */
	public static String doGet(String url, Map<String, String> param) {
		 
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
 
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
 
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
 
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
 
    public static String doGet(String url) {
        return doGet(url, null);
    }
 
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"GBK");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            System.out.println(response);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
 
        return resultString;
    }
 
    public static String doPost(String url) {
        return doPost(url, null);
    }
 
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
 
        return resultString;
    }
    
    public static String sendMsg(String to,String text){
    	String result="";
    	Map<String, String> map = new HashMap();
		System.out.println(map==null);
		Map<String, String> param = new HashMap<>();
		
		param.put("username", "ylxx@ylxx");
		param.put("password", "szjqZkeF");
		param.put("to", to);
		param.put("text", text);

		String url="http://129.204.74.115:9999/api/sendsms";
		
		//提交请求
		result = doPost(url, param);
		//System.out.println(result);
		//当时在测试的时候，返回的参数会出现中文乱码。这个是因为返回的参数使用了Unicode编码。所以用json转一下就可以了。
		System.out.println(result);
		JSONObject jsonObject= JSONObject.parseObject(result);
		result = jsonObject.toJSONString(); //
		return result;
    }

	public static void main(String[] args) {
		String result = "";

		
		Map<String, String> map = new HashMap();
		System.out.println(map==null);
		Map<String, String> param = new HashMap<>();
		
		param.put("username", "ylxx@ylxx");
		param.put("password", "szjqZkeF");
		param.put("to", "13763871580");
		param.put("text", "内容");

		String url="http://129.204.74.115:9999/api/sendsms";
		
		//提交请求
		result = doPost(url, param);
		//System.out.println(result);
		//当时在测试的时候，返回的参数会出现中文乱码。这个是因为返回的参数使用了Unicode编码。所以用json转一下就可以了。
		System.out.println(result);
		JSONObject jsonObject= JSONObject.parseObject(result);
		result = jsonObject.toJSONString(); //


		System.out.println("---------------------------------------------");


	}
}
