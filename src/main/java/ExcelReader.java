import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.time.Month;

public class ExcelReader {
    public static final String schema_w = "/Users/robertzetterlund/ica_schema/src/main/resources/6781_sommar.xls";
    public static final String schema_s = "/Users/robertzetterlund/ica_schema/src/main/resources/6781_vinter.xls";

    public static void main(String[] args) throws IOException, InvalidFormatException {


        // Creating a Workbook from an Excel file (.xls)
        Workbook workbook = WorkbookFactory.create(new File(schema_w));

        // Retrieving the number of sheets in the Workbook
        Sheet sheet = workbook.getSheetAt(0);

        // for-each loop to iterate over the rows and columns
        int day = 10;
        int week = 24;
        int month = 6;
        int year = 2019;

        int daysOfJuny = 30;
        int daysOfJuly = 31;
        boolean correctpos = false;



        for (Row row : sheet) {
            for (Cell cell : row) {
                if(cell.getStringCellValue().equals("24")){
                    correctpos = true;
                    System.out.println("MY BOY");
                }
                if(correctpos) {









                }

            }
        }












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
}
