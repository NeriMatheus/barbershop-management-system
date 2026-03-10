package br.edu.ufvjm.barbershop.model.enums;

public enum AppointmentStatus {

    PENDING("Pending"),
    PRE_SCHEDULED("Pre Scheduled"),
    CONFIRMED("Confirmed"),
    CANCELED("Canceled"),
    COMPLETED("Completed");

    private final String description;

    AppointmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}