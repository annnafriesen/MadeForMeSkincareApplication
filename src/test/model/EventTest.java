package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

//REFERENCE LIST: the following code mimics behaviour seen in AlarmSystem project provided in CPSC 210,
// which can be found at https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git/.

//Tests for the Event class
public class EventTest {
    private Event e;
    private Event e2;

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");
        e2 = new Event("Sensor closed at door");
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertFalse(e.equals(e2));
        assertFalse(e.equals(null));
        assertEquals(-195810773, e.hashCode());
    }



}
