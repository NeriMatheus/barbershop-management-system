package br.edu.ufvjm.barbershop.controller;

import br.edu.ufvjm.barbershop.model.Appointment;
import br.edu.ufvjm.barbershop.service.BarbershopSystem;

public class AppointmentController {

    private final BarbershopSystem system;

    public AppointmentController(BarbershopSystem barbershopSystem) {
        if (barbershopSystem == null) {
            throw new IllegalArgumentException("BarbershopSystem is required.");
        }
        this.system = barbershopSystem;
    }

    // Secondary Queue
    public void addToSecondaryQueue(Appointment appointment) {
        system.addToSecondaryQueue(appointment);
    }

    public Appointment callNextFromSecondaryQueue() {
        return system.pollSecondaryQueue();
    }

    public boolean hasPendingSecondaryAppointments() {
        return system.hasPendingSecondaryAppointments();
    }

    // Appointment status
    public void confirmAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null.");
        }
        appointment.confirm();
    }

    public void cancelAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null.");
        }
        appointment.cancel();
    }

    public void concludeAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null.");
        }
        appointment.complete();
    }
}