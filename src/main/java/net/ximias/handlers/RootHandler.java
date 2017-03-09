package net.ximias.handlers;

import net.ximias.Register;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alex on 09/03/2017.
 */
public class RootHandler extends ContextHandlerCollection {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println(request.getRequestURL());
        if (request.getRequestURL().toString().startsWith("http:")){
            String httpsTarget = request.getRequestURL().toString().replace("http:","https:");
            httpsTarget = httpsTarget.replace(":"+ Register.HTTP_PORT,":"+Register.SSL_PORT);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().println("<html>\n" +
                    "<head><title>Redirecting...</title></head>\n" +
                    "<script language=\"JavaScript\">\n" +
                    "{\n" +
                    "    window.location = \""+httpsTarget+"\";\n" +
                    "}\n" +
                    "</script>\n" +
                    "<body>\n" +
                    "</body>\n" +
                    "</html>");
            baseRequest.setHandled(true);
            return;
        }
        super.handle(target, baseRequest, request, response);
    }
}
