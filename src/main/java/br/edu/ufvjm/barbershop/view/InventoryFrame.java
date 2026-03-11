package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.model.Inventory;
import br.edu.ufvjm.barbershop.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryFrame extends JFrame {

    private final Inventory inventory;

    private JTextField txtProductId;
    private JTextField txtQuantity;

    private JTable table;
    private DefaultTableModel tableModel;

    public InventoryFrame(Inventory inventory) {
        this.inventory = inventory;
        initComponents();
    }

    private void initComponents() {
        setTitle("Inventory Management");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Inventory Movement"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtProductId = new JTextField(20);
        txtQuantity = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Product ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtProductId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtQuantity, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        JButton btnSell = new JButton("Register sale");
        JButton btnReceive = new JButton("Receive from supplier");
        JButton btnRefresh = new JButton("Refresh list");

        buttonPanel.add(btnSell);
        buttonPanel.add(btnReceive);
        buttonPanel.add(btnRefresh);

        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.add(formPanel, BorderLayout.CENTER);
        left.add(buttonPanel, BorderLayout.SOUTH);

        // Table
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Type", "Price", "Quantity"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Products in Inventory"));

        root.add(left, BorderLayout.WEST);
        root.add(scroll, BorderLayout.CENTER);

        setContentPane(root);

        // Actions
        btnSell.addActionListener(e -> sellProduct());
        btnReceive.addActionListener(e -> receiveProduct());
        btnRefresh.addActionListener(e -> loadTable());

        loadTable();
    }

    private void sellProduct() {
        try {
            Long productId = Long.parseLong(txtProductId.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());

            inventory.sellProduct(productId, quantity);

            JOptionPane.showMessageDialog(this, "Sale registered successfully.");
            loadTable();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid product ID or quantity.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void receiveProduct() {
        try {
            Long productId = Long.parseLong(txtProductId.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());

            inventory.receiveProduct(productId, quantity);

            JOptionPane.showMessageDialog(this, "Products received successfully.");
            loadTable();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid product ID or quantity.",
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
        List<Product> products = inventory.getProducts();

        for (Product p : products) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getType(),
                    p.getPrice(),
                    p.getQuantity()
            });
        }
    }
}