package org.cm.pro.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class ZTWAES {
    private SecretKeySpec key;
    private IvParameterSpec iv;
    private int BYTE_LEN=16;

    public ZTWAES(String _key, String _iv){
        key=new SecretKeySpec(input_str(_key),"AES");
        iv=new IvParameterSpec(input_str(_iv));
    }
    private String byteToHex(byte[] bys){
        String str="";
        for (byte i:bys){
            String hv=Integer.toHexString(i & 0xFF);
            hv=hv.length()==1?"0"+hv:hv;
            str+=hv;
        }
        return str;
    }
    private byte[] hexToByte(String str){
        byte[] b=new byte[str.length()/2];
        for(int i=0,len=b.length;i<len;i++){
            int str_index=i*2;
            b[i]=(byte)Integer.parseInt(str.substring(str_index,str_index+2),16);
        }
        return b;
    }
    private byte[] input_str(String content){
        try {
            byte[] bys = content.getBytes("utf-8");
            int bys_len=bys.length;
            byte dis=(byte)(BYTE_LEN-bys_len%BYTE_LEN);
            byte[] bys_next=new byte[bys_len+dis];
            for(int i=0,len=bys_len+dis;i<len;i++){
                if(i<bys_len){
                    bys_next[i]=bys[i];
                }else{
                    bys_next[i]=dis;
                }
            }
            return bys_next;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    private String output_str(byte[] bys){
        byte comple_i=bys[bys.length-1];
        String result=new String(bys);
        int len=result.length();
        return result.substring(0,len-comple_i);
    }
    public String encrypt(String str){
        try{
            Cipher cipher =Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE,key,iv);
            byte[] encrypted=cipher.doFinal(input_str(str));
            return byteToHex(encrypted);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public String decrypt(String str){
        try{
            Cipher cipher=Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE,key,iv);
            byte[] decrypted=cipher.doFinal(hexToByte(str));
            return output_str(decrypted);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}