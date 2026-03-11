package br.edu.ufvjm.barbershop.model;

import java.util.Objects;

public class Station {

    private final int number;
    private String description;
    private Employee responsible;
    private boolean occupied;

    public Station(int number, String description) {

        if (number <= 0)
            throw new IllegalArgumentException("Station number must be positive");

        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Station description cannot be empty");

        this.number = number;
        this.description = description;
        this.occupied = false;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public Employee getResponsible() {
        return responsible;
    }

    public boolean isOccupied() {
        return occupied;
    }

    // Business rules
    public void occupy(Employee employee) {
        if (occupied)
            throw new IllegalStateException("Station is already occupied");

        if (employee == null)
            throw new IllegalArgumentException("Responsible employee is required");

        this.responsible = employee;
        this.occupied = true;
    }

    public void release() {
        this.responsible = null;
        this.occupied = false;
    }

    // Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return number == station.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    // Output
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "number="
                + getNumber()
                + ", description='"
                + getDescription()
                + '\''
                + ", responsible="
                + (getResponsible() != null ? getResponsible().getName() : "none")
                + ", occupied="
                + occupied
                + '}';
    }
}
