package br.edu.ufvjm.barbershop.app;

import br.edu.ufvjm.barbershop.controller.AppointmentController;
import br.edu.ufvjm.barbershop.controller.CustomerController;
import br.edu.ufvjm.barbershop.controller.EmployeeController;
import br.edu.ufvjm.barbershop.controller.FinanceController;
import br.edu.ufvjm.barbershop.controller.ProductController;
import br.edu.ufvjm.barbershop.controller.SalesReportController;
import br.edu.ufvjm.barbershop.controller.ServiceController;
import br.edu.ufvjm.barbershop.controller.ServiceOrderController;

import br.edu.ufvjm.barbershop.service.BarbershopSystem;

import br.edu.ufvjm.barbershop.view.LoginFrame;

import javax.swing.SwingUtilities;

public class AppMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            BarbershopSystem barbershopSystem = new BarbershopSystem();
            AppointmentController appointmentController = new AppointmentController(barbershopSystem);
            CustomerController customerController = new CustomerController();
            EmployeeController employeeController = new EmployeeController();
            FinanceController financeController = new FinanceController();
            ProductController productController = new ProductController();
            ServiceController serviceController = new ServiceController();
            ServiceOrderController serviceOrderController = new ServiceOrderController();
            SalesReportController salesReportController = new SalesReportController(serviceOrderController);

            LoginFrame loginFrame = new LoginFrame(
                    appointmentController,
                    barbershopSystem,
                    customerController,
                    employeeController,
                    financeController,
                    productController,
                    salesReportController,
                    serviceController,
                    serviceOrderController
            );
            loginFrame.setVisible(true);
        });
    }
}