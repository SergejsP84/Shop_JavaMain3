package de.telran.g_280323_m_be_shop.exception_handling.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }

}
