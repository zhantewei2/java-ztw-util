package org.cm.pro.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.cm.pro.HttpException.ReqBad;
import org.cm.pro.HttpException.ReqBadEnum;
import org.cm.pro.HttpException.ReqBadParams;
import org.cm.pro.utils.entity.SaltProfile;
import org.cm.pro.utils.lib.ZTWUniqueId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.*;
import java.util.zip.GZIPOutputStream;

@Slf4j
public class ZTWMethod {
    /***
     * author: zhantewei
     * 生成唯一id
     * creation time:2019-09-12
     ****/
    public static String uniqueId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
    /***
     * author: zhantewei
     * 校验参数
     * creation time:2019-09-12
     ****/
    public static void ValCheck(BindingResult validResult){
        if(validResult.hasErrors()){
            FieldError err=validResult.getFieldError();
            String key=err.getField();
            String value=err.getDefaultMessage();
            throw new ReqBadParams(key+":"+value);
        }
    }
    /***
     * author: zhantewei
     * 加载json文件为bean
     * creation time:2019-09-12
     ****/
    public static void loadJson(Object target,String jsonAddress) throws Exception{
        Class<?> targetClass=target.getClass();
        InputStream jsonStream=targetClass.getResourceAsStream(jsonAddress);
        String stringify= ZTWIOMethod.getString(jsonStream);
        jsonStream.close();
        Map<String, Object> map=JSON.parseObject(stringify);
        Method[] methods=targetClass.getMethods();
        for(Method m:methods){
            String methodName=m.getName();
            if(methodName.indexOf("set")==0){
                String fieldName=methodName.substring(3);
                String fieldNameSecondary=fieldName.substring(0,1).toLowerCase()+fieldName.substring(1);
                String finalFieldName="";
                try {
                    targetClass.getDeclaredField(fieldName);
                    finalFieldName=fieldName;
                }catch (Exception e){
                    try {
                        targetClass.getDeclaredField(fieldNameSecondary);
                        finalFieldName=fieldNameSecondary;
                    }catch (Exception err){}
                }
                if(finalFieldName.equals(""))continue;
                m.invoke(target,map.get(finalFieldName));
            }
        }
    }
    public static void loadMap(Object target){

    }

    //object assign
    public static void objectAssign(Object a,Object b) throws Exception{
        Class<?> classA=a.getClass();
        Class<?> classB=b.getClass();
        Field[] fields=classB.getDeclaredFields();
        for(Field f:fields){
            String key=f.getName();
            try {
                if (classA.getDeclaredField(key) == null) continue;
            }catch(NoSuchFieldException e){
                continue;
            }
            Object valueType=f.getType();
            key=key.substring(0,1).toUpperCase()+key.substring(1);
            Method getMethod=classB.getMethod("get"+key);
            Method setMethod=classA.getMethod("set"+key,(Class)valueType);
            Object getValue=getMethod.invoke(b);
            if(getValue ==null) continue;
            setMethod.invoke(a,getValue);
        }

    }
    /***
     * author: zhantewei
     * 获取内容 摘要，裁剪内容
     * creation time:2019-09-12
     ****/
    public static String extractBrief(String content,int maxSize){
        String result=content
                .replaceAll("<.*?>","")
                .replaceAll("(\\s|\\t)","");
        return result.length()<=maxSize?result:result.substring(0,maxSize);
    }
    /***
     * author: zhantewei
     * 输出gzip responseEntity
     * creation time:2019-08-29
     ****/
    public static ResponseEntity<byte[]> returnGzip(byte[] data) throws Exception{
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        GZIPOutputStream gzip=new GZIPOutputStream(bos);
        gzip.write(data);
        gzip.close();
        byte[] resultDate=bos.toByteArray();
        bos.close();
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Encoding","gzip");
        return ResponseEntity.ok().headers(headers).body(resultDate);
    }
    /***
     * author: zhantewei
     *  更新监测 抛出异常
     * creation time:2019-09-02
     ****/
    public static void CheckUpdate(Integer updateCount){
        if (updateCount<1){
            log.error("未找到匹配的更新项");
            throw new ReqBad(ReqBadEnum.UpdateNotFound);
        }
    }
    /***
     * author: zhantewei
     *  redis插入监测 抛出异常
     * creation time:2019-09-16
     ****/
    public static void CheckRedis(Long result){
        if(result<1){
            log.error("redis set loss");
            throw new ReqBad("redis set loss");
        }
    }
    /***
    * @Description  生成 uniqueId 仅用于当前服务唯一
    * @author ztw
    * @param
    * @return
    **/
    private static ZTWUniqueId ztwUniqueId=new ZTWUniqueId();
    public static synchronized Long getUniqueId(){
        return ztwUniqueId.getUniqueId();
    }

    /***
    * @Description 生成 saltProfile
    * @author ztw
    * @param
    * @return
    **/
    private static String getFinalPassword(
            String password,
            String salt
    ){
        return DigestUtils.md5DigestAsHex((salt+password).getBytes());
    }
    public static SaltProfile getSaltProfile(String password){
        SaltProfile saltProfile=new SaltProfile();
        String salt=ZTWMath.randomId();
        saltProfile.setSalt(salt);
        saltProfile.setFinalPassword(getFinalPassword(password,salt));
        saltProfile.setPassword(password);
        return saltProfile;
    }
    /***
    * @Description 检查密码和salt是否与finalPassword一致
    * @author ztw
    * @param password
    * @param  salt
    * @param  finalPassword
    * @return boolean
    **/
    public static boolean validSaltPassword(
            String password,
            String salt,
            String finalPassword
    ){
        String targetFinalPassword=getFinalPassword(password,salt);
        return finalPassword.equals(targetFinalPassword);
    }

    public static byte[] getBytesFromInputStream(
            InputStream isr
    ) throws IOException {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        byte[] buff=new byte[100];
        int rsc=0;
        for(;;){
            rsc=isr.read(buff);
            bos.write(buff,0,rsc);
            if(rsc<100)break;
        }
        isr.close();
        return bos.toByteArray();
    }
}
