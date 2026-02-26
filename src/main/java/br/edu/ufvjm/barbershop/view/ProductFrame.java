package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.ProductController;
import br.edu.ufvjm.barbershop.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductFrame extends JFrame {

    private final ProductController controller;
    private final List<Product> products = new ArrayList<>();

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtType;
    private JTextField txtPrice;
    private JTextField txtQuantity;

    private JTable table;
    private DefaultTableModel model;

    public ProductFrame(ProductController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Product Management");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // FORMULÁRIO
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Product Data"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(20);
        txtName = new JTextField(20);
        txtType = new JTextField(20);
        txtPrice = new JTextField(20);
        txtQuantity = new JTextField(20);

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        form.add(txtId, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        form.add(txtName, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        form.add(txtType, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        form.add(txtPrice, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Initial Quantity:"), gbc);
        gbc.gridx = 1;
        form.add(txtQuantity, gbc);

        // BOTÕES
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        JButton btnClear = new JButton("Clear");
        JButton btnSave = new JButton("Save");
        JButton btnRefresh = new JButton("Refresh");

        buttons.add(btnClear);
        buttons.add(btnSave);
        buttons.add(btnRefresh);

        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.add(form, BorderLayout.CENTER);
        left.add(buttons, BorderLayout.SOUTH);

        // TABELA
        model = new DefaultTableModel(
                new Object[]{"ID", "Name", "Type", "Price", "Quantity"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(22);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Registered Products"));

        root.add(left, BorderLayout.WEST);
        root.add(scroll, BorderLayout.CENTER);

        setContentPane(root);

        // AÇÕES
        btnClear.addActionListener(e -> clear());
        btnSave.addActionListener(e -> save());
        btnRefresh.addActionListener(e -> loadTable());

        loadTable();
    }

    private void clear() {
        txtId.setText("");
        txtName.setText("");
        txtType.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        txtId.requestFocus();
    }

    private void save() {
        try {
            Long id = Long.parseLong(txtId.getText().trim());
            String name = txtName.getText().trim();
            String type = txtType.getText().trim();
            BigDecimal price = new BigDecimal(txtPrice.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());

            Product product = new Product(id, name, type, price, quantity);
            products.add(product);

            JOptionPane.showMessageDialog(this, "Product saved successfully.");
            loadTable();
            clear();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid values: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTable() {
        model.setRowCount(0);
        for (Product p : products) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getType(),
                    p.getPrice(),
                    p.getQuantity()
            });
        }
    }
}