package org.cm.pro.Http;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CMBodyProfile<T> {
    private int code;
    private T result;
}
