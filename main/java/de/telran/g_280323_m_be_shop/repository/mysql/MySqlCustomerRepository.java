package de.telran.g_280323_m_be_shop.repository.mysql;

import de.telran.g_280323_m_be_shop.domain.entity.common.CommonCart;
import de.telran.g_280323_m_be_shop.domain.entity.common.CommonCustomer;
import de.telran.g_280323_m_be_shop.domain.entity.common.CommonProduct;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Cart;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Customer;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import de.telran.g_280323_m_be_shop.repository.interfaces.CustomerRepository;
import de.telran.g_280323_m_be_shop.service.common.CommonProductService;
import de.telran.g_280323_m_be_shop.service.interfaces.ProductService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.telran.g_280323_m_be_shop.domain.database.MySQLConnector.getConnection;

public class MySqlCustomerRepository implements CustomerRepository {
    private final String ID = "customer_id";
    private final String NAME = "name";
    private final String PRODUCT_ID = "product_id";
    private final String PRODUCT_NAME = "name";
    private final String PRICE = "price";
//    private final Cart CART = new CommonCart();
    private MySqlProductRepository productRepository = new MySqlProductRepository();

    @Override
    public List<Customer> getAll() {
        try (Connection connection = getConnection()) {
            String rightQuery = "select * from customer as c left join customer_product as cp on c.customer_id = cp.customer_id left join product as p on cp.product_id = p.product_id";
            List<Customer> result = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(rightQuery);
            Set<Integer> busyIDs = new HashSet<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String customerName = resultSet.getNString("c.name");
                if (!busyIDs.contains(id)) {
                    CommonCustomer customer = new CommonCustomer();
                    customer.setId(id);
                    customer.setName(customerName);
                    customer.setCart(new CommonCart());
                    busyIDs.add(id);
                    result.add(customer);
                }
                int product_id = resultSet.getInt(PRODUCT_ID);
                String productName = resultSet.getNString("p.name");
                double productPrice = resultSet.getDouble(PRICE);
                CommonProduct addedProduct = new CommonProduct();
                addedProduct.setId(product_id);
                addedProduct.setName(productName);
                addedProduct.setPrice(productPrice);
                getById(id).getCart().addProduct(addedProduct);
//                for (Customer customer : result) {
//                    if (customer.getId() == id) customer.getCart().addProduct(addedProduct);
//                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getById(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("SELECT * FROM customer where customer_id = %d;", id);
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            CommonCustomer customer = new CommonCustomer();
            while(resultSet.next()) {
                customer.setId(id);
                customer.setName(resultSet.getNString(NAME));
                customer.setCart(new CommonCart());
            }
            String queryForCart = String.format("SELECT customer_product.customer_id,\n" +
                    " product.product_id,\n" +
                    " product.name,\n" +
                    " product.price\n" +
                    " FROM customer_product\n" +
                    " JOIN product ON customer_product.product_id=product.product_id\n" +
                    " WHERE customer_id = %d;", id);
            ResultSet obtainedProducts = connection.createStatement().executeQuery(queryForCart);
            while(obtainedProducts.next()) {
                customer.getCart().addProduct(new CommonProduct(obtainedProducts.getInt(PRODUCT_ID),
                        obtainedProducts.getNString(PRODUCT_NAME), obtainedProducts.getDouble(PRICE)));
            }
            return customer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(String name) {
        try (Connection connection = getConnection()) {
            CommonCustomer customer = new CommonCustomer();
            customer.setName(name);
            String query = String.format("INSERT INTO `customer` (`name`) VALUES ('%s');", name);
            connection.createStatement().execute(query);
            System.out.println("Customer " + name + " added to the database!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection()) {
            Customer customer = getById(id);
            if (customer != null) {
                String name = customer.getName();
                String query = String.format("DELETE FROM `customer` WHERE (`customer_id` = '%d');", id);
                connection.createStatement().execute(query);
                System.out.println("Customer " + name + " removed from the database!");
            } else System.out.println("No customers with such ID in the database!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addToCartById(int customerId, int productId) {
        try (Connection connection = getConnection()) {
            Customer customer = getById(customerId);
            if (customer != null) {
                Product product = productRepository.getById(productId);
                customer.getCart().addProduct(product);
                String query = String.format("INSERT INTO `customer_product` (`customer_id`, `product_id` )" +
                        " VALUES ('%s', '%s');", customerId, productId);
                connection.createStatement().executeUpdate(query);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFromCartById(int customerId, int productId) {
        try (Connection connection = getConnection()) {
            Customer customer = getById(customerId);
            Boolean isInCart = false;
            if (customer != null) {
                for (Product product : customer.getCart().getProducts()) {
                    if (product.getId() == productId) isInCart = true;
                }
                if (isInCart) {
                    customer.getCart().deleteProduct(productId);
                    String query = String.format("DELETE FROM `customer_product` WHERE" +
                            "(`customer_id` = '%d' AND `product_id` = %d);", customerId, productId);
                    connection.createStatement().executeUpdate(query);
                } else {
                    System.out.println("This cart does not contain any products with the specified index!");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearCart(int customerId) {
        try (Connection connection = getConnection()) {
            System.out.println("Cart clearer initiated!");
            Customer customer = getById(customerId);
            String query = String.format("DELETE FROM `customer_product` WHERE " +
                    "(`customer_id` = %d);", customerId);
            customer.getCart().clear();
            int wtf = connection.createStatement().executeUpdate(query);
            System.out.println("The executeUpdate output is " + wtf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
