package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.FinanceController;
import br.edu.ufvjm.barbershop.model.Expense;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExpenseFrame extends JFrame {

    private final FinanceController controller;

    private JTextField txtId;
    private JTextField txtDescription;
    private JTextField txtValue;
    private JTextField txtDate;

    private JTable table;
    private DefaultTableModel tableModel;

    public ExpenseFrame(FinanceController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Barbershop Expenses");
        setSize(950, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Expense"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        txtId = new JTextField(20);
        txtDescription = new JTextField(20);
        txtValue = new JTextField(20);
        txtDate = new JTextField(20);

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtId, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDescription, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Value (USD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtValue, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDate, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        JButton btnAdd = new JButton("Add expense");
        JButton btnRefresh = new JButton("Refresh list");
        JButton btnClear = new JButton("Clear fields");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClear);

        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.add(formPanel, BorderLayout.CENTER);
        left.add(buttonPanel, BorderLayout.SOUTH);

        // Table
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Description", "Value", "Date"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Registered expenses"));

        root.add(left, BorderLayout.WEST);
        root.add(scroll, BorderLayout.CENTER);

        setContentPane(root);

        // Actions
        btnAdd.addActionListener(e -> addExpense());
        btnRefresh.addActionListener(e -> loadTable());
        btnClear.addActionListener(e -> clearFields());

        loadTable();
    }

    private void clearFields() {
        txtId.setText("");
        txtDescription.setText("");
        txtValue.setText("");
        txtDate.setText("");
        txtId.requestFocus();
    }

    private void addExpense() {
        try {
            Long id = Long.parseLong(txtId.getText().trim());
            String description = txtDescription.getText().trim();
            BigDecimal value = new BigDecimal(txtValue.getText().trim());
            LocalDate date = LocalDate.parse(txtDate.getText().trim());

            Expense expense = new Expense(id, description, value, date);
            controller.addExpense(expense);

            JOptionPane.showMessageDialog(this, "Expense successfully added.");
            loadTable();
            clearFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid ID or value.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        List<Expense> expenses = controller.getExpenses();

        for (Expense e : expenses) {
            tableModel.addRow(new Object[]{
                    e.getId(),
                    e.getDescription(),
                    e.getValue(),
                    e.getDate()
            });
        }
    }
}