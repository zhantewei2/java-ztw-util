package org.cm.pro.StaticServer;

import javax.servlet.http.HttpServletResponse;

public class StaticZTWSign {
    static public void sign(HttpServletResponse res){
        res.setHeader("Static-Author","zhantewei");
    }

}
