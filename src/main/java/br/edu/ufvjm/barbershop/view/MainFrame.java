package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.ServiceController;
import br.edu.ufvjm.barbershop.controller.ServiceOrderController;
import br.edu.ufvjm.barbershop.service.BarbershopSystem;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final BarbershopSystem barbershopSystem;
    private final ServiceController serviceController;
    private final ServiceOrderController serviceOrderController;

    public MainFrame(BarbershopSystem barbershopSystem,
                     ServiceController serviceController,
                     ServiceOrderController serviceOrderController) {

        this.barbershopSystem = barbershopSystem;
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

        JButton btnServices = new JButton("Services");
        JButton btnAppointments = new JButton("Appointments");
        JButton btnServiceOrders = new JButton("Service Orders");
        JButton btnStations = new JButton("Stations");
        JButton btnExit = new JButton("Exit");

        buttonPanel.add(btnServices);
        buttonPanel.add(btnAppointments);
        buttonPanel.add(btnServiceOrders);
        buttonPanel.add(btnStations);
        buttonPanel.add(btnExit);

        root.add(buttonPanel, BorderLayout.CENTER);
        setContentPane(root);

        // Actions
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