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





}