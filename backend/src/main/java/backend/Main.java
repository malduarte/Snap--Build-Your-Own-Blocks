package backend;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;

/**
 * 
 * This class launches the web application in an embedded Jetty container.
 * This is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 *
 */
public class Main {
    
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        String webappDirLocation = "webapp/";
        
        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = args.length > 0 ? args[0] : "8080";


        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext root = new WebAppContext();

        final URL warUrl = root.getClass().getClassLoader().getResource(webappDirLocation);
        final String warUrlString = warUrl.toExternalForm();
        root.setContextPath("/");
        root.setDescriptor(warUrlString+"/WEB-INF/web.xml");
        root.setResourceBase(warUrlString);

        //Parent loader priority is a class loader setting that Jetty accepts.
        //By default Jetty will behave like most web containers in that it will
        //allow your application to replace non-server libraries that are part of the
        //container. Setting parent loader priority to true changes this behavior.
        //Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
        root.setParentLoaderPriority(true);
        
        server.setHandler(root);
        
        server.start();
        server.join();   
    }

}
