package org.cm.pro.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cm.pro.utils.entity.RequestResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ZTWReq {
    /***
     * author: zhantewei
     *  get request
     * creation time:2019-09-12
     ****/
    public static RequestResult get(
            String url,
            String params
    )throws Exception {
        return sendHttp("GET",url,params,null,null);
    }

    /***
     * author: zhantewei
     * post request
     * creation time:2019-09-12
     ****/
    public static RequestResult post(
            String url,
            Map<String,Object> params
    )throws Exception{
        return sendHttp("POST",url,"",params,null);
    }

    public static RequestResult post(
            String url
    )throws Exception{
        return sendHttp("POST",url,"",null,null);
    }

    public static RequestResult post(
            String url,
            Map<String,Object> params,
            Map<String,String> headers
    )throws Exception{
        return sendHttp("POST",url,"",params,headers);
    }

    public static void writeHeader(
            HttpURLConnection conn,
            Map<String,String> headers
    ){
        for(String key:headers.keySet()){
            String value=headers.get(key);
            conn.setRequestProperty(key,value);
        }
    }

    public static RequestResult sendHttp(
            String method,
            String url,
            String params,
            Map<String,Object> paramsMap,
            Map<String,String> headerMap
    )throws Exception{
        if (url.startsWith("https")) HTTPSTrustManager.allowAllSSL();
        if(!params.equals(""))url=url+"?"+params;
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        try {
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);
            conn.setRequestMethod(method);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if(headerMap!=null)writeHeader(conn,headerMap);

            if(method.equals("POST")) {
                conn.setRequestProperty("Content-Type", "application/json");
                if(paramsMap!=null){
                    byte[] data = handlePostBody(paramsMap);
                    conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(data);
                }
            }
            conn.connect();
            RequestResult result = new RequestResult();
            int statusCode=conn.getResponseCode();
            result.setStatusCode(statusCode);
            result.setHeaders(conn.getHeaderFields());

            InputStream inputStream;
            try{
                inputStream=conn.getInputStream();
            }catch (IOException e){
                inputStream=conn.getErrorStream();
            }
            result.setBuffer(ZTWMethod.getBytesFromInputStream(
                    inputStream
            ));
            return result;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if(conn!=null)conn.disconnect();
        }
    }

    public static byte[] handlePostBody(Map<String,Object> param)throws JsonProcessingException {
        ObjectMapper m=new ObjectMapper();
        return m.writeValueAsBytes(param);
    }
}
