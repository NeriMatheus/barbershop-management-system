package br.edu.ufvjm.barbershop.model;

import br.edu.ufvjm.barbershop.model.enums.EmployeePosition;

import java.math.BigDecimal;
import java.util.Objects;

public class Administrator extends Employee {

    public Administrator(Long id, String name, Phone phone, Address address, EmployeePosition employeePosition, String login, String passwordHash, BigDecimal salary) {
        super(id, name, phone, address, employeePosition, login, passwordHash, salary);
    }

    public boolean changePassword(String currentPassword, String newPassword) {

        if (!Objects.equals(getPassword(), currentPassword)) {
            return false;
        }

        super.changePassword(newPassword);
        return true;
    }

    // Output
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "id="
                + getId()
                + ", name='"
                + getName()
                + '\''
                + ", position="
                + getEmployeePosition()
                + '}';
    }
}
