package org.cm.pro.HttpException;

import org.springframework.http.HttpStatus;

public class ReqForbidden extends BaseException {
    public ReqForbidden(Object a){
        super(a);
        setStatus(HttpStatus.FORBIDDEN);
    }
}
