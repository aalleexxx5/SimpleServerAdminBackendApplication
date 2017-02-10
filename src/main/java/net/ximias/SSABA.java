package net.ximias;

import net.ximias.handlers.Admin;
import net.ximias.handlers.GoodDayHandler;
import net.ximias.handlers.HelloHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

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
     * Sets up webapps based on their fully qualified names from the adminwebapps.txt file.
     * The name of the class will be the URL extention Ex. a class named "HelloWorld"
     * ends up at "example.com/helloworld" TODO make this modifiable
     * @return the ContextHAndlerCollection containing all webappclasses named in the
     * file "adminwebapps.txt"
     *
     */
    static ContextHandlerCollection createContextHandlers(){
        ContextHandlerCollection root = new ContextHandlerCollection();
        ContextHandler serverContent = new ContextHandler();
        File contentDir = new File("content");
        serverContent.setBaseResource(Resource.newResource(contentDir));
        serverContent.setContextPath("/");
        serverContent.setHandler(new ResourceHandler());
        root.addHandler(serverContent);

        ContextHandlerCollection admin = new Admin();
        ContextHandler adminContent = new ContextHandler();
        File adminDir = new File("admincontent");
        adminContent.setBaseResource(Resource.newResource(adminDir));
        adminContent.setContextPath("/admin/");
        adminContent.setHandler(new ResourceHandler());
        admin.addHandler(adminContent);

        root.addHandler(addWebappsFromFile("adminwebapps.txt","/admin/",admin));
        root.addHandler(addWebappsFromFile("webapps","/app/",new ContextHandlerCollection()));
        return root;
    }

    static ContextHandlerCollection addWebappsFromFile(String filename,String path,ContextHandlerCollection context){
        File file = new File(filename);
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<String> webappsNames = new ArrayList<String>();
            String line = reader.readLine();
            while (line!=null&&line.length()>2){
                webappsNames.add(line);
                line = reader.readLine();
            }
            reader.close();
            for (String webappName : webappsNames) {
                Class<?> cls = Class.forName(webappName);
                Object instance = cls.newInstance();
                if (instance instanceof AbstractHandler){
                    ContextHandler contextHandler = new ContextHandler();
                    System.out.println(cls.getSimpleName());
                    Register.registerWebapp(cls.getSimpleName(),path+cls.getSimpleName().toLowerCase());
                    contextHandler.setContextPath(path+cls.getSimpleName().toLowerCase());
                    contextHandler.setHandler((AbstractHandler) instance);
                    context.addHandler(contextHandler);
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
        return context;
    }
}