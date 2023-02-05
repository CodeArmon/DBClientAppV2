package model;

public class Customers {

    private int cid;
    private String customer_Name;
    private String address;
    private String postal_Code;
    private String phone;
    private String division;
    private  String country;
    public Customers(int cid, String customer_Name, String address, String postal_Code, String phone, String division, String country) {
        this.cid = cid;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    @Override
    public String toString() {
        return String.valueOf(cid);
    }

    public int getCid() {
        return cid;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostal_Code() {
        return postal_Code;
    }

    public String getPhone() {
        return phone;
    }

    public String getDivision() {
        return division;
    }

    public String getCountry() {
        return country;
    }

    public int getCustomer_IDfromName(Customers customers){return cid;}


}

