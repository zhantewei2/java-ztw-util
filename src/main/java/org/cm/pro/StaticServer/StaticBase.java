package org.cm.pro.StaticServer;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class StaticBase {
    private StaticContentType staticContentType=new StaticContentType();

    final protected String getSuffix(String fileName){
        int lastIndex= fileName.lastIndexOf(".");
        if(lastIndex<0) return null;
        return fileName.substring(lastIndex+1);
    }
    final protected void setContentHeader(HttpServletResponse res,String suffix){
        res.setHeader("Contnet-Type",staticContentType.getType(suffix));
    }
    final protected void setGzipHeader(HttpServletResponse res, List<String> gzipSuffixList,String suffix){
        if(gzipSuffixList.indexOf(suffix)>=0){
            res.setHeader("Content-Encoding","gzip");
        }
    }
    final protected void cacheFile(HttpServletRequest req,HttpServletResponse res,File file,boolean useNotModify,long cacheControlSeconds)throws IOException{
        //302
        if(useNotModify){
            try{
                Long lastModified=file.lastModified();
                Long modifiedSince=null;
                try {
                    modifiedSince = Long.valueOf(req.getHeader("If-Modified-Since"));
                }catch (Exception e){}
                if(modifiedSince!=null&&lastModified.equals(modifiedSince)){
                    res.setStatus(304);
                    res.getWriter().close();
                    throw new StaticRaiseException();
                }
                res.setHeader("Last-Modified",String.valueOf(lastModified));
            }catch (Exception e){
                if(e instanceof StaticRaiseException)throw e;
            }
        }

        if(cacheControlSeconds!=0){
            res.setHeader("Cache-Control","max-age="+cacheControlSeconds);
        }
    }

    final protected void sendData(HttpServletResponse res, File file)throws FileNotFoundException,IOException {
        FileInputStream fileInputStream=new FileInputStream(file);
        OutputStream writer=res.getOutputStream();
        try{
            int readCount=50;
            byte[] bytes=new byte[50];
            int finalCount;
            for(;;){
                finalCount=fileInputStream.read(bytes);
                if(finalCount<=0)break;
                if(finalCount<readCount){
                    writer.write(Arrays.copyOfRange(bytes,0,finalCount));
                    break;
                }else{
                    writer.write(bytes);
                }
            }
        }catch (Exception e){

        }finally {
            fileInputStream.close();
            writer.close();
        }
    }

}
