package org.cm.pro.StaticServer;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticInterceptor{
    public boolean preHandle(
            HttpServletRequest req,
            HttpServletResponse res,
            Object handle
    )throws Exception{
        
        return true;
    }
}
