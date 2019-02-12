package com.srikanth.hibernate.main;

import com.srikanth.hibernate.business.ManageEmployeeEventRegistration;
import com.srikanth.hibernate.exception.EventRegistrationFailureException;

import java.util.Scanner;

public class Main {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Main.class);

    private final ManageEmployeeEventRegistration business = new ManageEmployeeEventRegistration();

    public static void main(String[] args) {

        final Main mainInstance = new Main();
        try {
            // Pre loaded data
            mainInstance.loadInitialData();

            // Process operations required
            mainInstance.processRegistrations();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Cannot complete transaction(s)", ex);
        } finally {

        }
    }

    /**
     * Load data to DB
     */
    private void loadInitialData()
            throws EventRegistrationFailureException {

        business.initializeDatabase();
    }

    /**
     * Perform operations per project
     */
    private void processRegistrations() throws EventRegistrationFailureException {
        
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
                    business.registerEmployeeForEvent(scan);
                    break;
                case 2:
                    business.displayAllEmployees();
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

