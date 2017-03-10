package net.ximias.handlers;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;

/**
 * Created by Alex on 24/02/2017.
 */
public class Delete extends AbstractHandler {
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html; charset=utf-8");
        if (httpServletRequest.getMethod().equals("GET")){
            request.setHandled(true);
            String response = "<html><head></head><body><table><th>Content name</th><th>delete</th>";
            File content = new File("content");
            for (File file : content.listFiles()) {
                response += "<tr><td>"+file.getName()+"</td><td><form method=\"post\"><input type=\"hidden\" name=\"name\" value=\""+file.getPath()+"\" /><input type=\"submit\" value=\"Delete\" /></form></td></tr>";
            }
            response += "</table></body></html>";
            httpServletResponse.getWriter().println(response);
        }else if (httpServletRequest.getMethod().equals("POST")){
            request.setHandled(true);
            if (httpServletRequest.getParameter("name")!=null){
                File file = new File(httpServletRequest.getParameter("name"));
                if (file.exists()){
                    file.delete();
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                    httpServletResponse.getWriter().println("<head></head><body><h1>Delete successful!</h1></body>");
                }else{
                    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    httpServletResponse.getWriter().println("<head></head><body><h1>Internal server error</h1><br/><h2>The file \""+httpServletRequest.getParameter("name")+"\" was not found</h2></body>");
                }
            }else {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpServletResponse.getWriter().println("<head></head><body><h1>Bad request</h1><br/><h2>No name parameter was specified</h2></body>");
            }
            //TODO
        }
    }
}
