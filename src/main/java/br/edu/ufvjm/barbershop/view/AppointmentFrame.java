package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.AppointmentController;
import br.edu.ufvjm.barbershop.model.Appointment;
import br.edu.ufvjm.barbershop.model.Customer;
import br.edu.ufvjm.barbershop.model.Employee;
import br.edu.ufvjm.barbershop.model.Service;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AppointmentFrame extends JFrame {

    private final AppointmentController controller;

    // Form
    private JComboBox<Customer> cbCustomer;
    private JComboBox<Employee> cbEmployee;
    private JComboBox<Service> cbService;
    private JTextField txtDateTime;
    private JTextField txtAmount;
    private JTextArea txtDescription;

    // Buttons
    private JButton btnSchedule;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JButton btnConclude;

    private Appointment currentAppointment;

    public AppointmentFrame(AppointmentController controller) {
        this.controller = controller;

        setTitle("Appointment Management");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(createFormPanel(), BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(panel);
    }

    // Form
    private JPanel createFormPanel() {
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));

        cbCustomer = new JComboBox<>();
        cbEmployee = new JComboBox<>();
        cbService = new JComboBox<>();
        txtDateTime = new JTextField("2026-01-01T14:00");
        txtAmount = new JTextField();
        txtDescription = new JTextArea(3, 20);

        form.add(new JLabel("Customer:"));
        form.add(cbCustomer);

        form.add(new JLabel("Employee:"));
        form.add(cbEmployee);

        form.add(new JLabel("Service:"));
        form.add(cbService);

        form.add(new JLabel("Date & Time:"));
        form.add(txtDateTime);

        form.add(new JLabel("Amount:"));
        form.add(txtAmount);

        form.add(new JLabel("Description:"));
        form.add(new JScrollPane(txtDescription));

        return form;
    }

    // Buttons
    private JPanel createButtonPanel() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnSchedule = new JButton("Schedule");
        btnConfirm = new JButton("Confirm");
        btnCancel = new JButton("Cancel");
        btnConclude = new JButton("Conclude");

        btnSchedule.addActionListener(e -> scheduleAppointment());
        btnConfirm.addActionListener(e -> confirmAppointment());
        btnCancel.addActionListener(e -> cancelAppointment());
        btnConclude.addActionListener(e -> concludeAppointment());

        buttons.add(btnSchedule);
        buttons.add(btnConfirm);
        buttons.add(btnCancel);
        buttons.add(btnConclude);

        return buttons;
    }

    // Actions
    private void scheduleAppointment() {
        try {
            Appointment appointment = new Appointment.Builder()
                    .customer((Customer) cbCustomer.getSelectedItem())
                    .employee((Employee) cbEmployee.getSelectedItem())
                    .service((Service) cbService.getSelectedItem())
                    .dateTime(LocalDateTime.parse(txtDateTime.getText()))
                    .amount(new BigDecimal(txtAmount.getText()))
                    .description(txtDescription.getText())
                    .build();

            controller.addToSecondaryQueue(appointment);
            currentAppointment = appointment;

            JOptionPane.showMessageDialog(this, "Appointment scheduled successfully.");

        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void confirmAppointment() {
        try {
            controller.confirmAppointment(currentAppointment);
            JOptionPane.showMessageDialog(this, "Appointment confirmed.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void cancelAppointment() {
        try {
            controller.cancelAppointment(currentAppointment);
            JOptionPane.showMessageDialog(this, "Appointment canceled.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void concludeAppointment() {
        try {
            controller.concludeAppointment(currentAppointment);
            JOptionPane.showMessageDialog(this, "Appointment concluded.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    // Utils
    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}