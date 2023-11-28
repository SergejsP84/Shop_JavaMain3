package de.telran.g_280323_m_be_shop.domain.entity.jpa;

import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name = "product")
public class JpaProduct implements Product {

    private static final Logger LOGGER = LogManager.getLogger(JpaProduct.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;


    // Test - OK
    // Te - FAIL (number of letters greater than 2)
    // TEST - FAIL (all caps denied)
    // TEst - Fail
    // Test - Fail
    @Column(name = "name")
//    @NotNull
//    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]{2,}")
    private String name;
    @Column(name = "price")
    @Min(value = 5)
    @Max(value = 99999)
    private double price;

    public JpaProduct() {
        LOGGER.info("Empty JPA product constructor invoked");
    }

    public JpaProduct(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        LOGGER.info("Full JPA product constructor invoked");
    }

    @Override
    public int getId() {
        LOGGER.info("Returning a product by ID {}.", id);
        return id;
    }

    public void setId(int id) {
        LOGGER.info("Setting product ID to " + id);
        this.id = id;
    }

    @Override
    public String getName() {
        LOGGER.info("Identifying the product by name");
        return name;
    }

    public void setName(String name) {
        LOGGER.info("Setting product name to " + name);
        this.name = name;
    }

    @Override
    public double getPrice() {
        LOGGER.info("Obtaining product price");
        return price;
    }

    public void setPrice(double price) {
        LOGGER.info("Setting product price to {}", price);
        this.price = price;
    }

    @Override
    public String toString() {
        return "JpaProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
