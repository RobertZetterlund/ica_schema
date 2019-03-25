import java.util.GregorianCalendar;

public class EventCreator {
    public java.util.Calendar [] startTime(int day, int month, int starthour, int startminute, int endhour, int endminute) {
        java.util.Calendar startDate = new GregorianCalendar();
        java.util.Calendar endDate = new GregorianCalendar();

        startDate.set(java.util.Calendar.MONTH, month);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, day);
        startDate.set(java.util.Calendar.YEAR, 2019);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, starthour);
        startDate.set(java.util.Calendar.MINUTE, startminute);
        startDate.set(java.util.Calendar.SECOND, 0);


        if(endhour < starthour) {
            day++;
            // also check if month, but not right now.
        }

        endDate.set(java.util.Calendar.MONTH, month);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, day);
        endDate.set(java.util.Calendar.YEAR, 2019);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, endhour);
        endDate.set(java.util.Calendar.MINUTE, endminute);
        endDate.set(java.util.Calendar.SECOND, 0);

        java.util.Calendar arr [] = {startDate, endDate};

        return arr;
    }
}