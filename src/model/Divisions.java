package model;

public class Divisions {
    private int division_ID;
    private String division;

    public Divisions(int division_ID, String division) {
        this.division_ID = division_ID;
        this.division = division;

    }

    public int getDivision_ID() {
        return division_ID;
    }

    public String getDivision() {
        return division;
    }

    @Override
    public String toString() {
        return  division ;
    }
}
