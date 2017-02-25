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
        if (httpServletRequest.getMethod().equals("GET")){
            httpServletResponse.setContentType("text/html; charset=utf-8");
            String response = "<html><head></head><body><table><th>Content name</th><th>delete</th>";
            File content = new File("content");
            for (File file : content.listFiles()) {
                response += "<tr><td>"+file.getName()+"</td><td><button type=\"submit\" formmethod=\"delete\" name=\""+file.getAbsolutePath()+"\">delete</button></td></tr>";
            }
            response += "</table></body></html>";
            httpServletResponse.getWriter().println(response);
            request.setHandled(true);
        }
    }
}
