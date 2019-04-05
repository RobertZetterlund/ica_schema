public class WorkDay {
    int day;
    int month;
    int year;

    int startTime;
    int endTime;


    public WorkDay () {
        year = 2019;
    }

    @Override
    public String toString() {
        return "WorkDay{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
