package de.telran.g_280323_m_be_shop.service.jpa;

import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Cart;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Customer;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import de.telran.g_280323_m_be_shop.domain.entity.jpa.JpaCart;
import de.telran.g_280323_m_be_shop.domain.entity.jpa.JpaCustomer;
import de.telran.g_280323_m_be_shop.exception_handling.exception.CartOperationException;
import de.telran.g_280323_m_be_shop.exception_handling.exception.CustomerNotFoundException;
import de.telran.g_280323_m_be_shop.exception_handling.exception.ProductNotFoundException;
import de.telran.g_280323_m_be_shop.repository.jpa.JpaCartRepository;
import de.telran.g_280323_m_be_shop.repository.jpa.JpaCustomerRepository;
import de.telran.g_280323_m_be_shop.repository.jpa.JpaProductRepository;
import de.telran.g_280323_m_be_shop.service.interfaces.CustomerService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JpaCustomerService implements CustomerService {

    //slf4j here
    private final Logger LOGGER = LoggerFactory.getLogger(JpaCustomerService.class);


    private JpaCustomerRepository repository;
    private JpaCartRepository cartRepository;
    private JpaProductRepository productRepository;

    public JpaCustomerService(JpaCustomerRepository repository, JpaCartRepository cartRepository, JpaProductRepository productRepository) {
        this.repository = repository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(repository.findAll());
    } // (GET) http://localhost:8080/customer - OK

    @Override
    public Customer getById(int id) {
        LOGGER.info("Запрошен покупатель с идентификатором {}.", id);
        LOGGER.warn("Запрошен покупатель с идентификатором {}.", id);
        LOGGER.error("Запрошен покупатель с идентификатором {}.", id);
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
    } // (GET) http://localhost:8080/customer/findCustomerById/1 - OK


    @Override
    public void add(Customer customer) {
        JpaCustomer savedCustomer = repository.save(new JpaCustomer(0, customer.getName(), customer.getAge(), customer.getEmail()));
        cartRepository.save(new JpaCart(savedCustomer));
    }  // (POST) http://localhost:8080/customer/addCustomer - OK
    // USE THE "BODY" SECTION, like
//    {
//        "name": "Nevermore",
//            "cart": {
//        "products": []
//    },
//        "age": 39,
//            "email": "fifteenth@domain.com"
//    }

    @Override
    public void deleteById(int id) {
        cartRepository.delete(getById(id).getCart());
        repository.deleteById(id);
    } // (DELETE) http://localhost:8080/customer/deleteCustomerById/5 - OK

    @Transactional
    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public int getCount() {
        return (int) repository.count();
    } // (GET) http://localhost:8080/customer/countCustomers - OK

    @Override
    public double getTotalPriceById(int id) {
        return getById(id).getCart().getTotalPrice();
    } // (GET) http://localhost:8080/customer/cartPriceById/1 - OK

    @Override
    public double getAveragePriceById(int id) {
        return getById(id).getCart().getAveragePrice();
    } // (GET) http://localhost:8080/customer/averageCartPriceById/1 - OK

    @Transactional
    @Override
    public void addToCartById(int customerId, int productId) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
        Cart cart = customer.getCart();
        try {
            cart.addProduct(product);
        } catch (Exception e) {
            throw new CartOperationException("Failed to add the product to cart");
        }
    } // (POST) http://localhost:8080/customer/addProductToCartById/4/2 - OK


    @Transactional
    @Override
    public void deleteFromCartById(int customerId, int productId) {
        try {
            getById(customerId).getCart().deleteProduct(productId);
        } catch (Exception e) {
            throw new CartOperationException("Failed to delete the product from cart");
        }
    } // (DELETE) http://localhost:8080/customer/deleteFromCart/4/2 - OK

    @Transactional
    @Override
    public void clearCartById(int customerId) {
        try {
            getById(customerId).getCart().clear();
        } catch (Exception e) {
            throw new CartOperationException("Cart clearance failed");
        }
    } // (DELETE) http://localhost:8080/customer/clear/4 - OK
}