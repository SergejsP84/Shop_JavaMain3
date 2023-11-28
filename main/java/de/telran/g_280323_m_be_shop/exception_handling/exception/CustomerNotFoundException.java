package de.telran.g_280323_m_be_shop.exception_handling.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
