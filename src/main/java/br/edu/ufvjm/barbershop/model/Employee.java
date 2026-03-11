package br.edu.ufvjm.barbershop.model;

import br.edu.ufvjm.barbershop.model.enums.EmployeePosition;
import java.math.BigDecimal;
import java.util.Objects;

public class Employee extends Person {

    private EmployeePosition employeePosition;
    private final String login;
    private String password;
    private BigDecimal salary;

    public Employee(Long id, String name, Phone phone, Address address, EmployeePosition employeePosition, String login, String password, BigDecimal salary) {
        super(id, name, phone, address);

        if (employeePosition == null)
            throw new IllegalArgumentException("Position cannot be null.");

        if (login == null || login.isBlank())
            throw new IllegalArgumentException("Login cannot be empty.");

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password hash cannot be empty.");

        if (salary == null || salary.signum() < 0)
            throw new IllegalArgumentException("Salary cannot be negative.");

        this.employeePosition = employeePosition;
        this.login = login;
        this.password = password;
        this.salary = salary;
    }

    // Getters and setters
    public EmployeePosition getEmployeePosition() {
        return employeePosition;
    }

    public void setPosition(EmployeePosition employeePosition) {
        this.employeePosition = Objects.requireNonNull(employeePosition);
    }

    public String getLogin() {
        return login;
    }

    protected String getPassword() {
        return password;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        if (salary == null || salary.signum() < 0)
            throw new IllegalArgumentException("Salary cannot be negative.");

        this.salary = salary;
    }

    // Change password
    public void changePassword(String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be empty.");
        }
        this.password = newPassword;
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
                + ", position="
                + getEmployeePosition()
                + '}';
    }
}