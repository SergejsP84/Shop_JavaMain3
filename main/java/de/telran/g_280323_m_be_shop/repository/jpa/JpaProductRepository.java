package de.telran.g_280323_m_be_shop.repository.jpa;

import de.telran.g_280323_m_be_shop.domain.entity.jpa.JpaProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaProductRepository extends JpaRepository<JpaProduct, Integer> {

    @Transactional
    void deleteByName(String name);

    @Query(value = "SELECT SUM(price) FROM product;", nativeQuery = true) // Если не добавить nativeQuery,
    // Spring будет считать, что оно написано на HQL
    double getTotalPrice();

    @Query(value = "SELECT AVG(price) FROM product;", nativeQuery = true) // Если не добавить nativeQuery,
        // Spring будет считать, что оно написано на HQL
    double getAveragePrice();
}
