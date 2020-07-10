package org.cm.pro.HttpException;

import org.springframework.http.HttpStatus;

public class ReqUnauthorized extends BaseException {
    public ReqUnauthorized(Object a){
        super(a);
        setStatus(HttpStatus.UNAUTHORIZED);
    }
}
