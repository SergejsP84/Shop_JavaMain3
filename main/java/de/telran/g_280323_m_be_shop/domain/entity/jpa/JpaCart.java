package de.telran.g_280323_m_be_shop.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Cart;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class JpaCart implements Cart {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaCart.class);


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "customer_id")
    private JpaCustomer customer;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<JpaProduct> products;

    public JpaCart() {
        LOGGER.info("Empty JPA Cart constructor invoked");
    }

    public JpaCart(JpaCustomer customer) {
        LOGGER.info("JPA Cart constructor invoked");
        this.customer = customer;
    }

    public void setId(int id) {
        LOGGER.info("Setting cart ID to " + id);
        this.id = id;
    }

    public JpaCustomer getCustomer() {
        LOGGER.info("Returning customer " + customer.getName() + " for this cart");
        return customer;
    }

    public void setCustomer(JpaCustomer customer) {
        LOGGER.info("Setting a customer for the cart");
        this.customer = customer;
    }

    @Override
    public List<Product> getProducts() {
        LOGGER.info("Returning a list of products stored in the cart");
        return new ArrayList<>(products);
    }

    // METHODS HERE

    @Override
    public void addProduct(Product product) {
        LOGGER.info("Adding a product to the cart");
        products.add(new JpaProduct(product.getId(), product.getName(), product.getPrice()));
    }

    @Override
    public double getTotalPrice() {
        LOGGER.info("Returning the total price of the cart");
        return products.stream().mapToDouble(JpaProduct::getPrice).sum();
    }

    @Override
    public double getAveragePrice() {
        LOGGER.info("Returning the average price of a product in the cart");
        return products.stream().mapToDouble(JpaProduct::getPrice).average().orElse(0);
    }

    @Override
    public void deleteProduct(int id) {
        LOGGER.info("Removing a product from the cart");
        products.removeIf(x -> x.getId() == id);
    }

    @Override
    public void clear() {
        LOGGER.info("Clearing the cart");
        products.clear();
    }

    public void setProducts(List<JpaProduct> products) {
        LOGGER.info("Setting a list of products as cart content");
        this.products = products;
    }
}

