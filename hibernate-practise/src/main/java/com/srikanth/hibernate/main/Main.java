package com.srikanth.hibernate.main;

import com.srikanth.hibernate.entity.Employee;
import com.srikanth.hibernate.entity.Event;
import com.srikanth.hibernate.util.DatabaseLoader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Main.class);
    private static final String COMMA = ",";

    public static void main(String[] args) {

        // Configure hibernate from cfg xml
        URL resource = Main.class.getClassLoader().getResource("hibernate.cfg.xml");
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(resource).build();
        SessionFactory sessionFactory = null;

        try {
            // Build a Session Factory for operations
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Exception e) {
            // Destroy sessionFactory and exit with failure
            StandardServiceRegistryBuilder.destroy(registry);
            logger.error("cannot create sessionFactory", e);
            System.exit(1);
        }

        // create session, open transaction and save test entity to db
        Session session = sessionFactory.openSession();

        try {

            // Pre loaded data
            loadInitialData(session);

            // Process operations required
            processRegistrations(session);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("cannot complete transaction(s)", e);
        } finally {
            session.close();
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    /**
     * Load data to DB
     *
     * @param session
     */
    private static void loadInitialData(Session session) {

        Transaction tx = session.beginTransaction();

        try {
            List<Employee> employeesList = DatabaseLoader.getEmployeesToLoad();
            employeesList.forEach(emp -> session.persist(emp));

            List<Event> eventList = DatabaseLoader.getEventsToLoad();
            eventList.forEach(event -> session.persist(event));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * Perform operations per project
     *
     * @param session
     */
    private static void processRegistrations(Session session) {

        Transaction tx = session.beginTransaction();
        // load events initially to display
        List<Event> events = session.createQuery("from Event").list();

        logger.info("\n\n----------------------------------------");
        System.out.println("\n*** REGISTRATION FOR EVENTS NOW OPEN");
        System.out.println("*** Select from the options below");
        int answer = 0;

        Scanner scan = new Scanner(System.in);

        do {
            System.out.println("\n1. Register employee for events");
            System.out.println("2. Display all employees");
            System.out.println("3. Exit");

            answer = scan.nextInt();
            scan.nextLine();

            switch (answer) {
                case 1:
                    // registration employee for event
                    System.out.println("\nEnter employee id (M100100): ");
                    String empId = scan.nextLine();
                    System.out.println();

                    // find the employee
                    Employee employeeUpdatable = session.get(Employee.class, empId);

                    events.forEach(event -> System.out.println(event.getEventId() + ". " + event.getEventTitle() + " (" + event.getEventDescription() + ")"));
                    System.out.println("\nType event numbers from the list above with comma as a separator. Eg: 1,2,3 -->");
                    String eventsCsv = scan.nextLine();

                    String[] eventsArr = eventsCsv.split(COMMA);
                    for (String eventNum : eventsArr) {
                        // find the event
                        Event eventToReg = session.get(Event.class, Long.parseLong(eventNum));
                        Set<Event> empEvents = employeeUpdatable.getEvents();
                        if (empEvents == null) {
                            empEvents = new HashSet<>();
                        }
                        empEvents.add(eventToReg);
                    }
                    session.saveOrUpdate(employeeUpdatable);
                    tx.commit();

                    break;
                case 2:
                    // display employees
                    List<Employee> employees = session.createQuery("from Employee").list();
                    employees.forEach(
                            employee -> System.out.println(employee.getEmployeeId() + " :: " + employee.getEmployeeName() + " :: " +
                                    employee.getJoiningDate() + " :: " + employee.getEmailId() + " :: Events: " + employee.getEventTitles()));
                    break;
                case 3:
                    // exit
                    System.out.println("Exiting system gracefully...");
                    System.exit(0);
                    break;
                default:
                    answer = 0;
                    System.out.println("\nChoose from the options.\n");
                    break;
            }
        } while (answer != 3);
    }
}

