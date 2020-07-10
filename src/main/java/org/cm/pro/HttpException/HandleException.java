package org.cm.pro.HttpException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class HandleException {

    @ExceptionHandler(value={ReqBadParams.class,ReqBad.class,ReqForbidden.class,ReqNotFound.class,ReqUnauthorized.class})
    public ResponseEntity defaultErrHandle(
            Exception e,
            HttpServletResponse res
    )throws Exception{
        if(e instanceof BaseException){
            log.warn("response ztw error: "+((BaseException) e).getContent());
//            return new ResponseEntity(((BaseException) e).getContent(), HttpStatus.OK);
//            要求 错误、正常 均为 OK
            return new ResponseEntity(((BaseException) e).getContent(),((BaseException) e).getStatus());
        }
        else if(e instanceof ProfileException){
            Object a=1;
            return new ResponseEntity(
                    ((ProfileException) e).getContent(),
                HttpStatus.OK
            );
        }
        throw e;
    }
}
