package br.edu.ufvjm.barbershop.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Expense {

    private Long id;
    private String description;
    private BigDecimal value;
    private LocalDate date;

    public Expense() {
    }

    public Expense(Long id, String description, BigDecimal value, LocalDate date) {
        this.id = id;
        setDescription(description);
        setValue(value);
        setDate(date);
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Expense description cannot be null or blank");
        }
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Expense value must be zero or positive");
        }
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Expense date cannot be null");
        }
        this.date = date;
    }

    // Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;
        Expense expense = (Expense) o;
        return Objects.equals(id, expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Output
    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{id="
                + getId()
                + ", description='"
                + getDescription()
                + '\''
                + ", value="
                + getValue()
                + ", date="
                + getDate()
                + '}';
    }
}
