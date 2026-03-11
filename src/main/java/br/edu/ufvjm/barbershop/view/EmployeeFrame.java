package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.EmployeeController;
import br.edu.ufvjm.barbershop.model.Employee;
import br.edu.ufvjm.barbershop.model.enums.EmployeePosition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class EmployeeFrame extends JFrame {

    private final EmployeeController controller;

    private JTextField txtId;
    private JTextField txtName;
    private JComboBox<EmployeePosition> cbPosition;
    private JTextField txtLogin;
    private JPasswordField txtPassword;
    private JTextField txtSalary;

    private JTable table;
    private DefaultTableModel model;

    public EmployeeFrame(EmployeeController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Employee Management");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Employee Data"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(20);
        txtName = new JTextField(20);
        cbPosition = new JComboBox<>(EmployeePosition.values());
        txtLogin = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtSalary = new JTextField(20);

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; form.add(txtId, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; form.add(txtName, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Position:"), gbc);
        gbc.gridx = 1; form.add(cbPosition, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Login:"), gbc);
        gbc.gridx = 1; form.add(txtLogin, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; form.add(txtPassword, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Salary:"), gbc);
        gbc.gridx = 1; form.add(txtSalary, gbc);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        JButton btnNew = new JButton("New / Clear");
        JButton btnSave = new JButton("Save");
        JButton btnSearch = new JButton("Search by ID");
        JButton btnRemove = new JButton("Remove");
        JButton btnRefresh = new JButton("Refresh List");

        buttons.add(btnNew);
        buttons.add(btnSave);
        buttons.add(btnSearch);
        buttons.add(btnRemove);
        buttons.add(btnRefresh);

        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.add(form, BorderLayout.CENTER);
        left.add(buttons, BorderLayout.SOUTH);

        // Table
        model = new DefaultTableModel(
                new Object[]{"ID", "Name", "Position", "Login", "Salary"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Registered Employees"));

        root.add(left, BorderLayout.WEST);
        root.add(scroll, BorderLayout.CENTER);

        setContentPane(root);

        // Actions
        btnNew.addActionListener(e -> clearFields());
        btnSave.addActionListener(e -> saveEmployee());
        btnSearch.addActionListener(e -> searchEmployee());
        btnRemove.addActionListener(e -> removeEmployee());
        btnRefresh.addActionListener(e -> loadTable());

        loadTable();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtLogin.setText("");
        txtPassword.setText("");
        txtSalary.setText("");
        cbPosition.setSelectedIndex(0);
        txtId.requestFocus();
    }

    private void saveEmployee() {
        try {
            Long id = Long.parseLong(txtId.getText().trim());
            String name = txtName.getText().trim();
            EmployeePosition position = (EmployeePosition) cbPosition.getSelectedItem();
            String login = txtLogin.getText().trim();
            String password = new String(txtPassword.getPassword());
            BigDecimal salary = new BigDecimal(txtSalary.getText().trim());

            controller.addEmployee(id, name, position, login, password, salary);

            JOptionPane.showMessageDialog(this, "Employee saved successfully.");
            loadTable();
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchEmployee() {
        try {
            Long id = Long.parseLong(txtId.getText().trim());
            Employee e = controller.findById(id);

            if (e == null) {
                JOptionPane.showMessageDialog(this, "Employee not found.");
                return;
            }

            txtName.setText(e.getName());
            cbPosition.setSelectedItem(e.getEmployeePosition());
            txtLogin.setText(e.getLogin());
            txtSalary.setText(e.getSalary().toString());
            txtPassword.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    private void removeEmployee() {
        try {
            Long id = Long.parseLong(txtId.getText().trim());

            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Remove employee " + id + "?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (option != JOptionPane.YES_OPTION)
                return;

            controller.remove(id);
            JOptionPane.showMessageDialog(this, "Employee removed.");
            loadTable();
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    private void loadTable() {
        model.setRowCount(0);
        List<Employee> employees = controller.findAll();

        for (Employee e : employees) {
            model.addRow(new Object[]{
                    e.getId(),
                    e.getName(),
                    e.getEmployeePosition(),
                    e.getLogin(),
                    e.getSalary()
            });
        }
    }
}