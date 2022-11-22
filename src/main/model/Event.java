package model;

import java.util.Calendar;
import java.util.Date;

//REFERENCE LIST: the following code mimics behaviour seen in AlarmSystem project provided in CPSC 210,
// which can be found at https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git/.

//Represents a shopping cart or shopper event.
public class Event {
    private String description;

    //Creates an event with the given description and the current date/time stamp
    public Event(String description) {
        this.description = description;
    }

    // Gets the description of this event.
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event event = (Event) o;

        return description.equals(event.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

}