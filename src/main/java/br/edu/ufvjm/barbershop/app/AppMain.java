package br.edu.ufvjm.barbershop.app;

import br.edu.ufvjm.barbershop.controller.ServiceController;
import br.edu.ufvjm.barbershop.controller.ServiceOrderController;
import br.edu.ufvjm.barbershop.service.BarbershopSystem;
import br.edu.ufvjm.barbershop.view.LoginFrame;

import javax.swing.SwingUtilities;

public class AppMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            BarbershopSystem system = new BarbershopSystem();
            ServiceController serviceController = new ServiceController();
            ServiceOrderController serviceOrderController = new ServiceOrderController();

            LoginFrame loginFrame = new LoginFrame(
                    system,
                    serviceController,
                    serviceOrderController
            );
            loginFrame.setVisible(true);
        });
    }
}