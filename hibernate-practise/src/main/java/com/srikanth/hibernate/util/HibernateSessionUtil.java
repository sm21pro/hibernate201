package com.srikanth.hibernate.util;

import com.srikanth.hibernate.main.Main;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.net.URL;

public class HibernateSessionUtil {

    private static SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(HibernateSessionUtil.class);

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // Configure hibernate from cfg xml
            URL resource = Main.class.getClassLoader().getResource("hibernate.cfg.xml");
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(resource).build();

            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                // Destroy sessionFactory and exit with failure
                StandardServiceRegistryBuilder.destroy(registry);
                logger.error("Cannot create sessionFactory", e);
                System.exit(1);
            }
        }

        return sessionFactory;
    }
}
