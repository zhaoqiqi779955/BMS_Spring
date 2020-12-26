package filter;

import af.sql.c3p0.AfSimpleDB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class FirstServletContextListener implements ServletContextListener{


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        String sql="set global event_scheduler = on";
        try {
            AfSimpleDB.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        String sql="set global event_scheduler = off";

        try {
            AfSimpleDB.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
