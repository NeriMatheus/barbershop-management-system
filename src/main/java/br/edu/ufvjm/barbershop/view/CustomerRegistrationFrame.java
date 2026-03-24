package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.CustomerController;
import br.edu.ufvjm.barbershop.model.Address;
import br.edu.ufvjm.barbershop.model.Customer;
import br.edu.ufvjm.barbershop.model.Phone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerRegistrationFrame extends JFrame {

    private final CustomerController controller;

    // Customer
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtCpf;

    // Phone
    private JTextField txtDdi;
    private JTextField txtDdd;
    private JTextField txtPhoneNumber;

    // Address
    private JTextField txtStreet;
    private JTextField txtNumber;
    private JTextField txtNeighborhood;
    private JTextField txtCity;
    private JTextField txtZipCode;

    // Table
    private JTable table;
    private DefaultTableModel tableModel;

    public CustomerRegistrationFrame(CustomerController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Customer Registration");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Customer Data"));

        txtId = new JTextField();
        txtName = new JTextField();
        txtEmail = new JTextField();
        txtCpf = new JTextField();

        txtDdi = new JTextField("55");
        txtDdd = new JTextField();
        txtPhoneNumber = new JTextField();

        txtStreet = new JTextField();
        txtNumber = new JTextField();
        txtNeighborhood = new JTextField();
        txtCity = new JTextField();
        txtZipCode = new JTextField();

        form.add(new JLabel("ID:"));
        form.add(txtId);

        form.add(new JLabel("Name:"));
        form.add(txtName);

        form.add(new JLabel("Email:"));
        form.add(txtEmail);

        form.add(new JLabel("CPF:"));
        form.add(txtCpf);

        form.add(new JLabel("DDI:"));
        form.add(txtDdi);

        form.add(new JLabel("DDD:"));
        form.add(txtDdd);

        form.add(new JLabel("Phone number:"));
        form.add(txtPhoneNumber);

        form.add(new JLabel("Street:"));
        form.add(txtStreet);

        form.add(new JLabel("Number:"));
        form.add(txtNumber);

        form.add(new JLabel("Neighborhood:"));
        form.add(txtNeighborhood);

        form.add(new JLabel("City:"));
        form.add(txtCity);

        form.add(new JLabel("ZIP Code:"));
        form.add(txtZipCode);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnSave = new JButton("Save");
        JButton btnSearch = new JButton("Search");
        JButton btnRemove = new JButton("Remove");
        JButton btnClear = new JButton("Clear");

        btnSave.addActionListener(e -> saveCustomer());
        btnSearch.addActionListener(e -> searchCustomer());
        btnRemove.addActionListener(e -> removeCustomer());
        btnClear.addActionListener(e -> clearFields());

        buttons.add(btnSave);
        buttons.add(btnSearch);
        buttons.add(btnRemove);
        buttons.add(btnClear);

        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.add(form, BorderLayout.CENTER);
        left.add(buttons, BorderLayout.SOUTH);

        // Table
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Email", "CPF"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Customers"));

        root.add(left, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);

        setContentPane(root);
        loadTable();
    }

    // Actions
    private void saveCustomer() {
        try {
            Long id = Long.parseLong(txtId.getText().trim());

            Phone phone = new Phone(
                    txtDdi.getText().trim(),
                    txtDdd.getText().trim(),
                    txtPhoneNumber.getText().trim()
            );

            Address address = new Address(
                    txtStreet.getText().trim(),
                    txtNumber.getText().trim(),
                    txtNeighborhood.getText().trim(),
                    txtCity.getText().trim(),
                    txtZipCode.getText().trim()
            );

            Customer customer = new Customer(
                    id,
                    txtName.getText().trim(),
                    phone,
                    address,
                    txtEmail.getText().trim(),
                    txtCpf.getText().trim()
            );

            controller.add(customer);

            JOptionPane.showMessageDialog(this, "Customer saved successfully.");
            loadTable();
            clearFields();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "ID must be a number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchCustomer() {
        try {
            Long id = Long.parseLong(txtId.getText().trim());
            Customer c = controller.findById(id);

            if (c == null) {
                JOptionPane.showMessageDialog(this, "Customer not found.");
                return;
            }

            txtName.setText(c.getName());
            txtEmail.setText(c.getEmail());
            txtCpf.setText(c.getCpf());

            txtDdi.setText(c.getPhone().ddi());
            txtDdd.setText(c.getPhone().ddd());
            txtPhoneNumber.setText(c.getPhone().number());

            txtStreet.setText(c.getAddress().street());
            txtNumber.setText(c.getAddress().number());
            txtNeighborhood.setText(c.getAddress().neighborhood());
            txtCity.setText(c.getAddress().city());
            txtZipCode.setText(c.getAddress().zipCode());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    private void removeCustomer() {
        try {
            Long id = Long.parseLong(txtId.getText().trim());
            controller.remove(id);

            JOptionPane.showMessageDialog(this, "Customer removed.");
            loadTable();
            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadTable() {
        tableModel.setRowCount(0);

        for (Customer c : controller.findAll()) {
            tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getName(),
                    c.getEmail(),
                    c.getCpf(),
            });
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtCpf.setText("");

        txtDdi.setText("+55");
        txtDdd.setText("");
        txtPhoneNumber.setText("");

        txtStreet.setText("");
        txtNumber.setText("");
        txtNeighborhood.setText("");
        txtCity.setText("");
        txtZipCode.setText("");

        txtId.requestFocus();
    }
}