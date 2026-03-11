package br.edu.ufvjm.barbershop.service;

import br.edu.ufvjm.barbershop.model.Appointment;
import br.edu.ufvjm.barbershop.model.Employee;
import br.edu.ufvjm.barbershop.model.Station;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BarbershopSystem {

    private final List<Station> stations;
    private final Queue<Appointment> secondaryQueue = new ArrayDeque<>();

    public BarbershopSystem() {
        this.stations = new ArrayList<>(List.of(
                new Station(1, "Wash and dryer"),
                new Station(2, "Regular activities 1"),
                new Station(3, "Regular activities 2")
        ));
    }

    // Stations
    public List<Station> getStations() {
        return List.copyOf(stations);
    }

    public boolean hasFreeStation() {
        for (Station station : stations) {
            if (!station.isOccupied()) {
                return true;
            }
        }
        return false;
    }

    public Station allocateFreeStation(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee is required to allocate a station.");
        }

        for (Station station : stations) {
            if (!station.isOccupied()) {
                station.occupy(employee); // business rule delegated to Station
                return station;
            }
        }
        return null;
    }

    public void releaseStation(Station station) {
        if (station == null) {
            throw new IllegalArgumentException("Station cannot be null.");
        }

        if (station.isOccupied()) {
            station.release(); // business rule delegated to Station
        }
    }

    // Secondary Queue
    public void addToSecondaryQueue(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null.");
        }
        secondaryQueue.offer(appointment);
    }

    public Appointment pollSecondaryQueue() {
        return secondaryQueue.poll();
    }

    public int getSecondaryQueueSize() {
        return secondaryQueue.size();
    }

    public boolean hasPendingSecondaryAppointments() {
        return !secondaryQueue.isEmpty();
    }

    // Output
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{stations="
                + stations.size()
                + ", secondaryQueueSize="
                + secondaryQueue.size()
                + '}';
    }
}
