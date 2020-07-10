package org.cm.pro.StaticServer;

public class StaticContentType {
    public enum ContentType{
        html("text/html;charset=utf8"),
        txt("text/html"),
        css("text/css"),
        jpg("image/jpeg"),
        png("image/png"),
        git("image/gif"),
        jpeg("image/jpeg"),
        ico("image/x-icon"),
        js("application/x-javascript"),
        woff("'application/x-font-woff"),
        woff2("application/x-font-woff"),
        svg("image/svg+xml"),
        oft("application/x-font-otf"),
        ttf("application/x-font-ttf"),
        eot("application/vnd.ms-fontobject"),
        mp4("video/mpeg4"),
        ogg("application/ogg");

        private String contentType;
        private ContentType(String contentType){
           this.contentType=contentType;
        }
        public String getContentType(){
            return contentType;
        }
    }
    public String getType(String type){
        if(type==null) return "application/octet-stream";
        try {
            ContentType contentType = ContentType.valueOf(type);
            return contentType.getContentType();
        }catch (IllegalArgumentException e){
            return "application/octet-stream";
        }
    }
    public String getFileType(String fileName){
        int lastIndex=fileName.lastIndexOf(".");
        if(lastIndex<0)return getType(null);
        return getType(fileName.substring(lastIndex+1));
    }
}
