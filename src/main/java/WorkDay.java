/**
 * A class representing a workday with date and hours.
 */
public class WorkDay {
    int day;
    int month;
    int year;

    int startTimeHour;
    int startTimeMin;
    int endTimeHour;
    int endTimeMin;

    public WorkDay () {
        year = 2019;
    }

    @Override
    public String toString() {
        return "WorkDay{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", startTimeHour=" + startTimeHour +
                ", startTimeMin=" + startTimeMin +
                ", endTimeHour=" + endTimeHour +
                ", endTimeMin=" + endTimeMin +
                '}';
    }
}
