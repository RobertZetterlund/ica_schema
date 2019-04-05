import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

public class Reader2019 {

    // ----------------------------- START ADJUSTABLES ----------------------------- //
    private static final String schema_2019 = "/Users/robertzetterlund/Projects_prog/ica_schema/src/main/resources/Schema-Testschema.xlsx";

    // -----------------------------  END ADJUSTABLES  ----------------------------- //

    int day = 0;
    int month = 0;
    String value;

    static String id = "notfound";
    private int rowI = 0;
    private int colI = 0;
    private EventCreator eC = new EventCreator();
    private CalendarWriter cW = new CalendarWriter();

    public static void main(String[] args) throws IOException, InvalidFormatException {
        Reader2019 eR = new Reader2019();
        eR.app();
    }

    public void app() throws IOException, InvalidFormatException {

        int[] eventTimes;

        Workbook workbook = WorkbookFactory.create(new File(schema_2019));

        // Retrieving the sheet in the Workbook
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            rowI++;
            if(rowI == 6) {
                for (Cell cell : row) {
                    colI++;
                    if(colI%2 == 0 ) {
                        value = cell.getStringCellValue();
                        if(!cell.getStringCellValue().equals("")) {
                            WorkDay wD = new WorkDay();
                            int[] date = parsedate(value);
                            wD.day = date[0];
                            wD.month = date[1];
                        }
                    } else {
                       // System.out.println(cell.getStringCellValue());
                    }


//                    System.out.println("value: " + cell.getStringCellValue() + ", row = " + rowI + ", col = " + colI);
                }
            }
            colI = 0;
        }

      //  cW.WriteToFile();
    }

    public int [] parsedate(String value) {
        int[] date = new int [2];
        String [] s = value.split("/");
        date[0] = Integer.valueOf(s[0]);
        date[1] = Integer.valueOf(s[1]);
        return date;
    }


    /**
     * Extracts the id from the file so that the naming of the file is automatically fixed.
     * @param idString the string containing the id of the employee
     * @return the id of the employee
     */
    private String extractId(String idString) {
        String temp = idString.substring(12);
        int i=0;

        while(Character.isDigit(temp.charAt(i))) {
            i++;
        }
        return idString.substring(12,12+i);
    }

    /**
     *
     * @param times array formatted as [starthour, startmin, endhour, endmin].
     * @param day the day the event should be created on, from 1-31.
     * @param month the month the event should be created on.
     * @param year the year the event should be created on (currently unused).
     * @throws SocketException
     */
    private void createEvent(int[] times, int day, int month, int year) throws SocketException {
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
