package model;

public class Contacts {
    private int contact_ID;
    private String contact_Name;
    private String email;

    public Contacts(int contact_ID, String contact_Name, String email) {
        this.contact_ID = contact_ID;
        this.contact_Name = contact_Name;
        this.email = email;
    }

    public int getContact_ID() {
        return contact_ID;
    }

    public String getContact_Name() {
        return contact_Name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return contact_Name;
    }
}
