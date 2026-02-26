package br.edu.ufvjm.barbershop.controller;

import br.edu.ufvjm.barbershop.model.Appointment;
import br.edu.ufvjm.barbershop.model.ServiceOrder;
import br.edu.ufvjm.barbershop.model.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ServiceOrderController {

    private final List<ServiceOrder> orders = new ArrayList<>();
    private long nextId = 1L;

    public ServiceOrder createServiceOrder(Appointment appointment,
                                           PaymentMethod paymentMethod) {

        if (appointment == null) {
            throw new IllegalArgumentException("Appointment is required.");
        }

        if (appointment.getService() == null) {
            throw new IllegalArgumentException("Appointment must have a service.");
        }

        BigDecimal totalAmount =
                BigDecimal.valueOf(appointment.getService().getValue());

        ServiceOrder order =
                new ServiceOrder(appointment, totalAmount, paymentMethod);

        setId(order);

        orders.add(order);
        return order;
    }

    public List<ServiceOrder> findAll() {
        return new ArrayList<>(orders);
    }

    public ServiceOrder findById(Long id) {
        if (id == null) return null;

        for (ServiceOrder order : orders) {
            if (id.equals(order.getId())) {
                return order;
            }
        }
        return null;
    }

    public void pay(Long id) {
        ServiceOrder order = findById(id);
        if (order == null) {
            throw new IllegalArgumentException("Service order not found.");
        }
        order.pay();
    }

    public void cancel(Long id) {
        ServiceOrder order = findById(id);
        if (order == null) {
            throw new IllegalArgumentException("Service order not found.");
        }
        order.cancel();
    }

    private void setId(ServiceOrder order) {
        try {
            var field = ServiceOrder.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(order, nextId++);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ServiceOrder ID", e);
        }
    }
}