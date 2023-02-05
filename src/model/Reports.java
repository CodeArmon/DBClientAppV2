package model;

public class Reports {
    private String monthName;
    private int count;
    private String type;


    public Reports(String monthName, int count) {
        this.monthName = monthName;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Reports(String type) {
    }



    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
