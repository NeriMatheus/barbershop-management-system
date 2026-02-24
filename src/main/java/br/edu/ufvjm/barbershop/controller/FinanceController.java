package br.edu.ufvjm.barbershop.controller;

import br.edu.ufvjm.barbershop.model.Expense;
import br.edu.ufvjm.barbershop.model.SalesReport;
import br.edu.ufvjm.barbershop.model.ServiceOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FinanceController {

    public FinanceController() {
    }

    // RELATÓRIOS
    public SalesReport generateDailySalesReport(
            List<ServiceOrder> orders,
            LocalDate date
    ) {
        if (date == null)
            throw new IllegalArgumentException("Date is required.");

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        return new SalesReport(orders, start, end); m
    }

    public SalesReport generateMonthlySalesReport(
            List<ServiceOrder> orders,
            int year,
            int month
    ) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return new SalesReport(
                orders,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        );
    }

    // BALANÇO
    public String generateMonthlyBalance(
            SalesReport report,
            List<Expense> expenses,
            int year,
            int month
    ) {
        if (report == null)
            throw new IllegalArgumentException("Sales report is required.");

        double totalExpenses = 0.0;

        if (expenses != null) {
            for (Expense expense : expenses) {
                if (expense.getDate().getYear() == year
                        && expense.getDate().getMonthValue() == month) {
                    totalExpenses += expense.getValue().doubleValue();
                }
            }
        }

        double revenue = report.calculateTotal().doubleValue();
        double balance = revenue - totalExpenses;

        return "Balance "
                + String.format("%02d/%d", month, year)
                + " revenue=" + revenue
                + " expenses=" + totalExpenses
                + " balance=" + balance;
    }
}