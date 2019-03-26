import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.FixedUidGenerator;
import net.fortuna.ical4j.util.MapTimeZoneCache;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class CalenderWriter {

    final public String eventName = "Ica Jobb";
    private ArrayList<VEvent> eventlist= new ArrayList<>();

    private static boolean init = true;
    private static int count = 0;

    private TimeZoneRegistry registry;
    private TimeZone timezone;
    private VTimeZone tz;

    public void eventCreator(java.util.Calendar[] events) throws SocketException {

        if(init) {
            System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache.class.getName());
            // needs to be here. https://github.com/ical4j/ical4j/issues/195

            registry = TimeZoneRegistryFactory.getInstance().createRegistry();
            timezone = registry.getTimeZone("Europe/Oslo");
            tz = timezone.getVTimeZone();
            init = false;
        }

        events[0].setTimeZone(timezone);
        events[1].setTimeZone(timezone);

        // Create the events
        DateTime start = new DateTime(events[0].getTime());
        DateTime end = new DateTime(events[1].getTime());
        VEvent workpass = new VEvent(start, end, eventName);
        workpass.getProperties().add(tz.getTimeZoneId());

        // Generate unique id
        FixedUidGenerator ug = new FixedUidGenerator(Integer.toString(count++));
        workpass.getProperties().add(ug.generateUid());

        // Add to arraylist
        eventlist.add(workpass);
        System.out.println(eventlist.size());
    }

    /**
     * This function is called at the end of the process at the bottom of ExcelReader.
     * It uses the eventList that has been created and uses a CalendarOutputter to create the file sommarchema.ics
     * @throws IOException
     */
    public void WriteToFile() throws IOException {
        net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId("<- Ica Schema "+ExcelReader.id + " ->" ));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);

        // add the events

        for(VEvent vE : eventlist) {
            icsCalendar.getComponents().add(vE);
        }


        icsCalendar.getProperties().add(Version.VERSION_2_0);

        // generate a file that can be emailed using the globally available id in excelreader
        FileOutputStream fout = new FileOutputStream("sommarschema_" + ExcelReader.id + ".ics" );
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(icsCalendar, fout);
    }

    // Start at april 5 2019 at 06:00

    public void CreateCalender() throws IOException {


        System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache.class.getName());
        // needs to be here. https://github.com/ical4j/ical4j/issues/195

        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Robert//-"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("Europe/Oslo");
        VTimeZone tz = timezone.getVTimeZone();


        // Start at april 5 2019 at 06:00
        java.util.Calendar startDate = startTime(timezone);

        // End at april 5 2019 at 14:45
        java.util.Calendar endDate = endTime(timezone);


        // Create the event
        DateTime start = new DateTime(startDate.getTime());
        DateTime end = new DateTime(endDate.getTime());
        VEvent meeting = new VEvent(start, end, eventName);

        // add timezone info... (even though the events are based on it)
        meeting.getProperties().add(tz.getTimeZoneId());

        // generate unique identifier..
        // TODO: hash this?
        FixedUidGenerator ug = new FixedUidGenerator("6");
        meeting.getProperties().add(ug.generateUid());

        // Create a calendar
        net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId("-//Ica Schema Rz//"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);

        // add the meeting
        icsCalendar.getComponents().add(meeting);
        icsCalendar.getProperties().add(Version.VERSION_2_0);

        // generate a file that can be emailed
        FileOutputStream fout = new FileOutputStream("meeting.ics");
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(icsCalendar, fout);
    }
    public static java.util.Calendar startTime(java.util.TimeZone tz) {
        java.util.Calendar startDate = new GregorianCalendar();
        startDate.setTimeZone(tz);
        startDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 5);
        startDate.set(java.util.Calendar.YEAR, 2019);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 6);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);

        return startDate;
    }
    // End at april 5 2019 at 14:45

    public static java.util.Calendar endTime(java.util.TimeZone tz) {
        java.util.Calendar endDate = new GregorianCalendar();
        endDate.setTimeZone(tz);
        endDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, 5);
        endDate.set(java.util.Calendar.YEAR, 2019);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, 14);
        endDate.set(java.util.Calendar.MINUTE, 45);
        endDate.set(java.util.Calendar.SECOND, 0);

        return endDate;
    }

}
