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

public class CalendarWriter {

    private ArrayList<VEvent> eventlist= new ArrayList<>();

    private static boolean init = true;
    private static int count = 0;

    private TimeZoneRegistry registry;
    private TimeZone timezone;
    private VTimeZone tz;

    public void eventCreator(java.util.Calendar[] events, String time) throws SocketException {

        if(init) {
            System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache.class.getName());
            // needs to be here. https://github.com/ical4j/ical4j/issues/195

            registry = TimeZoneRegistryFactory.getInstance().createRegistry();
            timezone = registry.getTimeZone("Europe/Oslo");
            tz = timezone.getVTimeZone();
            init = false;
        }

        StringBuilder eventName =  new StringBuilder();

        eventName.append("Ica Jobb ");

        eventName.append(time);
        System.out.println(eventName);

        events[0].setTimeZone(timezone);
        events[1].setTimeZone(timezone);



        // Create the events
        DateTime start = new DateTime(events[0].getTime());
        DateTime end = new DateTime(events[1].getTime());
        VEvent workpass = new VEvent(start, end, eventName.toString());
        workpass.getProperties().add(tz.getTimeZoneId());

        // Generate unique id
        FixedUidGenerator ug = new FixedUidGenerator(Integer.toString(count++));
        workpass.getProperties().add(ug.generateUid());

        // Add to arraylist
        eventlist.add(workpass);
        System.out.println(eventlist.size());
    }

    /**
     * This function is called at the end of the process at the bottom of Dated_ExcelReader.
     * It uses the eventList that has been created and uses a CalendarOutputter to create the file sommarchema.ics
     * @throws IOException
     */
    public void WriteToFile() throws IOException {
        net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId("<- Ica Schema "+ Dated_ExcelReader.id + " ->" ));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);

        // add the events

        for(VEvent vE : eventlist) {
            icsCalendar.getComponents().add(vE);
        }


        icsCalendar.getProperties().add(Version.VERSION_2_0);

        // generate a file that can be emailed using the globally available id in excelreader
        FileOutputStream fout = new FileOutputStream("Examples/" + ExcelReader.id + "_sommarschema.ics" );
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(icsCalendar, fout);
    }
}
