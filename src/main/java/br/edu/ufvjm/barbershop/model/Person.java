package br.edu.ufvjm.barbershop.model;

public abstract class Person {

    protected  Long id;
    protected String name;
    protected Phone phone;
    protected Address address;

    public Person() {}

    public Person(Long id, String name, Phone phone, Address address) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
                + ", phone="
                + getPhone()
                + ", address="
                + getAddress()
                + '}';
    }
}