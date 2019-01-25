package com.srikanth.hibernate.main;

import com.srikanth.hibernate.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger("HibernateLogger");

    public static void main(String[] args) {

        URL resource = Main.class.getClassLoader().getResource("hibernate.cfg.xml");

        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure(resource)
                        .build();

        SessionFactory sessionFactory = null;

        try {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            logger.log(Level.SEVERE, "cannot create sessionFactory", e);
            System.exit(1);
        }

        // create session, open transaction and save test entity to db
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            Employee employee = new Employee();
            employee.setEmployeeId("M1023174");
            employee.setEmailId("m@g.com");
            employee.setEmployeeName("Srikanth M");
            employee.setJoiningDate("2013-06-17");

            session.persist(employee);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            logger.log(Level.SEVERE, "cannot commit transaction", e);
        } finally {
            session.close();
        }

        // clean up
        sessionFactory.close();
    }
}

