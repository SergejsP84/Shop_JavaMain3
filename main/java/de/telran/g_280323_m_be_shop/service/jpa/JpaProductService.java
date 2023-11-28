package de.telran.g_280323_m_be_shop.service.jpa;

import de.telran.g_280323_m_be_shop.domain.entity.common.CommonProduct;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import de.telran.g_280323_m_be_shop.domain.entity.jpa.JpaProduct;
import de.telran.g_280323_m_be_shop.domain.entity.jpa.Task;
import de.telran.g_280323_m_be_shop.exception_handling.exception.InvalidProductDataException;
import de.telran.g_280323_m_be_shop.exception_handling.exception.ProductDeletionException;
import de.telran.g_280323_m_be_shop.exception_handling.exception.ProductNotFoundException;
import de.telran.g_280323_m_be_shop.repository.jpa.JpaProductRepository;
import de.telran.g_280323_m_be_shop.schedule.ScheduleExecutor;
import de.telran.g_280323_m_be_shop.service.interfaces.ProductService;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JpaProductService implements ProductService {

    //Logger by log4j
    private final Logger LOGGER = LogManager.getLogger(JpaProductService.class);

    private JpaProductRepository repository;
        private TaskService taskService;

    public JpaProductService(JpaProductRepository repository, TaskService taskService) {
        this.repository = repository;
        this.taskService = taskService;
    }

//    public JpaProductService(JpaProductRepository repository) {
//        this.repository = repository;
//    }

    @Override
    public List<Product> getAll() {
        System.out.println("JPA Product getAll method engaged");
        Task task = taskService.createTask("Requested a list of all products");
        ScheduleExecutor.executeScheduledTask(task);
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public Product getById(int id) {
        System.out.println("JPA Product getById method engaged");
        LOGGER.log(Level.INFO, "Запрошен продукт с идентификатором {}.", id);
        LOGGER.log(Level.WARN, "Запрошен продукт с идентификатором {}.", id);
        LOGGER.log(Level.ERROR, "Запрошен продукт с идентификатором {}.", id);
        // LOGGER.info("Запрошен продукт с идентификатором {}.", id)
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException("No product with an ID " + id));
    }

    @Override
    public void add(Product product) {
        System.out.println("JPA Product add method engaged");
        if (product == null ||
                StringUtils.isBlank(product.getName()) ||
                product.getPrice() <= 0) {
            throw new InvalidProductDataException("Invalid product data");
        }
        repository.save(new JpaProduct(product.getId(), product.getName(), product.getPrice()));
//        ((CommonProduct) product).setId(777); // чисто в учебных целях, для теста АОП, так оно не нужно
    }

    @Override
    public void deleteById(int id) {
        System.out.println("JPA Product deleteById method engaged");
        try {
        Task task = taskService.createTask("Attempting to remove the product by ID " + id);
        LOGGER.info("Invoked the deleteById method");
        ScheduleExecutor.executeScheduledTaskOnce(task);
        repository.deleteById(id);
        } catch (Exception e) {
            throw new ProductDeletionException("Error deleting product with ID: " + id);
        }
    }

    @Override
    public int getCount() {
        System.out.println("JPA Product getCount method engaged");
        return (int) repository.count();
    }

    @Override
    public double getTotalPrice() {
        System.out.println("JPA Product getTotalPrice method engaged");
        return repository.getTotalPrice();
    }

    @Override
    public double getAveragePrice() {
        System.out.println("JPA Product getAveragePrice method engaged");
        return repository.getAveragePrice();
    }

    @Override
    public void deleteByName(String name) {
        System.out.println("JPA Product deleteByName method engaged");
        try {
        repository.deleteByName(name);
        } catch (Exception e) {
            throw new ProductDeletionException("Error deleting product named " + name);
        }
    }
}
