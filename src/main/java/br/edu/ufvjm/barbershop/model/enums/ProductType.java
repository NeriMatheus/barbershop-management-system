package br.edu.ufvjm.barbershop.model.enums;

public enum ProductType {

    SHAMPOO("Shampoo"),
    CONDITIONER("Conditioner"),
    POMADE("Pomade"),
    GEL("Hair Gel"),
    BEARD_OIL("Beard Oil"),
    OTHER("Other");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}