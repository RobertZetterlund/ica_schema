import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

public class ExcelReader {

    // ----------------------------- START ADJUSTABLES ----------------------------- //
    private static final String schema_2019 = "/Users/robertzetterlund/Projects_prog/ica_schema/src/main/resources/Schema-Testschema.xlsx";

    // -----------------------------  END ADJUSTABLES  ----------------------------- //

    /**
     * id which workers have, used in name of created file. will be changed when it is able to read from schema.
     */
    static String id = "test";


    private int rowI = 0;
    private int colI = 0;
    private EventCreator eC = new EventCreator();
    private CalendarWriter cW = new CalendarWriter();

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ExcelReader eR = new ExcelReader();
        eR.app();
    }

    public void app() throws IOException, InvalidFormatException {

        String cellValue;

        // determines whether or not an event should be created. evaluates on dates where times are found.
        boolean working = false;


        int[] eventTimes;
        Workbook workbook = WorkbookFactory.create(new File(schema_2019));

        // Retrieving the sheet in the Workbook
        Sheet sheet = workbook.getSheetAt(0);

        WorkDay wD = new WorkDay();

        for (Row row : sheet) {
            rowI++;
            if (rowI % 8 == 6) { //  (rowI % 8 == 6) the distance between the events are 8, and the first row of work is on index 6
                for (Cell cell : row) {
                    colI++;
                    cellValue = cell.getStringCellValue();

                    // the excel-fie has [time | date] and date is even and time is odd. meaning, if working AND even index, create event.
                    if (colI % 2 == 0 && working) {
                        if (!cell.getStringCellValue().equals("")) {
                            int[] date = parsedate(cellValue);
                            wD.day = date[0];
                            wD.month = date[1];

                            System.out.println(wD.toString());
                            createEvent(wD);

                            wD = new WorkDay();
                            working = false;
                        }
                    } else { // we search for times, ":" being a somewhat unique character is passable as a check.
                        if (cellValue.contains(":")) {

                            eventTimes = ParseTimesIntoIntArray(cellValue);
                            // eventimes = [int,int,int,int]
                            // [starhour,startmin,endhour,endmin]


                            wD.startTimeHour = eventTimes[0];
                            wD.startTimeMin = eventTimes[1];
                            wD.endTimeHour = eventTimes[2];
                            wD.endTimeMin = eventTimes[3];
                            working = true;
                        }

                    }
                }
                colI = 0; // reset colI when row is completed
            }
        }

        // being finished, all events are collected, we write to file.
        cW.WriteToFile();
    }


    /**
     * Parses the date which is formatted as "date/month" as numbers and returns a "pair" of integers, [day, month].
     */
    public int [] parsedate(String value) {
        int[] date = new int [2];
        String [] s = value.split("/");
        date[0] = Integer.valueOf(s[0]);
        date[1] = Integer.valueOf(s[1]);
        return date;
    }


    /**
     * Gives the work done to CalendarWriter, which creates a event and stores.
     */
    private void createEvent(WorkDay workDay) throws SocketException {

        String time = formatTime(workDay);

        cW.eventCreator(eC.getTimes(workDay),time);
    }


    /**
     * Some serious reverse-engineering. Take int values and make them into string as a digital clock would present them.
     * @param workDay information about time, in ints.
     * @return a string of worktimes.
     */
    private String formatTime (WorkDay workDay) {

        StringBuilder time = new StringBuilder();

        time.append((workDay.startTimeHour < 10 ? "0" : ""));
        time.append(workDay.startTimeHour);
        time.append(":");
        time.append((workDay.startTimeMin < 10 ? "0" : ""));
        time.append(workDay.startTimeMin);

        time.append(" - ");

        time.append((workDay.endTimeHour < 10 ? "0" : ""));
        time.append(workDay.endTimeHour);
        time.append(":");
        time.append((workDay.endTimeMin < 10 ? "0" : ""));
        time.append(workDay.endTimeMin);

        return time.toString();
    }


    /**
     * Given a string formatted as ICA_schema outputs an int array of size 4 with [starthour, startmin, endhour, endmin]
     * Example, "15:00 - 23:45" outputs [15,0,23,45]
     * @param str A string formatted as xx:xx - yy:yy
     * @return Returns an int array of size 4 with [starthour, startmin, endhour, endmin]
     */
    public int [] ParseTimesIntoIntArray(String str) {

        // example: if str = "15:00 - 23:45"
        String start = str.substring(0,5); // "15:00"
        String end = str.substring(8);     // "23:45"

        int starthour = Integer.valueOf(start.substring(0,2));  // 15
        int startmin  = Integer.valueOf(start.substring(3,5));  // 00
        int endhour   = Integer.valueOf(end.substring(0,2));    // 23
        int endmin    = Integer.valueOf(end.substring(3,5));    // 45

        //      15       0        23      45
        int [] p = {starthour,startmin,endhour,endmin};
        return p;
    }
}
