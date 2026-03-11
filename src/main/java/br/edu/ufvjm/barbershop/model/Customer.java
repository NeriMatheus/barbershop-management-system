package br.edu.ufvjm.barbershop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer extends Person {

    private String email;
    private final String cpf;
    private final List<ServiceOrder> orders = new ArrayList<>();

    protected Customer() {
        this.cpf = null;
    }

    public Customer(Long id, String name, Phone phone, Address address, String email, String cpf) {
        super(id, name, phone, address);

        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email cannot be empty.");

        if (cpf == null || cpf.isBlank())
            throw new IllegalArgumentException("CPF cannot be empty.");

        this.email = email;
        this.cpf = cpf;
    }

    // Business rules
    public void addOrder(ServiceOrder order) {
        if (order == null)
            throw new IllegalArgumentException("Service order cannot be null.");
        orders.add(order);
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email cannot be empty.");
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public List<ServiceOrder> getOrders() {
        return List.copyOf(orders);
    }

    // Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    // Output
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{id="
                + getId()
                + ", name='"
                + getName()
                + '\''
                + ", cpf='"
                + getCpf()
                + '\''
                + ", email='"
                + getEmail()
                + '\''
                + '}';
    }
}
