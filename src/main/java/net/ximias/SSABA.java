package net.ximias;

import net.ximias.handlers.GoodDayHandler;
import net.ximias.handlers.HelloHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class SSABA{
    public static void main(String[] args) throws Exception {

        TimerTask cpumonitorTask = new TimerTask() {
            @Override
            public void run() {
                OperatingSystemMXBean osbean = ManagementFactory.getOperatingSystemMXBean();
                double percentage = 100*osbean.getSystemLoadAverage();
                Register.addCpuDataPoint((byte)percentage);
            }
        };
        Timer cpumonitor = new Timer();
        cpumonitor.scheduleAtFixedRate(cpumonitorTask,0,1000);
        Server server = new Server(8080);


        server.setHandler(createContextHandlers());

        server.start();
        server.join();
    }

    /**
     * Sets up webapps based on their fully qualified names from the webapps.txt file.
     * The name of the class will be the URL extention Ex. a class named "HelloWorld"
     * ends up at "example.com/helloworld" TODO make this modifiable
     * @return the ContextHAndlerCollection containing all webappclasses named in the
     * file "webapps.txt"
     *
     */
    static ContextHandlerCollection createContextHandlers(){
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        File file = new File("webapps.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<String> webappsNames = new ArrayList<String>();
            String line = reader.readLine();
            while (line!=null&&line.length()>2){
                webappsNames.add(line);
                line = reader.readLine();
            }
            for (String webappName : webappsNames) {
                Class<?> cls = Class.forName(webappName);
                Object instance = cls.newInstance();
                if (instance instanceof AbstractHandler){
                    ContextHandler contextHandler = new ContextHandler();
                    System.out.println(cls.getSimpleName());
                    Register.registerWebapp(cls.getSimpleName());
                    contextHandler.setContextPath("/"+cls.getSimpleName().toLowerCase());
                    contextHandler.setHandler((AbstractHandler) instance);
                    contexts.addHandler(contextHandler);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contexts;
    }
}