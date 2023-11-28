package de.telran.g_280323_m_be_shop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AspectLogging {

    private final Logger LOGGER = LoggerFactory.getLogger(AspectLogging.class);

    @Pointcut("execution(* de.telran.g_280323_m_be_shop.service.jpa.JpaProductService.*(..))")
    public void productServiceMethods() {}

    @Before("productServiceMethods()")
    public void onProductEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        Object[] args = joinPoint.getArgs();
        LOGGER.info("Triggered method: " + methodName + " from class" + className + ", incoming arguments: " + Arrays.toString(args));
    }

    @After("productServiceMethods()")
    public void onProductExit(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        LOGGER.info("Disengaging method: " + methodName + " from class" + className);
    }

    @AfterReturning("productServiceMethods()")
    public void afterReturnValueGeneral(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        LOGGER.info("Method " + methodName + " from class" + className + " has returned a value");
    }

    @AfterThrowing("productServiceMethods()")
    public void afterThrowingExceptionGeneral(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        LOGGER.info("Method " + methodName + " from class" + className + " has thrown an exception");
    }

    @Around("execution(* de.telran.g_280323_m_be_shop.service.jpa.JpaProductService.*(..))")
    public Object aroundProduct(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        LOGGER.info("Invoked the " + methodName + " method of " + className + " class");
        try {
            Object result = joinPoint.proceed();
            LOGGER.info(methodName + "has delivered the following result: {}", result);
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Pointcut("execution(* de.telran.g_280323_m_be_shop.service.jpa.JpaCustomerService.*(..))")
    public void customerServiceMethods() {}

    @Before("customerServiceMethods()")
    public void onCustomerEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        Object[] args = joinPoint.getArgs();
        LOGGER.info("Triggered method: " + methodName + " from class" + className + ", incoming arguments: " + Arrays.toString(args));
    }

    @After("customerServiceMethods()")
    public void onCustomerExit(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        LOGGER.info("Disengaging method: " + methodName + " from class" + className);
    }

    @AfterReturning("customerServiceMethods()")
    public void afterReturnValueGeneralCustomer(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        LOGGER.info("Method " + methodName + " from class" + className + " has returned a value");
    }

    @AfterThrowing("customerServiceMethods()")
    public void afterThrowingExceptionGeneralCustomer(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        LOGGER.info("Method " + methodName + " from class" + className + " has thrown an exception");
    }

    @Around("execution(* de.telran.g_280323_m_be_shop.service.jpa.JpaCustomerService.*(..))")
    public Object aroundCustomer(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        LOGGER.info("Invoked the " + methodName + " method of " + className + " class");
        try {
            Object result = joinPoint.proceed();
            LOGGER.info(methodName + "has delivered the following result: {}", result);
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

// HOMEWORK ENDS HERE
    
//    public void addProduct() {}
//
//    @Before("addProduct()")
//    public void beforeAddProduct(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        LOGGER.info("Вызван метод add класса JpaProductService с параметром {}.", args[0]);
//    }
//
//
//    @After("addProduct()")
//    public void afterAddProduct(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        LOGGER.info("Метод add класса JpaProductService завершил работу. {}.", args[0]);
//    }

    @Pointcut("execution(* de.telran.g_280323_m_be_shop.controllers.ProductController.addProduct(..))")
    public void addProductInController() {}

    @AfterReturning("addProductInController()")
    public void afterReturnValue() {
        LOGGER.info("Метод add класса ProductController успешно вернул значение.");
    }

    @AfterThrowing("addProductInController()")
    public void afterThrowingException() {
        LOGGER.info("Метод add класса ProductController выбросил исключение.");
    }

    @Pointcut("execution(* de.telran.g_280323_m_be_shop.service.jpa.JpaProductService.getCount(..))")
    public void getProductCount() {}

    @Around("execution(* de.telran.g_280323_m_be_shop.service.jpa.JpaProductService.getProductCount(..))")
    public Object aroundGetCount(ProceedingJoinPoint joinPoint) {
        LOGGER.info("Вызван метод getCount класса JpaProductService");
        try {
            Object result = joinPoint.proceed();
            LOGGER.info("Метод getCount класса JpaProductService отработал с результатом {}", result);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return 777;
    }

}
