package com.srikanth.hibernate.util;

import com.srikanth.hibernate.entity.Employee;
import com.srikanth.hibernate.entity.Event;

import java.util.ArrayList;
import java.util.List;

public class DatabaseLoader {


    public static List<Employee> getEmployeesToLoad() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("M100100", "Karthik Kumar", "2013-02-05", "karthik_kumar"));
        employees.add(new Employee("M100108", "Ramesh Kulkarni", "2013-02-05", "ramesh_kulkarni"));
        employees.add(new Employee("M100189", "Mohan Agarwal M", "2013-03-22", "rohit_agarwal_m"));
        employees.add(new Employee("M101190", "Magesh Narayanan", "2013-03-22", "magesh_narayanan"));
        return employees;
    }

    public static List<Event> getEventsToLoad() {
        List<Event> events = new ArrayList<>();
        events.add(new Event(1, "Trekking", "Held every month. More details from Manish Kumar."));
        events.add(new Event(2, "Guitar Classes", "Weekly 3 sessions. Classes conducted by Daniel M"));
        events.add(new Event(3, "Yoga Classes", "Saturdays and Sundays. Classes conducted by Yamini"));
        events.add(new Event(4, "Health & Diet tips", "Every Friday 5 PM to 6 PM by Dr.Kishore Dutta"));
        return events;
    }
}
