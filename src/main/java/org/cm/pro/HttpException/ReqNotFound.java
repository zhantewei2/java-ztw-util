package org.cm.pro.HttpException;

import org.springframework.http.HttpStatus;

public class ReqNotFound extends BaseException {
    public ReqNotFound(Object a){
        super(a);
        this.setStatus(HttpStatus.NOT_FOUND);
    }
}
