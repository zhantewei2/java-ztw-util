package org.cm.pro.utils;

import org.springframework.util.DigestUtils;
import java.util.Scanner;
import java.util.Date;

public class ZTWString {
    static public boolean isEmpty(String str){
        return (str==null) || "".equals(str);
    }
    static public String SessionId(Long id){
        String hexId=Long.toHexString(id);
        String timestamp=Long.toHexString(new Date().getTime());
        return DigestUtils.md5DigestAsHex((hexId+timestamp).getBytes());
    }

    static public String SessionId(Long id,String salt){
        String hexId=Long.toHexString(id);
        return DigestUtils.md5DigestAsHex((hexId+salt).getBytes());
    }
}
