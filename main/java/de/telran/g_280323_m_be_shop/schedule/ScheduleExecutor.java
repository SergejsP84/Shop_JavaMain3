package de.telran.g_280323_m_be_shop.schedule;

import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Product;
import de.telran.g_280323_m_be_shop.domain.entity.jpa.Task;
import de.telran.g_280323_m_be_shop.repository.jpa.TaskRepository;
import de.telran.g_280323_m_be_shop.service.jpa.JpaProductService;
import de.telran.g_280323_m_be_shop.service.jpa.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.Instant;
import java.util.List;

import static de.telran.g_280323_m_be_shop.constants.Constants.*;

@Component
@EnableScheduling
@EnableAsync // аннотация разрешает выполнение задач в этом классе в разных потоках
public class ScheduleExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleExecutor.class);

    private TaskService service;

    private JpaProductService productService;

    @Autowired
    public ScheduleExecutor(TaskService service, JpaProductService productService)
    {
        this.productService = productService;
        this.service = service;
    }

    // Task 1
    @Scheduled(fixedRate = 30000)
    public void HomeworkTask1() {
        try {
            System.out.println("Latest tasks performed:");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `280323-m-be-shop-jpa`.task\n" +
                    "ORDER BY executed_at DESC\n" +
                    "LIMIT 5");
            while (resultSet.next()) {
                String description = resultSet.getString("description");
                Timestamp executedAt = resultSet.getTimestamp("executed_at");
                System.out.println("Task " + description + " performed at " + executedAt);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Task 2
    // см. cron-interval в application.properties - cron-interval=15,45 * * * * *
    @Scheduled(cron = "${cron-interval}")
    public void HomeworkTask2() {
        Task task = service.createTask("Obtained the last product added to the database");
        LOGGER.info(task.getDescription());
        List<Product> allProducts = productService.getAll();
        Product lastProduct = allProducts.get(allProducts.size() - 1);
        LOGGER.info("The last product added to the database is a " + lastProduct.getName());
    }

//    @Scheduled(fixedDelay = 5000)
//    public void fixedDelayTask() {
//        Task task = service.createTask("Fixed delay task");
//        LOGGER.info(task.getDescription());
//    }

//    @Scheduled(fixedDelay = 5000)
//    public void fixedDelayLongTimeTask() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Task task = service.createTask("Fixed delay long time task 3000");
//        LOGGER.info(task.getDescription());
//    }

//    @Scheduled(fixedDelay = 5000)
//    public void fixedDelayLongTimeTask() {
//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Task task = service.createTask("Fixed delay long time task 7000");
//        LOGGER.info(task.getDescription());
//    }

//    @Scheduled(fixedRate = 5000)
//    public void fixedRateLongTimeTask() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Task task = service.createTask("Fixed rate long time task 3000");
//        LOGGER.info(task.getDescription());
//    }

//    @Scheduled(fixedRate = 5000)
//    public void fixedRateLongTimeTask() {
//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Task task = service.createTask("Fixed rate long time task 7000");
//        LOGGER.info(task.getDescription());
//    }

//    @Scheduled(fixedRate = 5000)
//    @Async
//    public void fixedRateLongTimeAsyncTask() {
//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Task task = service.createTask("Fixed rate long time async task 7000");
//        LOGGER.info(task.getDescription());
//    }

//    @Scheduled(fixedDelay = 5000, initialDelay = 8000)
//    public void initialDelayTask() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Task task = service.createTask("Fixed delay task 1000 with initial delay ");
//        LOGGER.info(task.getDescription());
//    }

    // fixed delay - 2 hours (7 200 000 milliseconds) -> PT02H
//    @Scheduled(fixedDelayString = "PT03S")
//    public void anotherDelayFormatTask() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Task task = service.createTask("Another delay format task 1000");
//        LOGGER.info(task.getDescription());
//    }

//    @Scheduled(fixedDelayString = "${interval}")
//    public void delayInPropertyTask() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Task task = service.createTask("Delay in property task");
//        LOGGER.info(task.getDescription());
//    }

    // 0 15 9-17 * * MON-FRI - в 15 минут каждого часа с 9 до 17 по будням
    // Задача, которая будет выполняться в 27 и в 52 секунды каждой минуты
    // см. cron-interval в application.properties - cron-interval=27,52 * * * * *
//    @Scheduled(cron = "${cron-interval}")
//    public void cronExpressionTask() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Task task = service.createTask("Cron expression task");
//        LOGGER.info(task.getDescription());
//    }

    public static void executeScheduledTask(Task task) {
        TaskScheduler scheduler = new DefaultManagedTaskScheduler();
        scheduler.schedule(() -> LOGGER.info(task.getDescription()),
                new CronTrigger("0,10,20,30,40,50 * * * * *"));
    }

    public static void executeScheduledTaskOnce(Task task) {
        TaskScheduler scheduler = new DefaultManagedTaskScheduler();
        Instant executionTime = Instant.now().plusSeconds(20);
        scheduler.schedule(() -> LOGGER.info(task.getDescription()), executionTime);
    }

}
