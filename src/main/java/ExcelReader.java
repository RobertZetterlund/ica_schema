import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

public class ExcelReader {
    public static final String schema_w = "/Users/robertzetterlund/ica_schema/src/main/resources/6781_sommar.xls";
    public static final String schema_s = "/Users/robertzetterlund/ica_schema/src/main/resources/6781_vinter.xls";

    public EventCreator eC = new EventCreator();
    public CalenderWriter cW = new CalenderWriter();

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ExcelReader eR = new ExcelReader();
        eR.app();
    }

    public void app() throws IOException, InvalidFormatException {
        // Creating a Workbook from an Excel file (.xls)
        Workbook workbook = WorkbookFactory.create(new File(schema_w));

        // Retrieving the number of sheets in the Workbook
        Sheet sheet = workbook.getSheetAt(0);

        // for-each loop to iterate over the rows and columns


        int day = 10; // Dagen då sommarschemat börjar
        int week = 24; // Veckan då sommarschemat börjar
        String strweek = "24";
        int month = 6; // Månaden då sommarschemat börjar
        int year = 2019; // Året sommarschemat börjar

        int daysOfJuny = 30;
        int daysOfJuly = 31;
        boolean correctpos = false;

        int rowI=0;
        int colI=0;
        int [] eventtime;

        ArrayList<int []> eventTimes = new ArrayList<>();

        for (Row row : sheet) {
            rowI++;
            for (Cell cell : row) {
                colI++;

                if(!correctpos && cell.getStringCellValue().equals(strweek)){
                    correctpos = true;
                }
                if(correctpos && rowI>2 && colI >1 && colI < 9) {
                    String str = cell.getStringCellValue();

                    if(str.contains(":")) {
                        eventtime = getTimes(str);
                        createEvent(eventtime, day, month,year);
                    }

                    System.out.println(cell.getStringCellValue() + " day: " + day + ". Month: " + month);

                    day++;

                }
                if(day == daysOfJuly+1 && month == 6) {
                    month = 7;
                    day = 1;
                }
                else if(day == daysOfJuny+1 && month == 7) {
                    month = 8;
                    day = 1;
                }

            }
            System.out.println("--- new Week ---");
            colI = 0;
        }

        cW.WriteToFile();


        /*

        int A = 0;
        int N = 0;

        String[] dayWorking = {"Mån", "Tis", "Ons", "Tors", "Fre", "Lör", "Sön"};

        StringBuilder sb = new StringBuilder();

        for (Row row : sheet) {
            N = 0;
            for (Cell cell : row) {
                N++;
                System.out.println("this is the value N : "+N);
                System.out.println("this is the value A : "+A);
                System.out.println("value of week" + week);
                System.out.println("in cell:" + cell.getAddress() + ": we have the value: " + cell);
                //String cellValue = dataFormatter.formatCellValue(cell);
                //System.out.print(cellValue + "\t");

                if (A > 1 && N == 1) {
                    sb.append("Vecka " + cell.getStringCellValue() + " jobbar du: ");
                }


                if (!cell.getStringCellValue().equals("") && !cell.getStringCellValue().equals("Julafton") && A > 1 && N > 1) {
                    System.out.println("value of N: "+ N);
                    System.out.println("value of A: "+ A);
                    System.out.println(cell.getStringCellValue());
                    System.out.println(dayWorking[N-3]);
                    sb.append(dayWorking[N - 2] + "dag kl: " + cell.getStringCellValue() + ", ");
                }
            }
            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length() - 2);
                sb.deleteCharAt(sb.length() - 1);
            }
            System.out.println(sb.toString());
            sb = new StringBuilder();
            A++;
            week++;
            if (week > 52) {
                week = 1;
            }
        }

        workbook.close();
    */
    }


    // TODO: maybe not create a useless wrapper...
    // TODO: next up is connecting creation of events to the calenderwriter.
    private void createEvent(int[] times, int day, int month, int year) throws FileNotFoundException, SocketException {
        cW.eventCreator(eC.getTimes(day,month, times[0],times[1],times[2],times[3]));
    }

    private int [] getTimes(String str) {
        String start;
        String end;

        start = str.substring(0,5);
        end = str.substring(6);

        int starthour;
        int startmin;
        int endhour;
        int endmin;

        starthour=Integer.valueOf(start.substring(0,2));
        startmin=Integer.valueOf(start.substring(3,5));


        endhour=Integer.valueOf(end.substring(0,2));
        endmin=Integer.valueOf(end.substring(3,5));

        /*
        System.out.print(starthour + ":");
        System.out.println(startmin);

        System.out.print(endhour + ":");
        System.out.println(endmin);

        */


        int [] p = {starthour,startmin,endhour,endmin};

        return p;
    }
}
