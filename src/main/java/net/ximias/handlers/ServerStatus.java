package net.ximias.handlers;

import net.ximias.Register;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alex on 07/02/2017.
 */
public class ServerStatus extends AbstractHandler {
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (httpServletRequest.getPathInfo().equalsIgnoreCase("/cpugraphdata")){
            int dataSize;
            try{
                dataSize = Integer.valueOf(httpServletRequest.getParameter("dataSize"));
            }catch (NumberFormatException e){
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }catch (NullPointerException){
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            getCpuGraphData(dataSize,httpServletResponse);
            request.setHandled(true);
            return;
        }
        httpServletResponse.setContentType("text/plain; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.getWriter().println(httpServletRequest.getPathInfo());
        request.setHandled(true);
    }

    private void getCpuGraphData(int n,HttpServletResponse httpServletResponse){
        String ret;
    }
}
