package org.cm.pro.HttpException;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;

public class ReqBad extends BaseException{
    private ReqBadProfile reqBadProfile;

    public ReqBad(ReqBadEnum reqBadEnum){
//        String a=1;
        super();
        this.setStatus(HttpStatus.BAD_REQUEST);
        this.reqBadProfile=new ReqBadProfile();
        this.reqBadProfile.setType(reqBadEnum.getType());
    }
    public ReqBad(ReqBadEnum reqBadEnum,String message){
        super();
        this.setStatus(HttpStatus.BAD_REQUEST);
        this.reqBadProfile=new ReqBadProfile();
        this.reqBadProfile.setType(reqBadEnum.getType());
        this.reqBadProfile.setMessage(message);
    }
    public <T> ReqBad(ReqBadEnum reqBadEnum,String message,T content){
        super();
        this.setStatus(HttpStatus.BAD_REQUEST);
        this.reqBadProfile=new ReqBadProfile();
        this.reqBadProfile.setType(reqBadEnum.getType());
        this.reqBadProfile.setMessage(message);
        this.reqBadProfile.setContent(content);
    }
    public <T> ReqBad(ReqBadProfile<T> reqBadProfile){
        this.setStatus(HttpStatus.BAD_REQUEST);
        this.reqBadProfile=reqBadProfile;
    }
    public ReqBad(Object a){
        super(a);
        this.setStatus(HttpStatus.BAD_REQUEST);
        this.reqBadProfile=new ReqBadProfile();
        this.reqBadProfile.setType(ReqBadEnum.Normal.getType());
        this.reqBadProfile.setMessage(super.getContent());
    }

    @Override
    public String getContent(){
        return JSON.toJSONString(this.reqBadProfile);
    }

}
