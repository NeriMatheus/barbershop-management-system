package br.edu.ufvjm.barbershop.model.enums;

public enum ServiceOrderStatus {

    OPEN("Open"),
    PAID("Paid"),
    CANCELED("Canceled");

    private final String description;

    ServiceOrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}