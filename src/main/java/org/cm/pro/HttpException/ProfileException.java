package org.cm.pro.HttpException;

public class ProfileException extends RuntimeException{
    public Object getContent(){
        return "string";
    }
}
