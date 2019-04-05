public class WorkDay {
    int day;
    int month;
    int year;

    int startTimeHour;
    int startTimeMin;
    int endTimeHour;
    int endTimeMin;

    public int getStartTimeMin() {
        return startTimeMin;
    }

    public void setStartTimeMin(int startTimeMin) {
        this.startTimeMin = startTimeMin;
    }

    public int getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(int startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public int getEndTimeMin() {
        return endTimeMin;
    }

    public void setEndTimeMin(int endTimeMin) {
        this.endTimeMin = endTimeMin;
    }

    public int getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(int endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
