package net.ximias.handlers;

import net.ximias.Register;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import sun.misc.BASE64Encoder;
import sun.nio.cs.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Alex on 08/02/2017.
 */
public class UpdateCredentials extends AbstractHandler{
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        System.out.println(httpServletRequest.getMethod());
        httpServletResponse.setContentType("text/html; charset=utf-8");
        if (httpServletRequest.getMethod().equals("GET")){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            String ret="<head></head><body><form action=\"\" method=\"POST\">\n" +
                    "  Username:<br>\n" +
                    "  <input type=\"text\" name=\"user\" value=\"admin\"><br>\n" +
                    "  Password:<br>\n" +
                    "  <input type=\"password\" name=\"password\" value=\"admin\"><br>\n" +
                    "  Repeat Password:<br>\n" +
                    "  <input type=\"password\" name=\"repeatpassword\" value=\"admin\">\n" +
                    "  <br><input type=\"submit\" value=\"Submit\">\n" +
                    "</form></body>";
            httpServletResponse.getWriter().println(ret);
            request.setHandled(true);
        }else if (httpServletRequest.getMethod().equals("POST")){
            if (httpServletRequest.getParameter("user")!=null&&httpServletRequest.getParameter("password")!=null&&httpServletRequest.getParameter("repeatpassword")!=null){
                if (httpServletRequest.getParameter("password").equals(httpServletRequest.getParameter("repeatpassword"))){
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                    httpServletResponse.getWriter().println("<head></head><body><h1>Password update successful</h1></body>");
                    BASE64Encoder encoder = new BASE64Encoder();
                    String cred = httpServletRequest.getParameter("user")+":"+httpServletRequest.getParameter("password");
                    System.out.println(cred);
                    String encodedCred = encoder.encode(cred.getBytes("UTF8"));
                    System.out.println(encodedCred);
                    Register.updateAdminCredentials(encodedCred);
                    request.setHandled(true);
                    return;
                }
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpServletResponse.getWriter().println("<head></head><body><h1>Passwords did not match</hi></body>");
                request.setHandled(true);
                return;
            }
            httpServletResponse.getWriter().println("<head></head><body><h1>Error</hi></body>");
            request.setHandled(true);
        }
    }
}
