import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Reader2019Test {


    WorkDay wD = new WorkDay();
    Reader2019 eR = new Reader2019();

    @Test
    void parseDayOneDigit() {
        wD.day = eR.parsedate("1/4")[0];
        assertEquals(1, wD.day);
    }

    @Test
    void parseMonthOneDigit() {
        wD.month = eR.parsedate("1/4")[1];
        assertEquals(4, wD.month);
    }

    @Test
    void parseDayTwoDigit() {
        wD.month = eR.parsedate("20/4")[0];
        assertEquals(20, wD.month);
    }

    @Test
    void parseMonthTwoDigit() {
        wD.month = eR.parsedate("20/4")[1];
        assertEquals(4, wD.month);
    }

    @Test
    void parseWorkTimes1() {

        int[] myArr = eR.ParseTimesIntoIntArray("15:00 - 23:45");

        assertEquals(myArr[0], 15);
        assertEquals(myArr[1], 0);

        assertEquals(myArr[2], 23);
        assertEquals(myArr[3], 45);
    }

    @Test
    void parseWorkTimes2() {

        int[] myArr = eR.ParseTimesIntoIntArray("06:00 - 14:45");

        assertEquals(myArr[0], 6);
        assertEquals(myArr[1], 0);

        assertEquals(myArr[2], 14);
        assertEquals(myArr[3], 45);
    }




}