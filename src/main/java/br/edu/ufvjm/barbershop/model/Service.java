package br.edu.ufvjm.barbershop.model;

public class Service {

    private String type;
    private Double value;

    public Service(String type, Double value) {
        this.type = type;
        this.value = value;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    // Output
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{type='"
                + getType()
                + '\''
                + ", value="
                + getValue()
                + '}';
    }
}