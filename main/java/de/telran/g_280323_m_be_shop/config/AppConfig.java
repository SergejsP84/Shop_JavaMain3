package de.telran.g_280323_m_be_shop.config;

import de.telran.g_280323_m_be_shop.domain.database.common.CommonDatabase;
import de.telran.g_280323_m_be_shop.domain.database.interfaces.Database;
import de.telran.g_280323_m_be_shop.logging.AspectLogging;
import de.telran.g_280323_m_be_shop.repository.common.CommonCustomerRepository;
import de.telran.g_280323_m_be_shop.repository.common.CommonProductRepository;
import de.telran.g_280323_m_be_shop.repository.interfaces.CustomerRepository;
import de.telran.g_280323_m_be_shop.repository.interfaces.ProductRepository;
import de.telran.g_280323_m_be_shop.repository.mysql.MySqlCustomerRepository;
import de.telran.g_280323_m_be_shop.repository.mysql.MySqlProductRepository;
import de.telran.g_280323_m_be_shop.service.common.CommonCustomerService;
import de.telran.g_280323_m_be_shop.service.common.CommonProductService;
import de.telran.g_280323_m_be_shop.service.interfaces.CustomerService;
import de.telran.g_280323_m_be_shop.service.interfaces.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "de.telran.g.280323_m_be_shop")
public class AppConfig {

    @Bean
    public AspectLogging aspectLogging() {
        return new AspectLogging();
    }
    //    @Bean
//    public Database database() {
//        return new CommonDatabase();
//    }
//    @Bean
//    public ProductRepository productRepository() {
//        return new MySqlProductRepository();
//    }
//    @Bean
//    public CustomerRepository customerRepository() {
//        return new MySqlCustomerRepository();
//    }
//    @Bean
//    public ProductService productService() {
//        return new CommonProductService();
//    }
//    @Bean
//    public CustomerService customerService() {
//        return new CommonCustomerService();
//    }
}
