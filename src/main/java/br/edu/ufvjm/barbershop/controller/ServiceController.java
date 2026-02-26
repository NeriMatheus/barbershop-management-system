package br.edu.ufvjm.barbershop.controller;

import br.edu.ufvjm.barbershop.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceController {

    private final List<Service> services = new ArrayList<>();

    public void registerService(String type, Double value) {
        if (type == null || type.isBlank())
            throw new IllegalArgumentException("Service type is required.");

        if (value == null || value < 0)
            throw new IllegalArgumentException("Service value cannot be negative.");

        if (findByType(type) != null)
            throw new IllegalArgumentException("Service already exists.");

        services.add(new Service(type, value));
    }

    public Service findByType(String type) {
        for (Service s : services) {
            if (s.getType().equalsIgnoreCase(type)) {
                return s;
            }
        }
        return null;
    }

    public List<Service> findAll() {
        return new ArrayList<>(services);
    }
}