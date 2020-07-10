package org.cm.pro.StaticServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;

public interface StaticServerInterface {
    public boolean run(HttpServletRequest req, HttpServletResponse res) throws Exception;

}
