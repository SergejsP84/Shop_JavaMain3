package de.telran.g_280323_m_be_shop.controllers;

import de.telran.g_280323_m_be_shop.domain.entity.common.CommonCart;
import de.telran.g_280323_m_be_shop.domain.entity.common.CommonCustomer;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Cart;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Customer;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import de.telran.g_280323_m_be_shop.domain.entity.jpa.JpaCustomer;
import de.telran.g_280323_m_be_shop.service.common.CommonCustomerService;
import de.telran.g_280323_m_be_shop.service.interfaces.CustomerService;
import de.telran.g_280323_m_be_shop.service.interfaces.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/findCustomerById/{index}")
    public Customer findCustomerById(@PathVariable @Min(1) int index) {
        return customerService.getById(index);
    }

    @PostMapping("/deleteCustomerById/{index}")
    public void deleteByID(@PathVariable @Min(1) int index) {
        System.out.println("Deleting customer " + customerService.getById(index).getName());
        customerService.deleteById(index);
    }

    @PostMapping("/addCustomer")
    public Customer addCustomer(@RequestBody @Valid JpaCustomer customer) {
        System.out.println("Adding a new customer");
        customerService.add(customer);
        return customer;
    }

    @GetMapping("/countCustomers")
    public int countCustomers() {
        return customerService.getCount();
    }

    @GetMapping("/cartPriceById/{id}")
    public double cartPriceById(@PathVariable @Min(1) int id) {
        return customerService.getTotalPriceById(id);
    }

    @GetMapping("/averageCartPriceById/{id}")
    public double averageCartPriceById(@PathVariable @Min(1) Integer id) {
        return customerService.getAveragePriceById(id);
    }

    @DeleteMapping("/deleteByName/{name}")
    public void deleteCustomerByName(@PathVariable @NotBlank String name) {
        System.out.println("Deleting customer " + name);
        customerService.deleteByName(name);
    }

    @PostMapping("/addProductToCartById/{customerId}/{productId}")
    public void addProductToCartById(@PathVariable @Min(1) Integer customerId, @PathVariable @Min(1) Integer productId) {
        customerService.addToCartById(customerId, productId);
    }

    @DeleteMapping("deleteFromCart/{customerId}/{productId}")
    public void deleteFromCart(@PathVariable @Min(1) int customerId, @PathVariable @Min(1) int productId) {
        customerService.deleteFromCartById(customerId, productId);
    }

    @DeleteMapping("/clear/{customerId}")
    public void clearCart(@PathVariable @Min(1) int customerId) {
        customerService.clearCartById(customerId);
    }

}

