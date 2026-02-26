package br.edu.ufvjm.barbershop.view;

import br.edu.ufvjm.barbershop.controller.ServiceController;
import br.edu.ufvjm.barbershop.controller.ServiceOrderController;
import br.edu.ufvjm.barbershop.service.BarbershopSystem;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final BarbershopSystem system;
    private final ServiceController serviceController;
    private final ServiceOrderController serviceOrderController;

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JLabel lblMessage;

    public LoginFrame(BarbershopSystem system,
                      ServiceController serviceController,
                      ServiceOrderController serviceOrderController) {

        this.system = system;
        this.serviceController = serviceController;
        this.serviceOrderController = serviceOrderController;

        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Barbershop System");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));
        form.add(new JLabel("Login:"));
        txtLogin = new JTextField();
        form.add(txtLogin);

        form.add(new JLabel("Password:"));
        txtSenha = new JPasswordField();
        form.add(txtSenha);

        JPanel buttons = new JPanel(new FlowLayout());
        JButton btnEnter = new JButton("Enter");
        JButton btnExit = new JButton("Exit");

        buttons.add(btnEnter);
        buttons.add(btnExit);

        lblMessage = new JLabel("Authentication not implemented yet.", SwingConstants.CENTER);

        root.add(lblMessage, BorderLayout.NORTH);
        root.add(form, BorderLayout.CENTER);
        root.add(buttons, BorderLayout.SOUTH);

        setContentPane(root);

        btnEnter.addActionListener(e -> enter());
        btnExit.addActionListener(e -> System.exit(0));

        getRootPane().setDefaultButton(btnEnter);
    }

    private void enter() {
        MainFrame mainFrame = new MainFrame(
                system,
                serviceController,
                serviceOrderController
        );
        mainFrame.setVisible(true);
        dispose();
    }
}