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
public class ColorGuess extends AbstractHandler {
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        if (httpServletRequest.getMethod().equals("GET")) {
            PrintWriter out = httpServletResponse.getWriter();
            int red, green, blue;
            red = (int) (Math.random() * 256);
            green = (int) (Math.random() * 256);
            blue = (int) (Math.random() * 256);
            httpServletRequest.getSession().setAttribute("colorred", red);
            httpServletRequest.getSession().setAttribute("colorgreen", green);
            httpServletRequest.getSession().setAttribute("colorblue", blue);
            out.println("<html><head></head><body bgcolor=\"" + Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue) + "\">" +
                    "<form action=\"\" method=\"POST\">" +
                    "<input type= \"text\" name=\"colorguess\"/>" +
                    "<input type= \"submit\" value\"Guess!\">" +
                    "</form>" +
                    "</body></html>");
        }else if (httpServletRequest.getMethod().equals("POST")){
            if (httpServletRequest.getParameter("colourguess")!=null&&httpServletRequest.getParameter("colourguess").length()==6){
                int guessRed, guessGreen, guessBlue;
                try{
                    guessRed = Integer.parseInt(httpServletRequest.getParameter("colourguess").substring(0,2),16);
                    guessGreen = Integer.parseInt(httpServletRequest.getParameter("colourguess").substring(2,4),16);
                    guessBlue = Integer.parseInt(httpServletRequest.getParameter("colourguess").substring(4,6),16);
                    System.out.println(httpServletRequest.getSession().getAttribute("colorred"));
                }catch (NumberFormatException e){
                    //Invalid number in guess
                }
            }else{
                //invalid post
            }
        }
        request.setHandled(true);
    }
}
