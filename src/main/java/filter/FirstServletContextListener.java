package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Administrator on 2017/6/27.
 */

public class FirstServletContextListener implements ServletContextListener{


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("servletcontext初始化了");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("servletcontext销毁了");
    }
}
