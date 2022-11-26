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
    private Date d;
    private ShoppingCart sc;
    private Shopper s;

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");
        d = Calendar.getInstance().getTime();   // (2)
        e2 = new Event("Sensor closed at door");
        s = new Shopper();
        sc = new ShoppingCart(s);
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertEquals(d, e.getDate());
        assertFalse(e.equals(e2));
        assertFalse(e.equals(sc));
        assertFalse(e.equals(null));
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
    }

}
