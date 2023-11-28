package de.telran.g_280323_m_be_shop.repository.mysql;

import de.telran.g_280323_m_be_shop.domain.entity.common.CommonProduct;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import de.telran.g_280323_m_be_shop.repository.interfaces.ProductRepository;

import javax.print.attribute.standard.MediaSize;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static de.telran.g_280323_m_be_shop.domain.database.MySQLConnector.getConnection;

public class MySqlProductRepository implements ProductRepository {

    private final String ID = "product_id";
    private final String NAME = "name";
    private final String PRICE = "price";

    @Override
    public List<Product> getAll() {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM product;";
            //.executeQuery = когда хотим получить, ,execute - когда апдейт
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Product> result = new ArrayList<>();

            while(resultSet.next()) { // отработает столько раз, сколько продуктов лежит в базе данных
//                int id = resultSet.getInt(1); // первая колонка в таблице. Может глючть при изменении базщы данных!
                int id = resultSet.getInt(ID);
                String name = resultSet.getNString(NAME);
                double price = resultSet.getDouble(PRICE);
                result.add(new CommonProduct(id, name, price));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getById(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("SELECT * FROM product where product_id = %d;" , id);
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            Product product = null;

            while(resultSet.next()) { // почему через цикл: результат только один, но вот это надо,
                // чтобы переключиться на следующую строчку
                String name = resultSet.getString(NAME);
                double price = resultSet.getDouble(PRICE);
                product = new CommonProduct(id, name, price);
            }

            return product;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(String name, double price) {
        try (Connection connection = getConnection()) {
        String query = String.format(Locale.US,
                "INSERT INTO `product` (`name`, `price`) VALUES ('%s', '%.2f');", name, price);
        //Locale.US здесь используется для того, чтобы дробное число передавалось не с запятой, а с точкой
        connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("DELETE FROM `product` WHERE (`product_id` = '%d');", id);
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
