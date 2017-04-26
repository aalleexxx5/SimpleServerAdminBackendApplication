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
 * Handler for making sure the proper authentication is used.
 */
public class Admin extends ContextHandlerCollection {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html; charset=utf-8");
        if (request.getHeader("Authorization") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("WWW-Authenticate","Basic realm=\"admin login\"");
            response.getWriter().println("UNAUTHORIZED");
            baseRequest.setHandled(true);
        } else {
            System.out.println(request.getHeader("Authorization"));
            if (!request.getHeader("Authorization").equals("Basic "+ Register.getAdminCredentials())) { //Authentication was invalid
                response.setHeader("WWW-Authenticate","Basic realm=\"admin login\"");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("Access denied");
                baseRequest.setHandled(true);
            } else { //request was valid:
                super.handle(target, baseRequest, request, response);
            }
        }
    }
}
