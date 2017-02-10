package net.ximias.handlers;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;

/**
 * Created by Alex on 07/02/2017.
 */
public class Upload extends AbstractHandler {
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if(httpServletRequest.getMethod().equals("GET")){

            httpServletResponse.setContentType("text/html; charset=utf-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);

            PrintWriter out = httpServletResponse.getWriter();

            out.println("<form method=\"POST\" action=\"\" name=\"submit\" enctype=\"multipart/form-data\">\n" +
                    "<input type=\"file\" name=\"fileField\"><br /><br />\n" +
                    "<select name=\"fileLocation\"><option value=\"/adminjars/\">adminApp</option><option value=\"/admincontent/\">adminContent</option><option value=\"/content/\">content</option><option value=\"/app/\">app</option></select><br/>"+
                    "<input type=\"submit\" name=\"submit\" value=\"Submit\">\n" +
                    "</form>");
        }else if (httpServletRequest.getMethod().equals("POST")){
            System.out.println("POST received");
            if (httpServletRequest.getContentType().startsWith("multipart/form-data")){
                httpServletRequest.setAttribute(Request.__MULTIPART_CONFIG_ELEMENT,new MultipartConfigElement("download/"));
                Collection<Part> fileparts = httpServletRequest.getParts();
                if (!fileparts.isEmpty()){
                    for (Part filepart : fileparts) {
                        if (filepart.getSubmittedFileName()==null) continue;
                        System.out.println("part:");
                        System.out.println(filepart.getSubmittedFileName());
                        System.out.println(filepart.getName());
                        System.out.println(filepart.getContentType());
                        System.out.println(filepart.getSize());
                        File dest = new File("download/"+httpServletRequest.getParameter("fileLocation")+filepart.getSubmittedFileName());
                        Files.copy(filepart.getInputStream(),dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                    httpServletResponse.getWriter().print("<h1>Success</h1>");
                }
            }
        }
        request.setHandled(true);
    }
}
