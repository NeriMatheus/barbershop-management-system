package br.edu.ufvjm.barbershop.model;

/**
 * @param ddi    +55
 * @param ddd    (38)
 * @param number 9 9999-9999
 */

public record Phone(String ddi, String ddd, String number) {

    public Phone {
        if (ddi == null || ddi.isBlank() || ddd == null || ddd.isBlank() || number == null || number.isBlank()) {
            throw new IllegalArgumentException("DDI, DDD and number must be informed.");
        }
    }

    // Full number
    public String getFullNumber() {
        return "+" + ddi() + "(" + ddd() + ") " + number();
    }

    // Output
    @Override
    public String toString() {
        return getFullNumber();
    }
}