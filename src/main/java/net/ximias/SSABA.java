package net.ximias;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import java.io.*;
import java.util.ArrayList;

class SSABA{
    public static void main(String[] args) throws Exception {
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
                //Dark arts V
                Class<?> cls = Class.forName(webappName);
                Object instance = cls.newInstance();
                //More Dark arts ^
                if (instance instanceof AbstractHandler){
                    ContextHandler contextHandler = new ContextHandler();
                    System.out.println(cls.getSimpleName());
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