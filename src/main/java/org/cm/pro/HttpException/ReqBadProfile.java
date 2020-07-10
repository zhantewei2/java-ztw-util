package org.cm.pro.HttpException;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReqBadProfile<T>{
    private int type;
    private String message;
    private T content;
}
