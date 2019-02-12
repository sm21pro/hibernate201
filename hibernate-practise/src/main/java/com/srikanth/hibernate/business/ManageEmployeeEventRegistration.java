package com.srikanth.hibernate.business;

import com.srikanth.hibernate.dao.ManageEmployeeDao;
import com.srikanth.hibernate.dao.ManageEventDao;
import com.srikanth.hibernate.entity.Employee;
import com.srikanth.hibernate.entity.Event;
import com.srikanth.hibernate.exception.EventRegistrationFailureException;
import com.srikanth.hibernate.util.DatabaseLoader;
import com.srikanth.hibernate.util.PersistType;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ManageEmployeeEventRegistration {

    private final ManageEmployeeDao manageEmployeeDao = new ManageEmployeeDao();
    private final ManageEventDao manageEventDao = new ManageEventDao();

    private static final String COMMA = ",";


    /**
     * Case 2 : Displays all employees with events registered
     */
    public void displayAllEmployees() throws EventRegistrationFailureException {
        List<Employee> employees = manageEmployeeDao.getEmployees();
        System.out.println("\n"); // two new lines
        employees.forEach(
                employee -> System.out.println(employee.getEmployeeId() + " :: " + employee.getEmployeeName() + " :: " +
                        employee.getJoiningDate() + " :: " + employee.getEmailId() + " :: Events: " + employee.getEventTitles()));
    }

    /**
     * Case 1 : Registers employee for an event
     *
     * @param scan
     * @throws EventRegistrationFailureException
     */
    public void registerEmployeeForEvent(final Scanner scan) throws EventRegistrationFailureException {

        // registration employee for event
        System.out.println("\nEnter employee id (M100100): ");
        String empId = scan.nextLine();
        System.out.println();

        // find the employee
        Employee employeeUpdatable = manageEmployeeDao.fetchEmployee(empId);

        // fetch events to display
        List<Event> events = manageEventDao.getEvents();

        System.out.println("\n"); // Two new lines
        events.forEach(event -> System.out.println(event.getEventId() + ". " + event.getEventTitle() + " (" + event.getEventDescription() + ")"));
        System.out.println("\nType event numbers from the list above with comma as a separator. Eg: 1,2,3 -->");
        String eventsCsv = scan.nextLine();

        String[] eventsArr = eventsCsv.split(COMMA);
        for (String eventNum : eventsArr) {
            // find the event
            Event eventToReg = manageEventDao.fetchEvent(Long.parseLong(eventNum));
            Set<Event> empEvents = employeeUpdatable.getEvents();
            if (empEvents == null) {
                empEvents = new HashSet<>();
            }
            empEvents.add(eventToReg);
        }
        manageEmployeeDao.saveOrUpdateEmployee(employeeUpdatable, PersistType.UPDATE);
    }

    /**
     * Initializes database with dummy employee and event details
     *
     * @throws EventRegistrationFailureException
     */
    public void initializeDatabase() throws EventRegistrationFailureException {

        List<Employee> employeesList = DatabaseLoader.getEmployeesToLoad();
        for (Employee emp : employeesList) {
            manageEmployeeDao.saveOrUpdateEmployee(emp, PersistType.SAVE);
        }

        List<Event> eventList = DatabaseLoader.getEventsToLoad();
        for (Event event : eventList) {
            manageEventDao.saveOrUpdateEvent(event, PersistType.SAVE);
        }
    }
}
