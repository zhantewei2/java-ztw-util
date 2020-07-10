package org.cm.pro.HttpException;

import org.springframework.http.HttpStatus;

public class ReqBadParams extends BaseException {
    public ReqBadParams(Object a){
        super(a);
        setStatus(HttpStatus.NOT_ACCEPTABLE);
    }
}
