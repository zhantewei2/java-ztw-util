package org.cm.pro.utils.lib;

import org.cm.pro.utils.ZTWMath;

import java.util.Date;

public class ZTWUniqueId {
    private int index=0;
    private Long preTime;
    private char getRandom(){
        return (char)ZTWMath.randomInt(48,57);
    }
    private String getRandomStr(int count){
        StringBuilder builder=new StringBuilder();
        while(count-->0){
            builder.append(getRandom());
        }
        return builder.toString();
    }
    public Long getUniqueId(){
        Long nowTime=new Date().getTime();
        if(!nowTime.equals(preTime)){
            index=0;
            preTime=nowTime;
        }else{
            index++;
        }
        String str=""+nowTime+getRandomStr(3)+index;
        return Long.valueOf(str);
    }
}
