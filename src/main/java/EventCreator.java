import java.util.GregorianCalendar;

public class EventCreator {
    java.util.Calendar startDate = new GregorianCalendar();
    java.util.Calendar endDate = new GregorianCalendar();


    public java.util.Calendar [] getTimes(int day, int month, int starthour, int startminute, int endhour, int endminute) {

        startDate.set(java.util.Calendar.MONTH, month-1);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, day);
        startDate.set(java.util.Calendar.YEAR, 2019);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, starthour);
        startDate.set(java.util.Calendar.MINUTE, startminute);
        startDate.set(java.util.Calendar.SECOND, 0);

       /* if(endhour < starthour) {
            day++;
            // also check if month, but not right now.
        }*/

        endDate.set(java.util.Calendar.MONTH, month-1);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, day);
        endDate.set(java.util.Calendar.YEAR, 2019);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, endhour);
        endDate.set(java.util.Calendar.MINUTE, endminute);
        endDate.set(java.util.Calendar.SECOND, 0);

        java.util.Calendar arr [] = {startDate, endDate};

        return arr;
    }

    public java.util.Calendar [] getTimes(WorkDay wD) {

        boolean working_night = false;
        startDate.set(java.util.Calendar.MONTH, wD.month-1); // ical is different from normal month-numbering, starts index from 0 hence -1.
        startDate.set(java.util.Calendar.DAY_OF_MONTH, wD.day);
        startDate.set(java.util.Calendar.YEAR, wD.year);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, wD.startTimeHour);
        startDate.set(java.util.Calendar.MINUTE, wD.startTimeMin);
        //startDate.set(java.util.Calendar.SECOND, 0);

        if(wD.endTimeHour < wD.endTimeMin) {
            working_night = true;
        }// TODO: ALSO CHECK MONTHS, but tricky....

        endDate.set(java.util.Calendar.MONTH, wD.month-1);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, wD.day + (working_night ? 1 : 0));
        endDate.set(java.util.Calendar.YEAR, wD.year);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, wD.endTimeHour);
        endDate.set(java.util.Calendar.MINUTE, wD.endTimeMin);
        //endDate.set(java.util.Calendar.SECOND, 0);

        java.util.Calendar arr [] = {startDate, endDate};

        return arr;
    }
}
