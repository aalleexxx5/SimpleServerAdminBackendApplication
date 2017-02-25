package net.ximias;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;

/**
 * Created by Alex on 21/02/2017.
 */
public class WebappDeployer {

    public static void deployWebapp(File app){
        //compile the file, get the fully qualified name, save it in a file, load in the class to be used by the server
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        try {
            File out= new File("app/"+app.getName()+".class");
            if (!out.exists()){
                out.createNewFile();
            }
            javaCompiler.run(new FileInputStream(app),new FileOutputStream(out),System.out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}