package br.edu.ufvjm.barbershop.model;

import br.edu.ufvjm.barbershop.model.enums.ServiceOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalesReport {

    private final List<ServiceOrder> orders;
    private final LocalDateTime startPeriod;
    private final LocalDateTime endPeriod;

    public SalesReport(List<ServiceOrder> orders,
                       LocalDateTime startPeriod,
                       LocalDateTime endPeriod) {

        if (startPeriod == null || endPeriod == null)
            throw new IllegalArgumentException("Report period must be informed.");

        if (endPeriod.isBefore(startPeriod))
            throw new IllegalArgumentException("End period cannot be before start period.");

        this.orders = orders == null ? new ArrayList<>() : new ArrayList<>(orders);
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }

    // Getters
    public List<ServiceOrder> getOrders() {
        return new ArrayList<>(orders);
    }

    public LocalDateTime getStartPeriod() {
        return startPeriod;
    }

    public LocalDateTime getEndPeriod() {
        return endPeriod;
    }

    // Business calculations
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ServiceOrder order : orders) {
            if (order.getStatus() == ServiceOrderStatus.PAID) {
                total = total.add(order.getTotalAmount());
            }
        }
        return total;
    }

    public int getPaidOrderCount() {
        int count = 0;

        for (ServiceOrder order : orders) {
            if (order.getStatus() == ServiceOrderStatus.PAID) {
                count++;
            }
        }
        return count;
    }

    public BigDecimal getAverageTicket() {
        int count = getPaidOrderCount();

        if (count == 0)
            return BigDecimal.ZERO;

        return calculateTotal()
                .divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);
    }

    // Output
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{startPeriod="
                + getStartPeriod()
                + ", endPeriod="
                + getEndPeriod()
                + ", paidOrders="
                + getPaidOrderCount()
                + ", total="
                + calculateTotal()
                + ", averageTicket="
                + getAverageTicket()
                + '}';
    }
}
