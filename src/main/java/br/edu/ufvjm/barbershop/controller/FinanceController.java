package br.edu.ufvjm.barbershop.controller;

import br.edu.ufvjm.barbershop.model.Expense;
import br.edu.ufvjm.barbershop.model.SalesReport;
import br.edu.ufvjm.barbershop.model.ServiceOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FinanceController {

    private final List<Expense> expenses = new ArrayList<>();
    private final List<ServiceOrder> orders = new ArrayList<>();

    public FinanceController() {
    }

    // Expenses
    public void addExpense(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException("Expense cannot be null.");
        }
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return new ArrayList<>(expenses);
    }

    // Orders
    public void addServiceOrder(ServiceOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Service order cannot be null.");
        }
        orders.add(order);
    }

    public List<ServiceOrder> getServiceOrders() {
        return new ArrayList<>(orders);
    }

    // Sales report
    public SalesReport generateDailySalesReport(LocalDate date) {
        if (date == null)
            throw new IllegalArgumentException("Date is required.");

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        return new SalesReport(orders, start, end);
    }

    public SalesReport generateMonthlySalesReport(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return new SalesReport(
                orders,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        );
    }

    //  Balance
    public String generateMonthlyBalance(int year, int month) {

        SalesReport report = generateMonthlySalesReport(year, month);

        double totalExpenses = 0.0;
        for (Expense expense : expenses) {
            if (expense.getDate().getYear() == year
                    && expense.getDate().getMonthValue() == month) {
                totalExpenses += expense.getValue().doubleValue();
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