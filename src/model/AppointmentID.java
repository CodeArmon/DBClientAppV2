package model;

public class AppointmentID {
    private int appointment_ID;

    public int getAppointment_ID() {
        return appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        this.appointment_ID = appointment_ID;
    }

    public AppointmentID(int appointment_id) {
        this.appointment_ID = appointment_id;
    }

    @Override
    public String toString() {
        return "Appointment ID # " +
                + appointment_ID;
    }
}
