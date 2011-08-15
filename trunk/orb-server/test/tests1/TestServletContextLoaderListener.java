package tests1;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.curl.orb.context.AbstractApplicationContext;
import com.curl.orb.context.ApplicationContextFactory;
import com.curl.orb.context.ServletApplicationContext;

public class TestServletContextLoaderListener implements ServletContextListener
{
    public void contextInitialized(ServletContextEvent event)
    {
        AbstractApplicationContext context = 
            (ApplicationContextFactory.getInstance(event.getServletContext())).getApplicationContext();
        if (context instanceof ServletApplicationContext)
        {
            // test codes
            try {
                tests1.Person person = 
                    new tests1.Person("mori-akira", 2222, "amori", null, null, null);
                ((ServletApplicationContext)context).setObject("amori", person);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void contextDestroyed(ServletContextEvent event)
    {
        ServletContext context = event.getServletContext();
        context.removeAttribute("Person");
    }
}
