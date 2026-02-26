package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.ServiceOrderController;
import br.edu.ufvjm.barbershop.model.ServiceOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ServiceOrderFrame extends JFrame {

    private final ServiceOrderController controller;

    private JTextField txtFilterCustomerId;
    private JTable table;
    private DefaultTableModel model;
    private JTextArea txtDetails;

    public ServiceOrderFrame(ServiceOrderController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Service Orders / Statements");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // TOPO
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        top.add(new JLabel("Filter by Customer ID:"));
        txtFilterCustomerId = new JTextField(10);

        JButton btnFilter = new JButton("Filter");
        JButton btnAll = new JButton("Show all");

        top.add(txtFilterCustomerId);
        top.add(btnFilter);
        top.add(btnAll);

        // TABELA
        model = new DefaultTableModel(
                new Object[]{"OS ID", "Customer", "Service", "Total Amount", "Executed At"},
                0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(22);
        JScrollPane tableScroll = new JScrollPane(table);

        // DETALHES
        txtDetails = new JTextArea();
        txtDetails.setEditable(false);
        JScrollPane detailsScroll = new JScrollPane(txtDetails);
        detailsScroll.setBorder(
                BorderFactory.createTitledBorder("Details / Invoice")
        );

        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                tableScroll,
                detailsScroll
        );
        split.setResizeWeight(0.6);

        root.add(top, BorderLayout.NORTH);
        root.add(split, BorderLayout.CENTER);

        setContentPane(root);

        // AÇÕES
        btnFilter.addActionListener(e -> loadTableByCustomer());
        btnAll.addActionListener(e -> loadAll());

        table.getSelectionModel()
                .addListSelectionListener(e -> showDetails());

        loadAll();
    }

    private void loadAll() {
        loadTable(null);
    }

    private void loadTableByCustomer() {
        String customerIdText = txtFilterCustomerId.getText().trim();
        if (customerIdText.isEmpty()) {
            loadAll();
        } else {
            loadTable(customerIdText);
        }
    }

    private void loadTable(String customerIdFilter) {
        model.setRowCount(0);

        List<ServiceOrder> orders = controller.findAll();

        for (ServiceOrder os : orders) {

            if (customerIdFilter != null &&
                    os.getAppointment() != null &&
                    os.getAppointment().getCustomer() != null &&
                    !customerIdFilter.equals(
                            String.valueOf(os.getAppointment().getCustomer().getId())
                    )) {
                continue;
            }

            model.addRow(new Object[]{
                    os.getId(),
                    os.getAppointment() != null && os.getAppointment().getCustomer() != null
                            ? os.getAppointment().getCustomer().getName()
                            : "N/A",
                    os.getAppointment() != null && os.getAppointment().getService() != null
                            ? os.getAppointment().getService().getType()
                            : "N/A",
                    os.getTotalAmount(),
                    os.getExecutedAt()
            });
        }

        txtDetails.setText("");
    }

    private void showDetails() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        Long id = (Long) model.getValueAt(row, 0);

        ServiceOrder os = controller.findById(id);
        if (os != null) {
            txtDetails.setText(os.toString());
        }
    }
}