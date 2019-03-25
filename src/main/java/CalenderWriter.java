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
import java.util.GregorianCalendar;

public class CalenderWriter {

    public static void main(String[] args) throws IOException {


        System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache.class.getName());

        // needs to be here. https://github.com/ical4j/ical4j/issues/195

        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Robert//-"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);


        if(false) {
            System.out.println(calendar.toString());

            java.util.Calendar Jcalendar = java.util.Calendar.getInstance();
            Jcalendar.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
            Jcalendar.set(java.util.Calendar.DAY_OF_MONTH, 25);

// initialise as an all-day event..
            VEvent christmas = new VEvent(new Date(Jcalendar.getTime()), "Christmas Day");

// Generate a UID for the event..
            FixedUidGenerator ug = new FixedUidGenerator("1");
            christmas.getProperties().add(ug.generateUid());

            calendar.getComponents().add(christmas);

            System.out.println(calendar.getComponents());


            FileOutputStream fout = new FileOutputStream("mycalendar.ics");

            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, fout);
        }


        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("Europe/Oslo");
        VTimeZone tz = timezone.getVTimeZone();


        // Start at april 5 2019 at 06:00
        java.util.Calendar startDate = new GregorianCalendar();
        startDate.setTimeZone(timezone);
        startDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 5);
        startDate.set(java.util.Calendar.YEAR, 2019);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 6);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);

        // End at april 5 2019 at 14:45
        java.util.Calendar endDate = new GregorianCalendar();
        endDate.setTimeZone(timezone);
        endDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, 5);
        endDate.set(java.util.Calendar.YEAR, 2019);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, 14);
        endDate.set(java.util.Calendar.MINUTE, 45);
        endDate.set(java.util.Calendar.SECOND, 0);

// Create the event
        String eventName = "Ica Jobb";
        DateTime start = new DateTime(startDate.getTime());
        DateTime end = new DateTime(endDate.getTime());
        VEvent meeting = new VEvent(start, end, eventName);

        // add timezone info..
        meeting.getProperties().add(tz.getTimeZoneId());

// generate unique identifier..

        FixedUidGenerator ug = new FixedUidGenerator("6");
        //Uid uid = ug.generateUid();
        meeting.getProperties().add(ug.generateUid());



// Create a calendar
        net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId("-//Ica Schema Rz//"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);

        icsCalendar.getComponents().add(meeting);
        System.out.println(icsCalendar);


        FileOutputStream fout = new FileOutputStream("meeting.ics");

        icsCalendar.getProperties().add(Version.VERSION_2_0);
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(icsCalendar, fout);


        fout.close();


    }
}
