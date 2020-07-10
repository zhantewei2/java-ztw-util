package org.cm.pro.utils;

import java.util.HashMap;

public class ZTWMap extends HashMap {
    public ZTWMap(Object... args){
        super();
        int len=args.length;
        if(len %2!=0)return;
        for(int i=0;i<args.length;i+=2){
            this.put(args[i],args[i+1]);
        }
    }
}
