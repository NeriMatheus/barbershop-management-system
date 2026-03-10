package br.edu.ufvjm.barbershop.model.enums;

public enum EmployeePosition {

    BARBER("Barber"),
    RECEPTIONIST("Receptionist"),
    MANAGER("Manager");

    private final String description;

    EmployeePosition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}