package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.AppointmentController;
import br.edu.ufvjm.barbershop.controller.CustomerController;
import br.edu.ufvjm.barbershop.controller.EmployeeController;
import br.edu.ufvjm.barbershop.controller.FinanceController;
import br.edu.ufvjm.barbershop.controller.ProductController;
import br.edu.ufvjm.barbershop.controller.SalesReportController;
import br.edu.ufvjm.barbershop.controller.ServiceController;
import br.edu.ufvjm.barbershop.controller.ServiceOrderController;

import br.edu.ufvjm.barbershop.service.BarbershopSystem;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final AppointmentController appointmentController;
    private final BarbershopSystem barbershopSystem;
    private final CustomerController customerController;
    private final EmployeeController employeeController;
    private final FinanceController financeController;
    private final ProductController productController;
    private final SalesReportController salesReportController;
    private final ServiceController serviceController;
    private final ServiceOrderController serviceOrderController;

    public MainFrame(AppointmentController appointmentController, BarbershopSystem barbershopSystem, CustomerController customerController, EmployeeController employeeController, FinanceController financeController, ProductController productController, SalesReportController salesReportController, ServiceController serviceController, ServiceOrderController serviceOrderController) {
        this.appointmentController = appointmentController;
        this.barbershopSystem = barbershopSystem;
        this.employeeController = employeeController;
        this.financeController = financeController;
        this.productController = productController;
        this.salesReportController = salesReportController;
        this.customerController = customerController;
        this.serviceController = serviceController;
        this.serviceOrderController = serviceOrderController;

        initComponents();
    }

    private void initComponents() {
        setTitle("Barbershop System - Main Menu");
        setSize(800, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Main Menu", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));
        root.add(lblTitle, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 4, 10, 10));

        JButton btnAppointments = new JButton("Appointments");
        JButton btnCustomers = new JButton("Customers");
        JButton btnEmployees = new JButton("Employees");
        JButton btnFinances = new JButton("Finances");
        JButton btnProducts = new JButton("Products");
        JButton btnSaleReports = new JButton("Sales Reports");
        JButton btnServices = new JButton("Services");
        JButton btnServiceOrders = new JButton("Service Orders");
        JButton btnStations = new JButton("Stations");
        JButton btnExit = new JButton("Exit");

        buttonPanel.add(btnAppointments);
        buttonPanel.add(btnCustomers);
        buttonPanel.add(btnEmployees);
        buttonPanel.add(btnFinances);
        buttonPanel.add(btnProducts);
        buttonPanel.add(btnSaleReports);
        buttonPanel.add(btnServices);
        buttonPanel.add(btnServiceOrders);
        buttonPanel.add(btnStations);
        buttonPanel.add(btnExit);

        root.add(buttonPanel, BorderLayout.CENTER);
        setContentPane(root);

        // Actions
        btnAppointments.addActionListener(e ->
                new AppointmentFrame(appointmentController).setVisible(true)
        );

        btnCustomers.addActionListener(e ->
                new CustomerRegistrationFrame(customerController).setVisible(true)
        );

        btnCustomers.addActionListener(e ->
                new EmployeeFrame(employeeController).setVisible(true)
        );

        btnCustomers.addActionListener(e ->
                new ExpenseFrame(financeController).setVisible(true)
        );

        btnCustomers.addActionListener(e ->
                new ProductFrame(productController).setVisible(true)
        );

        btnCustomers.addActionListener(e ->
                new SalesReportFrame(salesReportController).setVisible(true)
        );

        btnServices.addActionListener(e ->
                new ServiceFrame(serviceController).setVisible(true)
        );

        btnServiceOrders.addActionListener(e ->
                new ServiceOrderFrame(serviceOrderController).setVisible(true)
        );

        btnStations.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        barbershopSystem.toString(),
                        "Stations Status",
                        JOptionPane.INFORMATION_MESSAGE)
        );

        btnAppointments.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Appointment module not connected yet.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE)
        );

        btnExit.addActionListener(e -> System.exit(0));
    }
}