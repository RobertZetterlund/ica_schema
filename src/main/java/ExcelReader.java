import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

public class ExcelReader {

    // ----------------------------- START ADJUSTABLES ----------------------------- //
    private static final String schema_s = "/Users/robertzetterlund/ica_schema/src/main/resources/6781_sommar.xls";
    private static final String schema_w = "/Users/robertzetterlund/ica_schema/src/main/resources/6781_vinter.xls";

    public static final String id = "6781";

    int day = 10; // Dagen då sommarschemat börjar
    int month = 6; // Månaden då sommarschemat börjar
    int year = 2019; // Året sommarschemat börjar

    int daysOfJuny = 30;
    int daysOfJuly = 31;
    // -----------------------------  END ADJUSTABLES  ----------------------------- //




    int rowI = 0;
    int colI = 0;
    int[] eventTimes;
    private EventCreator eC = new EventCreator();
    private CalenderWriter cW = new CalenderWriter();

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ExcelReader eR = new ExcelReader();
        eR.app();
    }

    public void app() throws IOException, InvalidFormatException {
        // Creating a Workbook from an Excel file (.xls)
        Workbook workbook = WorkbookFactory.create(new File(schema_s));

        // Retrieving the sheet in the Workbook
        Sheet sheet = workbook.getSheetAt(0);



        // for-each loop to iterate over the rows and columns
        for (Row row : sheet) {
            rowI++;
            if(rowI>2) { // we start at row 2
                for (Cell cell : row) {
                    colI++;
                    if (colI > 1 && colI < 9) { // between column-indices 1 and 9 lies the data, monday through sunday.
                        String str = cell.getStringCellValue();

                        if (str.contains(":")) { // time is formatted as XX:xx-YY:yy.
                            eventTimes = ParseTimesIntoIntArray(str);
                            createEvent(eventTimes, day, month, year);
                        }

                        System.out.println(cell.getStringCellValue() + " day: " + day + ". Month: " + month);

                        day++;
                    }
                    if (day == daysOfJuly + 1 && month == 6) {
                        month = 7;
                        day = 1;
                    } else if (day == daysOfJuny + 1 && month == 7) {
                        month = 8;
                        day = 1;
                    }

                }
                System.out.println("--- new Week ---");
                colI = 0;
            }
        }
        cW.WriteToFile();

    }

    /**
     *
     * @param times array formatted as [starthour, startmin, endhour, endmin].
     * @param day the day the event should be created on, from 1-31.
     * @param month the month the event should be created on.
     * @param year the year the event should be created on (currently unused).
     * @throws FileNotFoundException
     * @throws SocketException
     */
    private void createEvent(int[] times, int day, int month, int year) throws FileNotFoundException, SocketException {
        cW.eventCreator(eC.getTimes(day,month, times[0],times[1],times[2],times[3]));
    }


    /**
     * Given a string formatted as ICA_schema outputs an int array of size 4 with [starthour, startmin, endhour, endmin]
     * Example, "15:00-23:45" outputs [15,0,23,45]
     * @param str A string formatted as xx:xx-yy:yy
     * @return Returns an int array of size 4 with [starthour, startmin, endhour, endmin]
     */
    private int [] ParseTimesIntoIntArray(String str) {

        // example: if str = "15:00-23:45"
        String start = str.substring(0,5); // "15:00"
        String end = str.substring(6);     // "23:45"

        int starthour = Integer.valueOf(start.substring(0,2));  // 15
        int startmin  = Integer.valueOf(start.substring(3,5));  // 00
        int endhour   = Integer.valueOf(end.substring(0,2));    // 23
        int endmin    = Integer.valueOf(end.substring(3,5));    // 45

                //      15       0        23      45
        int [] p = {starthour,startmin,endhour,endmin};
        return p;
    }
}
