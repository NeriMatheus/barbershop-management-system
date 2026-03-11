package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.ServiceController;
import br.edu.ufvjm.barbershop.model.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ServiceFrame extends JFrame {

    private final ServiceController serviceController;

    private JTextField txtType;
    private JTextField txtValue;
    private JTable table;
    private DefaultTableModel tableModel;

    public ServiceFrame(ServiceController serviceController) {
        this.serviceController = serviceController;
        initComponents();
    }

    private void initComponents() {
        setTitle("Barbershop Services");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Service Data"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtType = new JTextField(20);
        txtValue = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Service Type:"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Value ($):"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtValue, gbc);

        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        JButton btnClear = new JButton("New / Clear");
        JButton btnSave = new JButton("Save");
        JButton btnRefresh = new JButton("Refresh List");

        buttonsPanel.add(btnClear);
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnRefresh);

        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Table
        tableModel = new DefaultTableModel(
                new Object[]{"Type", "Value"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(22);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Registered Services")
        );

        root.add(leftPanel, BorderLayout.WEST);
        root.add(scrollPane, BorderLayout.CENTER);

        setContentPane(root);

        // Actions
        btnClear.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> saveService());
        btnRefresh.addActionListener(e -> loadTable());

        loadTable();
    }

    private void clearForm() {
        txtType.setText("");
        txtValue.setText("");
        txtType.requestFocus();
    }

    private void saveService() {
        try {
            String type = txtType.getText().trim();
            Double value = Double.parseDouble(txtValue.getText().trim());

            serviceController.registerService(type, value);

            JOptionPane.showMessageDialog(this, "Service registered successfully.");
            loadTable();
            clearForm();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid value.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadTable() {
        tableModel.setRowCount(0);

        List<Service> services = serviceController.findAll();
        for (Service service : services) {
            tableModel.addRow(new Object[]{
                    service.getType(),
                    service.getValue()
            });
        }
    }
}