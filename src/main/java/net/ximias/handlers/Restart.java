package net.ximias.handlers;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Alex on 25/02/2017.
 */
public class Restart extends AbstractHandler {

    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (httpServletRequest.getMethod().equals("POST")){
            if (httpServletRequest.getParameter("reallyRestart")!=null&&httpServletRequest.getParameter("reallyRestart").equals("true")){
                httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
                request.setHandled(true);
                String command ="";
                if (System.getProperty("os.name").equals("Linux")||System.getProperty("os.name").contains("Mac OS")){
                    command = "shutdown -r now";
                }else if (System.getProperty("os.name").contains("Windows")){
                    command = "shutdown -r -t 0";
                }else {
                    throw new UnsupportedEncodingException("Os not supported");
                }
                Runtime.getRuntime().exec(command);
                System.exit(0);
            }
            request.setHandled(true);
            httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }else {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("text/html; charset=utf-8");
            httpServletResponse.getWriter().println("<head></head><body><form action=\"\" method=\"POST\">\n" +
                    "  Really restart?:<br>\n" +
                    "  <input type=\"checkbox\" name=\"reallyRestart\" value=\"true\" >\n" +
                    "  <br><input type=\"submit\" value=\"Submit\">\n" +
                    "</form></body>");
            request.setHandled(true);
        }
    }
}
