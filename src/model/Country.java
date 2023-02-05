package model;

public class Country {
    private int country_ID;
    private String country;

    public Country(int country_ID, String country) {
        this.country_ID = country_ID;
        this.country = country;
    }

    public int getCountry_ID() {
        return country_ID;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return  country;
    }
}
