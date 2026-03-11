package br.edu.ufvjm.barbershop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Inventory {

    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    // Business rules
    public void addProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Product cannot be null.");

        if (products.contains(product))
            throw new IllegalStateException("Product already exists in inventory.");

        products.add(product);
    }

    public Product findById(Long id) {
        if (id == null)
            return null;

        for (Product product : products) {
            if (Objects.equals(product.getId(), id))
                return product;
        }
        return null;
    }

    public void sellProduct(Long id, int quantity) {
        Product product = findById(id);

        if (product == null)
            throw new IllegalArgumentException("Product not found.");

        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive.");

        product.addQuantity(-quantity);
    }

    public void receiveProduct(Long id, int quantity) {
        Product product = findById(id);

        if (product == null)
            throw new IllegalArgumentException("Product not found.");

        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive.");

        product.addQuantity(quantity);
    }

    public int getTotalProducts() {
        return products.size();
    }

    // Output
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{totalProducts="
                + getTotalProducts()
                + '}';
    }
}
