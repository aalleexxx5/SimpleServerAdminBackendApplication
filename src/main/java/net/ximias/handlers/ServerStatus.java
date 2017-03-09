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
        if (httpServletRequest.getParameter("dataSize")!=null){
            System.out.println("ping");
            int dataSize;
            request.setHandled(true);
            try{
                dataSize = Integer.valueOf(httpServletRequest.getParameter("dataSize"));
            }catch (NumberFormatException e){
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpServletResponse.getWriter().print(-1);
                return;
            }catch (NullPointerException e){
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpServletResponse.getWriter().print(-1);
                return;
            }
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            getCpuGraphData(dataSize,httpServletResponse);
            return;
        }
        httpServletResponse.setContentType("text/html; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        String res = "<head></head><body><h1>Server Status</h1><canvas id=\"cpu\" width=\"500\" height=\"300\"style=\"border:1px solid #000000;\">Browser too old</canvas>\n" +
                "<script type=\"text/javascript\">var c = document.getElementById(\"cpu\");\n" +
                "var ctx = c.getContext(\"2d\");\n" +
                "setInterval(httpGetAsync(\"/admin/serverstatus?dataSize=200\",draw),1000);\n" +
                "function draw(text){\n" +
                "    points = text.split(\",\");\n" +
                "    for(var i=1;i<points.length-1;i++){\n" +
                "        var yPA = c.height-(points[i-1]*c.height/100);\n" +
                "        var yPB = c.height-(points[i]*c.height/100);\n" +
                "\t\tctx.moveTo(i-1,yPA);\n" +
                "        ctx.lineTo(i,yPB);\n" +
                "        ctx.stroke();}}\n" +
                "function httpGetAsync(theUrl, callback){\n" +
                "    var xmlHttp = new XMLHttpRequest();\n" +
                "    xmlHttp.onreadystatechange = function() { \n" +
                "        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)\n" +
                "            callback(xmlHttp.responseText);\n" +
                "    }\n" +
                "    xmlHttp.open(\"GET\", theUrl, true); // true for asynchronous \n" +
                "    xmlHttp.send(null);}</script></body>";

        httpServletResponse.getWriter().println(res);
        request.setHandled(true);
    }

    private void getCpuGraphData(int n,HttpServletResponse httpServletResponse) throws IOException{
        String ret="";
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("text/plain; charset=utf-8");
        Byte[] data= Register.getCpuData(n);
        for (Byte datum : data) {
            ret+=datum+",";
        }
        httpServletResponse.getWriter().println(ret.substring(0,ret.length()-1));
    }
}
