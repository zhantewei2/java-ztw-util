package org.cm.pro.Http;

public class CMHttp {
    static public <T> CMBodyProfile<T> resBody(int statusCode,T result){
        CMBodyProfile<T> profile=new CMBodyProfile<T>();
        profile.setCode(statusCode);
        profile.setResult(result);
        return profile;
    }
    static public <T> CMBodyProfile<T> resBody(T result){
        CMBodyProfile<T> profile=new CMBodyProfile<>();
        profile.setCode(20000);
        profile.setResult(result);
        return profile;
    }
}
