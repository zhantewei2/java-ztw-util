package org.cm.pro.utils;

public class ZTWMath {
    public static String randomId(int count){
        StringBuilder stringBuilder=new StringBuilder();
        while(count-->0){
            stringBuilder.append(randomOne());
        }
        return stringBuilder.toString();
    }
    public static String randomId(){
        return randomId(8);
    }
    public static char randomOne(){
        int index=((Double)Math.floor(Math.random()*3)).intValue();
        int code;
        if(index==0){
            code=randomInt(48,57);
        }else if(index==2){
            code=randomInt(97,122);
        }else{
            code=randomInt(65, 90);
        }
        return (char)code;
    }

    public static int randomInt(int start,int end){
        return ((Double)Math.floor(Math.random()*(end-start))).intValue()+start;
    }
}
