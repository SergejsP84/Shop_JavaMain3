package de.telran.g_280323_m_be_shop.exception_handling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
Плюс в том, что это глобальный обработчик. Ловит ошибку в любом классе.
Также это очень простой способ, который не требует написания какого-либо кода.
Минус в том, что нету информативного сообщения для клиента, отправляем только статус.

 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SecondTestException extends RuntimeException {
    public SecondTestException(String message) {
        super(message);
    }
}
