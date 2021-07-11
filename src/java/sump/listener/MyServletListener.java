/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sump.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Administrator
 */
public class MyServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            loadRoadmapToContext(context);
        } catch (FileNotFoundException ex) {
            context.log("MyServletListener _ FileNotFound " + ex.getMessage());
        } catch (IOException ex) {
            context.log("MyServletListener _ IO " + ex.getMessage());
        }
    }

    public void loadRoadmapToContext(ServletContext context)
            throws FileNotFoundException, IOException {
        String realPath = context.getRealPath("/");
        String roadmapFile = realPath + "WEB-INF/roadmap.txt";
        FileReader fr = null;
        BufferedReader br = null;
        Map<String, String> roadmap = new HashMap<>();
        try {
            File f = new File(roadmapFile);
            if (!f.exists()) {
                System.out.println("Not found");
            }//end if file is existed
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String line = "";
            //read file and load to map
            while ((line = br.readLine()) != null) {
                String[] str = line.split("=");
                String key = str[0];
                String value = str[1];
                roadmap.put(key, value);
            }//end traverse file list
            //store attr of context scope
            context.setAttribute("ROADMAP", roadmap);
        } finally {
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.removeAttribute("ROADMAP");
    }
}
