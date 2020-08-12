package test;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static Map<String,Object> entityToMap(Object obj) throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        Class<?> Obj=obj.getClass();
        String key;
        Object val;
        for(Field field :Obj.getDeclaredFields()){
            key=field.getName();
            Method m=Obj.getMethod("get"+key.substring(0,1).toUpperCase()+key.substring(1));
            val=m.invoke(obj);
            map.put(key,val);
        };
        return map;
    }

    static public void main(String[] args)throws Exception{
        Test2 test2=new Test2();
        Test3 test3=new Test3();
        test3.setName2("test3 name");
        test2.setName("name1 value");
        test2.setTest3(test3);
        Map m=entityToMap(test2);
        String str=JSON.toJSONString(m);
        System.out.println(str);
    }
}
