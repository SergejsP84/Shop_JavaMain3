package de.telran.g_280323_m_be_shop.controllers;

import de.telran.g_280323_m_be_shop.domain.entity.common.CommonProduct;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import de.telran.g_280323_m_be_shop.exception_handling.Response;
import de.telran.g_280323_m_be_shop.exception_handling.exception.EntityValidationException;
import de.telran.g_280323_m_be_shop.exception_handling.exception.FirstTestException;
import de.telran.g_280323_m_be_shop.exception_handling.exception.SecondTestException;
import de.telran.g_280323_m_be_shop.exception_handling.exception.ThirdTestException;
import de.telran.g_280323_m_be_shop.service.interfaces.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//II. ProductController
//        1) Вывести все продукты
//        2) Получить продукт по ID
//        3) Удалить продукт по ID
//        4) Добавить продукт
//        5) Вывести количество продуктов
//        6) Получить общую цену всех продуктов
//        7) Получить среднюю цену по всем продуктам
//        8) Удалить продукт по названию

@RestController

public class ProductController {

    @Autowired
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/findProductById/{index}")
    public Product findProductById(@PathVariable int index) {
        return productService.getById(index);
    }

    @DeleteMapping("/deleteProductById/{index}")
    public void deleteByID(@PathVariable int index) {
        System.out.println("Deleting product " + productService.getById(index).getName());
        productService.deleteById(index);
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody @Valid CommonProduct addedProduct) {
        if (addedProduct.getName().equals("Test")) {
            throw new FirstTestException("Incorrect product name!");
        }
        if (addedProduct.getName().equals("Test1")) {
            throw new SecondTestException("Another incorrect name!");
        }
        if (addedProduct.getName().equals("Test2")) {
            throw new ThirdTestException("Message from third exception!");
        }
        try {
            productService.add(addedProduct);
        } catch (Exception e) {
            throw new EntityValidationException(e.getMessage());
        }

        System.out.println("Added the " + addedProduct.getName() + " product worth "+ addedProduct.getPrice());
    } // (POST) http://localhost:8080/addProduct (USE THE "BODY" section!!!)

    @GetMapping("/countProducts")
    public int countProducts() {
        return productService.getCount();
    } // (GET) http://localhost:8080/countProducts

    @GetMapping("/totalPrice")
    public double totalPrice() {
        return productService.getTotalPrice();
    } // (GET) http://localhost:8080/totalPrice

    @GetMapping("/averagePrice")
    public double averagePrice() {
        return productService.getAveragePrice();
    } // (GET) http://localhost:8080/averagePrice

    @PostMapping("/deleteProductByName/{name}")
    public void deleteProductByName(@PathVariable String name) {
        System.out.println("Deleting product " + name);
        productService.deleteByName(name);
    }

    // аннотация говорит спрингу, что когда
    // возникает ошибка, он должен сам вызвать этот метод и вернуть сообщение
    // Работать оно будет только в том случае, если ошибка возникнет именно в продукт-контроллере
    /*
    Либо можно унаследовать все контроллеры одного родителя и прописать обработчик в родителе,
    но это не всегда удобно и возможно, если сложная структура контроллеров и это не было предусмотрено заранее
    Плюс - в том, что данный способ позволяет обрабатывать ошибки в разных классах по-разному
    */
    @ExceptionHandler(FirstTestException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleException(FirstTestException e) {
        return new Response(e.getMessage());
    }

}
