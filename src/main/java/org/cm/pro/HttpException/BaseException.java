package org.cm.pro.HttpException;

import com.alibaba.fastjson.JSON;
import org.cm.pro.Http.CMBodyProfile;
import org.cm.pro.Http.CMHttp;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.zip.DataFormatException;

public class BaseException extends RuntimeException{
    private String content;
    private HttpStatus statusCode;
    public BaseException(Object msg){
        super();

        if(msg instanceof Map){
            content= JSON.toJSONString(msg);
        }else if(msg instanceof String){
            content=(String)msg;
        }else{
            throw new IllegalArgumentException();
        }
    }
    public BaseException(){}
    public String getContent(){
        return content;
    }
    protected HttpStatus getStatus(){
        return this.statusCode;
    }
    protected void setStatus(HttpStatus statusCode){
        this.statusCode=statusCode;
    }
}
