package model;

import java.util.Calendar;
import java.util.Date;

//REFERENCE LIST: the following code mimics behaviour seen in AlarmSystem project provided in CPSC 210,
// which can be found at https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git/.

//Represents a shopping cart or shopper event.
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    //Creates an event with the given description and the current date/time stamp
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    //Gets the date of this event (includes time).
    public Date getDate() {
        return dateLogged;
    }

    // Gets the description of this event.
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}