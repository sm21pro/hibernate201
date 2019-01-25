package com.srikanth.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "EVENTS")
public class Event implements Serializable {

    public Event() {
    }

    public Event(long eventId, String eventTitle, String eventDescription) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
    }

    @Id
    @Column(name = "EVENT_ID", unique = true, nullable = false)
    private long eventId;

    @Column(name = "EVENT_TITLE", unique = true, nullable = false, length = 200)
    private String eventTitle;

    @Column(name = "DESCRIPTION", unique = true, nullable = false, length = 200)
    private String eventDescription;


    @ManyToMany(mappedBy = "events")
    private Set<Employee> employees = new HashSet<>();

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId &&
                Objects.equals(eventTitle, event.eventTitle) &&
                Objects.equals(eventDescription, event.eventDescription);
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventTitle, eventDescription);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                "}";
    }
}
