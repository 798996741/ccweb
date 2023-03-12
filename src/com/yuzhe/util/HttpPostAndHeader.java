package com.yuzhe.util;

import java.io.*;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.yulun.utils.Httpclient;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.tools.ant.taskdefs.Get;
import org.springframework.web.multipart.MultipartFile;


import javax.net.ssl.SSLContext;

/**
 * http发送
 * @date2020-09-19
 * @author  huangjianling
 */
public class HttpPostAndHeader {

    public static JSONObject getAccessToken() throws UnsupportedEncodingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //基本信息
        String userName = "74e81714-faaa-4edc-9dec-3e549653fcd0";
        String clientId = "AuOXOs6r";
        String url = "https://122.119.160.178:8443/sae/sae-oauth2/oauth/token?grant_type=client_credentials";
        String requestId = UuidUtil.get32UUID();

        BASE64Encoder base64 = new BASE64Encoder();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("requestId", requestId);
        httpPost.setHeader("Authorization", "Basic " + base64.encode((userName + ":" + clientId).getBytes("ISO_8859_1")));
        httpPost.setHeader("clientId", clientId);
        String charSet = "UTF-8";

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                30000);
        HttpConnectionParams.setSoTimeout(httpParameters, 30000);

        ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();


        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            //信任所有
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                new String[] { "TLSv1.2" },
                null,
                NoopHostnameVerifier.INSTANCE);
        HttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();


        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                strber.append(line + "\n");
            }
            inStream.close();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                JSONObject token =  JSONObject.parseObject(strber.toString());
                JSONObject json = token.getJSONObject("data");
                json.put("requestId",requestId);
                return  json;

            }


        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new JSONObject();
    }


    public static JSONObject getData(String url, PageData pd, String type) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, UnirestException {
        String POST = "post";
        String GET = "get";
        //返回Json类型
        JSONObject resultJson = new JSONObject();
        //设置头部信息
        Map<String, String> headers = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        headers.put("requestId", UuidUtil.get32UUID());
        headers.put("clientId", "AuOXOs6r");
        headers.put("Authorization", "Bearer " + getAccessToken().getString("access_token"));
        headers.put("Accept", "application/json;charset=utf-8");
        headers.put("Content-Type", "application/json;charset=utf-8");
        //添加SSL信任，并且获取httpclient
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            //信任所有
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1.2"},
                null,
                NoopHostnameVerifier.INSTANCE);
        HttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        //设置body数据
        Map<String, Object> bodyParams = new HashMap<String, Object>();
        //body传参
        bodyParams.putAll(pd);
//        try {
            //将httpclient提交给Unirest
            Unirest.setHttpClient(httpclient);
            com.mashape.unirest.http.HttpResponse<JsonNode> httpResponse = null;
            if(POST.equals(type)) {
                httpResponse = Unirest.post(url)
                        .headers(headers)
                        .body(mapper.writeValueAsString(bodyParams))
                        .asJson();
            }
            if(GET.equals(type)){
                httpResponse = Unirest.get(url)
                        .headers(headers)
                        .queryString(pd)
                        .asJson();
            }

            //获取数据结果
            String resultStr = httpResponse.getBody().toString();
             saveAsFileWriter("resultStr"+ resultStr);
            //格式转换json
            resultJson = JSONObject.parseObject(resultStr);
            saveAsFileWriter("resultJson"+ resultStr);
            return resultJson;
//        } catch (Exception e) {
//            resultJson.put("messge", "失败报错");
//            return resultJson;
//        } finally {
//
//        }
    }

    public static JSONObject getData(String url, JSONArray list, String type) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, UnirestException {
        String POST = "post";
        String GET = "get";
        //返回Json类型
        JSONObject resultJson = null;
        //设置头部信息
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("requestId", UuidUtil.get32UUID());
        headers.put("clientId", "AuOXOs6r");
        headers.put("Authorization", "Bearer " + getAccessToken().getString("access_token"));
        headers.put("Accept", "*/*");
        headers.put("Content-Type", "application/json;charset=utf-8");
        //添加SSL信任，并且获取httpclient
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            //信任所有
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1.2"},
                null,
                NoopHostnameVerifier.INSTANCE);
        HttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        //设置body数据
        Map<String, Object> bodyParams = new HashMap<String, Object>();
        try {
            //将httpclient提交给Unirest
            Unirest.setHttpClient(httpclient);
            com.mashape.unirest.http.HttpResponse<JsonNode> httpResponse = null;
            if(POST.equals(type)) {
                httpResponse = Unirest.post(url)
                        .headers(headers)
                        .body(list.toString())
                        .asJson();
            }
            if(GET.equals(type)){
                httpResponse = Unirest.get(url)
                        .headers(headers)
                        .asJson();
            }

            //获取数据结果
            String resultStr = httpResponse.getBody().toString().replace("/(\r\n)|(\n)|(\r)/g","");
            //格式转换json
            resultJson = JSONObject.parseObject(resultStr);
            return resultJson;
        } catch (Exception e) {
            resultJson.put("messge", "失败报错");
            return resultJson;
        } finally {

        }
    }



//    public static JSONObject upFile(File file) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, UnirestException {
//
//        file = new File("D:\\apache-tomcat-8-ccweb8090\\webapps\\ccweb\\uploadFiles\\uploadFile\\20201015_1602761570828.jpg");
//
//
//        JSONObject resultJson = null;
//        CloseableHttpClient httpclient = null;
//        SSLContext sslContext;
//        sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
//        httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
//        HttpPost httppost = new HttpPost("https://122.119.160.178:8443/sae/sae-liqp-pic-server/1.0/file/upload");
//        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
//        httppost.setConfig(requestConfig);
//        FileBody bin = new FileBody(file);
//        HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).build();
//        httppost.setEntity(reqEntity);
//        httppost.setHeader("requestId", UuidUtil.get32UUID());
//        httppost.setHeader("clientId", "AuOXOs6r");
//        httppost.setHeader("Authorization", "bearer " + getAccessToken().getString("access_token"));
//        httppost.setHeader("Accept", "application/json;charset=utf-8");
//        httppost.setHeader("Content-Type", "multipart/form-data; boundary=" + "Boundary_Aliar");
//        httppost.setHeader("Content-Length", String.valueOf(reqEntity.getContentLength()));
//        CloseableHttpResponse response = httpclient.execute(httppost);
//        String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
//        resultStr = resultStr.replace("\\", "");
//        resultJson = JSONObject.parseObject(resultStr);
//        return resultJson;
//    }

    public static JSONObject upFile(File file) throws UnirestException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, UnsupportedEncodingException {
        JSONObject resultJson = null;
        CloseableHttpClient httpclient = null;
        SSLContext sslContext;
        sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        Unirest.setHttpClient(httpclient);
        com.mashape.unirest.http.HttpResponse<JsonNode> httpResponse = Unirest.post("https://122.119.160.178:8443/sae/sae-liqp-pic-server/1.0/file/upload")
                .header("requestId", UuidUtil.get32UUID())
                .header("Authorization", "Bearer " + getAccessToken().getString("access_token"))
                .header("clientId", "AuOXOs6r")
                .header("Accept", "*/*")
                .field("file", file)
                .asJson();
           String resultStr = httpResponse.getBody().toString();
            saveAsFileWriter("接口请求返回的数据" + resultStr);
            //格式转换json
            resultJson = JSONObject.parseObject(resultStr);
            return resultJson;
    }


    private static void saveAsFileWriter(String content) {
        FileWriter fwriter = null;
        try {
            SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); //格式化当前系统日期
            String dateTime = dateFm.format(new java.util.Date());
            String filePath = "/usr/"+dateTime+".txt";
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(filePath, true);
            fwriter.write(content);
            fwriter.write("\r\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
                fwriter=null;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}



