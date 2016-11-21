package org.xuqi.geophysics;

import java.io.File;

import org.apache.catalina.startup.Tomcat;

public class StartTomcat {
    int port = 8080;

    private void startTomcat() throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.getConnector().setURIEncoding("UTF-8");
        tomcat.setPort(port);
        tomcat.addWebapp("/", new File("src/main/webapp").getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        StartTomcat main = new StartTomcat();
        //
        main.startTomcat();
//        System.out.println(new Date(1445990400000L));
    }
}