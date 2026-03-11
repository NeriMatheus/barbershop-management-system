package br.edu.ufvjm.barbershop.model;

import br.edu.ufvjm.barbershop.model.enums.PaymentMethod;
import br.edu.ufvjm.barbershop.model.enums.ServiceOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ServiceOrder {

    private Long id;
    private Appointment appointment;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private ServiceOrderStatus status;
    private LocalDateTime executedAt;

    protected ServiceOrder(){}

    public ServiceOrder(Appointment appointment, BigDecimal totalAmount, PaymentMethod paymentMethod) {

        if (appointment == null) {
            throw new IllegalArgumentException("Appointment is required to create a service order.");
        }

        this.appointment = appointment;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = ServiceOrderStatus.OPEN;
        this.executedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public ServiceOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    // Business rules
    public void pay() {
        if (status != ServiceOrderStatus.OPEN) {
            throw new IllegalStateException("Only open service orders can be paid.");
        }
        this.status = ServiceOrderStatus.PAID;
    }

    public void cancel() {
        if (status == ServiceOrderStatus.PAID) {
            throw new IllegalStateException("Paid service orders cannot be canceled.");
        }
        this.status = ServiceOrderStatus.CANCELED;
    }


    // Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceOrder)) return false;
        ServiceOrder that = (ServiceOrder) o;
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
                + ", appointmentId="
                + (getAppointment() != null ? appointment.getId() : "N/A")
                + ", totalAmount="
                + getTotalAmount()
                + ", paymentMethod="
                + getPaymentMethod()
                + ", status="
                + getStatus()
                + ", executedAt="
                + getExecutedAt()
                + '}';
    }
}