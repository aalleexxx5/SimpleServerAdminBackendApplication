package net.ximias.handlers;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Alex on 20/03/2017.
 */
public class RandomColor extends AbstractHandler{
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = httpServletResponse.getWriter();
        int red,green,blue;
        red = (int) (Math.random()*256);
        green = (int) (Math.random()*256);
        blue = (int) (Math.random()*256);
        out.println("<html><head></head><body bgcolor=\""+Integer.toHexString(red)+Integer.toHexString(green)+Integer.toHexString(blue)+"\"></body></html>");

        request.setHandled(true);
    }
}
