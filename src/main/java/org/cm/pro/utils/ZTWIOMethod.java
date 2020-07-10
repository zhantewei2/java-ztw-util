package org.cm.pro.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ZTWIOMethod {
    static public String getString(InputStream is) throws IOException {
        InputStreamReader isr=new InputStreamReader(is);
        StringBuilder builder=new StringBuilder();
        char[] chars=new char[50];
        int rsc=0;
        for(;;){
            rsc=isr.read(chars);
            builder.append(chars,0,rsc);
            if(rsc<50)break;
        }
        String content=builder.toString();
        isr.close();
        return content;
    }
}
