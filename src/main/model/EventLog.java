package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//REFERENCE LIST: the following code mimics behaviour seen in AlarmSystem project provided in CPSC 210,
// which can be found at https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git/.

//Represents a log of alarm system events.
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    //Constructs a new log of events
    private EventLog() {
        events = new ArrayList<Event>();
    }

    //Gets instance of EventLog - creates it if it doesn't already exist.
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    //Adds an event to the event log.
    public void logEvent(Event e) {
        events.add(e);
    }

    //Clears the event log and logs the event.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    //EFFECTS: getter for events
    public Collection<Event> getEvents() {
        return events;
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
