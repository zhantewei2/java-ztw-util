package org.cm.pro.StaticServer;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Setter
@Getter
@Slf4j
public class StaticServer extends StaticBase implements StaticServerInterface {
    public StaticServer(){super();}
    //前缀url
    private String handleUrl;
    //前缀path
    private Path handlePath;
    //cacheControl时间
    private long cacheControlSeconds;
    //使用not Modify 304判断
    private boolean useNotModify;
    private List<String> gzipSuffix=new ArrayList<>();
    public void setHandlePath(String path){
        this.handlePath=Paths.get(path);
    }
    //失败返回文件
    public Path callbackFile;
    public void setCallbackFile(String fileName){
        callbackFile=handlePath.resolve(fileName);
    }

    public void sendFile(HttpServletResponse res,File file) throws Exception{
        String suffix=super.getSuffix(file.toString());
        super.setContentHeader(res,suffix);
        super.setGzipHeader(res,gzipSuffix,suffix);
        super.sendData(res,file);
    }
    //未找到文件时，处理。
    private boolean handleNotFound(HttpServletResponse res)throws Exception{
        if(callbackFile!=null){
            sendFile(res,callbackFile.toFile());
            return false;
        }
        return true;
    }

    public boolean run(HttpServletRequest req, HttpServletResponse res)throws Exception {
        URL url=new URL(req.getRequestURL().toString());
        String activeFileStr=url.getPath().replaceFirst("^"+handleUrl,"").replaceFirst("(^/|\\?.*|#.*|/$)","");
        Path activePath=handlePath.resolve(activeFileStr);
        File activeFile;
        try {
            activeFile = activePath.toFile();
            if (activeFile.isFile()) {
                //作者签名
                StaticZTWSign.sign(res);
                //缓存头配置
                super.cacheFile(req, res, activeFile, useNotModify, cacheControlSeconds);
                //send data
                sendFile(res, activeFile);
                return false;
            } else {
                return handleNotFound(res);
            }
        }catch (StaticRaiseException raiseException){
            return false;
        }catch(Exception e){
            log.error(e.toString());
            throw e;
        }
    }
    static public void main(String[] args){
        String url="http://www.163.com/index.html#tag";
        System.out.println(url.replaceFirst("(\\?.*|#.*)",""));

    }

}
