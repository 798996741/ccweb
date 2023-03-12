package com.fh.util;


import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class Httpsend {

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
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
                httpPost.setEntity(entity);
            }
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

	public static void main(String[] args) {
		String result = "";

		Map<String, String> map = new HashMap();
		System.out.println(map==null);
		Map<String, String> param = new HashMap<>();
		
		System.out.println(map.get("cptId"));
		StringBuilder res=new StringBuilder();
		
		res.append("{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{");
		res.append("\"username\":\"00000\",");
		res.append("\"tslevel\":\"0\",");
		res.append("\"ishf\":\"1\",");
		res.append("\"tssource\":\"\",");
		res.append("\"tsdept\":\"350101\",");
		res.append("\"tstype\":\"\",");
		res.append("\"type\":\"0\",");
		res.append("\"endreason\":\"\",");
		res.append("\"clsx\":\"\",");
		res.append("\"doaction\":\"2\",");
		res.append("\"source\":\"2\",");
		res.append("\"tsqd\":\"\",");
		res.append("\"location\":\"\",");
		res.append("\"code\":\""+"233"+"\",");
		res.append("\"tsclassify\":\""+map.get("cptType")+"\",");
		res.append("\"tscont\":\""+map.get("cptContent") + map.get("beizhu")+"\",");
		res.append("\"tssq\":\""+map.get("suqiuContent")+"\",");
		res.append("\"tsman\":\""+map.get("passengerName")+"\",");
		res.append("\"cardtype\":\""+map.get("zhengjianid")+"\",");
		res.append("\"cardid\":\""+map.get("shenfenId")+"\",");
		res.append("\"hbh\":\""+map.get("flightNo")+"\",");
		res.append("\"deport\":\""+map.get("depPort")+"\",");
		System.out.println("出发机场"+map.get("depPort"));
		res.append("\"arrport\":\"福州\",");
		res.append("\"tsdate\":\"2019-02-10 10:10:10\",");
		res.append("\"cfbm\":\"\",");
		res.append("\"uid\":\"\",");
		res.append("\"iszx\":\"\"");
		res.append("}}");
		param.put("data", res.toString());

		
		String url="http://192.168.0.167:8080/ccweb/appuser/workorderSave.do";
		
		//提交请求
		result = doPost(url, param);
		//System.out.println(result);
		//当时在测试的时候，返回的参数会出现中文乱码。这个是因为返回的参数使用了Unicode编码。所以用json转一下就可以了。

		JSONObject jsonObject=JSONObject.parseObject(result);
		result = jsonObject.toJSONString(); //


		System.out.println("---------------------------------------------");


	}
}
