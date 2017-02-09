package net.ximias.handlers;

import net.ximias.Register;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alex on 08/02/2017.
 */
public class Admin extends ContextHandlerCollection {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html; charset=utf-8");
        if (request.getHeader("Authorization") != null) {
            System.out.println(request.getHeader("Authorization"));
            if (request.getHeader("Authorization").equals("Basic "+Register.getAdminCredentials())){
                super.handle(target, baseRequest, request, response);
                return;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate","Basic realm=\"admin login\"");
        response.getWriter().println("Admin page");
        baseRequest.setHandled(true);
    }
}
