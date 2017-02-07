package net.ximias.handlers;

import net.ximias.Register;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alex on 07/02/2017.
 */
public class RunningApps extends AbstractHandler {
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        String response="<head><style>table{border-collapse: collapse;width: 100%;}" +
                "td, th{border: 1px solid #000000;text-align: left;padding: 8px;}</style></head>" +
                "<body><h1>Webapps:</h1>";
        response+="<table style=width:100%><br/><tr><th>Webapp</th></tr>";
        for (String appname : Register.getWebappNames()) {
            response+="<tr><td><a href=\"/"+appname.toLowerCase()+"\">"+appname+"</a></td></tr>";
        }
        response+="</table></body>";
        httpServletResponse.getWriter().println(response);
        request.setHandled(true);
    }
}
