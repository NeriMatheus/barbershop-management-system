package br.edu.ufvjm.barbershop.controller;

import br.edu.ufvjm.barbershop.model.SalesReport;
import br.edu.ufvjm.barbershop.model.ServiceOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalesReportController {

    private final ServiceOrderController serviceOrderController;

    public SalesReportController(ServiceOrderController serviceOrderController) {
        if (serviceOrderController == null) {
            throw new IllegalArgumentException("ServiceOrderController is required.");
        }
        this.serviceOrderController = serviceOrderController;
    }

    public SalesReport generate(LocalDateTime startPeriod,
                                LocalDateTime endPeriod) {

        if (startPeriod == null || endPeriod == null) {
            throw new IllegalArgumentException("Start and end period are required.");
        }

        List<ServiceOrder> filteredOrders = new ArrayList<>();

        for (ServiceOrder order : serviceOrderController.findAll()) {

            if (order.getExecutedAt() == null) continue;

            if (!order.getExecutedAt().isBefore(startPeriod)
                    && !order.getExecutedAt().isAfter(endPeriod)) {

                filteredOrders.add(order);
            }
        }

        return new SalesReport(filteredOrders, startPeriod, endPeriod);
    }

    public SalesReport generateDaily(LocalDateTime dayStart,
                                     LocalDateTime dayEnd) {
        return generate(dayStart, dayEnd);
    }

    public SalesReport generateMonthly(LocalDateTime monthStart,
                                       LocalDateTime monthEnd) {
        return generate(monthStart, monthEnd);
    }
}