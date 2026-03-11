package br.edu.ufvjm.barbershop.model;

import java.math.BigDecimal;

public class Product {

    private final Long id;
    private String name;
    private String type;
    private BigDecimal price;
    private int quantity;

    public Product(Long id, String name, String type, BigDecimal price, int quantity) {

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be empty.");

        if (type == null || type.isBlank())
            throw new IllegalArgumentException("Type cannot be empty.");

        if (price == null || price.signum() < 0)
            throw new IllegalArgumentException("Price cannot be negative.");

        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative.");

        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null || type.isBlank())
            throw new IllegalArgumentException("Type cannot be empty.");
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.signum() < 0)
            throw new IllegalArgumentException("Price cannot be negative.");
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int delta) {
        if (this.quantity + delta < 0)
            throw new IllegalArgumentException("Quantity cannot be negative.");
        this.quantity += delta;
    }

    // Output
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{d="
                + getId()
                + ", name='"
                + getName()
                + '\''
                + ", type='"
                + getType()
                + '\''
                + ", price="
                + getPrice()
                + ", quantity="
                + getQuantity()
                + '}';
    }
}
