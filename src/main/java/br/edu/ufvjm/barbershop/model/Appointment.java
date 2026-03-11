package br.edu.ufvjm.barbershop.model;

import br.edu.ufvjm.barbershop.model.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Appointment {

    private Long id;
    private Customer customer;
    private Employee employee;
    private Service service;
    private LocalDateTime dateTime;
    private BigDecimal amount;
    private String description;
    private AppointmentStatus status;

    protected Appointment() {}

    // Private constructor (Builder)
    private Appointment(Builder builder) {
        this.customer = builder.customer;
        this.employee = builder.employee;
        this.service = builder.service;
        this.dateTime = builder.dateTime;
        this.amount = builder.amount;
        this.description = builder.description;
        this.status = builder.status != null
                ? builder.status
                : AppointmentStatus.PRE_SCHEDULED;
    }

    // Builder
    public static class Builder {

        private Customer customer;
        private Employee employee;
        private Service service;
        private LocalDateTime dateTime;
        private BigDecimal amount;
        private String description;
        private AppointmentStatus status;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder employee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Builder service(Service service) {
            this.service = service;
            return this;
        }

        public Builder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder status(AppointmentStatus status) {
            this.status = status;
            return this;
        }

        public Appointment build() {
            if (customer == null)
                throw new IllegalStateException("Customer is required.");
            if (employee == null)
                throw new IllegalStateException("Employee is required.");
            if (service == null)
                throw new IllegalStateException("Service is required.");
            if (dateTime == null)
                throw new IllegalStateException("Date and time are required.");

            return new Appointment(this);
        }
    }

    // Business rules
    public void confirm() {
        validateChange();
        this.status = AppointmentStatus.CONFIRMED;
    }

    public void complete() {
        if (status != AppointmentStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed appointments can be completed.");
        }
        this.status = AppointmentStatus.COMPLETED;
    }

    public void cancel() {
        validateChange();
        this.status = AppointmentStatus.CANCELED;
    }

    private void validateChange() {
        if (status == AppointmentStatus.CANCELED
                || status == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Appointment can no longer be modified.");
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    // Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Output
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{id="
                + getId()
                + ", customer='"
                + (getCustomer() != null ? getCustomer().getName() : "N/A")
                + '\''
                + ", employee='"
                + (getEmployee() != null ? getEmployee().getName() : "N/A")
                + '\''
                + ", service='"
                + (getService() != null ? getService().getType() : "N/A")
                + '\''
                + ", dateTime="
                + getDateTime()
                + ", amount="
                + getAmount()
                + ", status="
                + getStatus()
                + '}';
    }
}
