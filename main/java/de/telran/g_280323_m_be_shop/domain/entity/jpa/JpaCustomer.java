package de.telran.g_280323_m_be_shop.domain.entity.jpa;

import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Customer;
import de.telran.g_280323_m_be_shop.service.jpa.JpaCustomerService;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaCustomer.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int id;

    @Column(name = "name")
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be lank")
    @Pattern(regexp = "[A-Z][a-z]{2,}", message = "Name must consist of at least 2 characters" +
            "and may not include any characters aside from Latin letters")
    private String name;

    @Column(name = "age")
    @Min(value = 12, message = "The minimum age eligible for registration is 12")
    @Positive
    private int age;

    @Column(name = "email")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @OneToOne(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private JpaCart cart;

    public JpaCustomer() {
        LOGGER.info("Empty JPA customer constructor invoked");
    }

    public JpaCustomer(int id, String name) {
        this.id = id;
        this.name = name;
        LOGGER.info("2-field JPA customer constructor invoked");
    }

    public JpaCustomer(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        LOGGER.info("Full JPA customer constructor invoked");
    }

    public int getId() {
        LOGGER.info("Returning customer ID");
        return id;
    }

    public void setId(int id) {
        LOGGER.info("Setting customer ID to " + id);
        this.id = id;
    }

    public String getName() {
        LOGGER.info("Returning customer name");
        return name;
    }

    public void setName(String name) {
        LOGGER.info("Setting customer name to " + name);
        this.name = name;
    }

    public JpaCart getCart() {
        LOGGER.info("Returning a customer's cart");
        return cart;
    }

    @Override
    public int getAge() {
        LOGGER.info("Returning a customer's age");
        return age;
    }

    public void setAge(int age) {
        LOGGER.info("Setting customer age to " + age);
        this.age = age;
    }

    public String getEmail() {
        LOGGER.info("Returning a customer's email address");
        return email;
    }

    public void setEmail(String email) {
        LOGGER.info("Setting customer email to " + email);
        this.email = email;
    }

    public void setCart(JpaCart cart) {
        LOGGER.info("Setting a cart for a customer");
        this.cart = cart;
    }
}
