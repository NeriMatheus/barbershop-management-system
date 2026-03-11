package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.SalesReportController;
import br.edu.ufvjm.barbershop.model.SalesReport;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SalesReportFrame extends JFrame {

    private final SalesReportController controller;

    private JTextArea txtOutput;
    private JTextField txtDay;
    private JTextField txtMonth;
    private JTextField txtYear;

    public SalesReportFrame(SalesReportController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Sales Reports");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Filters
        JPanel filters = new JPanel(new GridBagLayout());
        filters.setBorder(BorderFactory.createTitledBorder("Period"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtDay = new JTextField(4);
        txtMonth = new JTextField(4);
        txtYear = new JTextField(6);

        gbc.gridx = 0; gbc.gridy = 0;
        filters.add(new JLabel("Day:"), gbc);
        gbc.gridx = 1;
        filters.add(txtDay, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        filters.add(new JLabel("Month:"), gbc);
        gbc.gridx = 1;
        filters.add(txtMonth, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        filters.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        filters.add(txtYear, gbc);

        // Buttons
        JPanel buttons = new JPanel(new GridLayout(3, 1, 5, 5));
        JButton btnDaily = new JButton("Daily Report");
        JButton btnMonthly = new JButton("Monthly Report");
        JButton btnBalance = new JButton("Monthly Balance");

        buttons.add(btnDaily);
        buttons.add(btnMonthly);
        buttons.add(btnBalance);

        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.add(filters, BorderLayout.CENTER);
        left.add(buttons, BorderLayout.SOUTH);

        // Output
        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtOutput);
        scroll.setBorder(BorderFactory.createTitledBorder("Result"));

        root.add(left, BorderLayout.WEST);
        root.add(scroll, BorderLayout.CENTER);

        setContentPane(root);

        // Actions
        btnDaily.addActionListener(e -> generateDaily());
        btnMonthly.addActionListener(e -> generateMonthly());
        btnBalance.addActionListener(e -> generateMonthlyBalance());
    }

    private Integer parseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private void generateDaily() {
        Integer d = parseInt(txtDay.getText());
        Integer m = parseInt(txtMonth.getText());
        Integer y = parseInt(txtYear.getText());

        if (d == null || m == null || y == null) {
            JOptionPane.showMessageDialog(this, "Day, month and year are required.");
            return;
        }

        LocalDate date = LocalDate.of(y, m, d);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        SalesReport report = controller.generate(start, end);
        txtOutput.setText(report.toString());
    }

    private void generateMonthly() {
        Integer m = parseInt(txtMonth.getText());
        Integer y = parseInt(txtYear.getText());

        if (m == null || y == null) {
            JOptionPane.showMessageDialog(this, "Month and year are required.");
            return;
        }

        LocalDate startDate = LocalDate.of(y, m, 1);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = startDate
                .withDayOfMonth(startDate.lengthOfMonth())
                .atTime(23, 59, 59);

        SalesReport report = controller.generate(start, end);
        txtOutput.setText(report.toString());
    }

    private void generateMonthlyBalance() {
        generateMonthly();
    }
}