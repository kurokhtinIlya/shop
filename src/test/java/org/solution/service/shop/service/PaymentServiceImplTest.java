package org.solution.service.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.solution.service.shop.domain.Client;
import org.solution.service.shop.domain.enums.PaymentStatus;
import org.solution.service.shop.domain.enums.ShopType;
import org.solution.service.shop.service.impl.PaymentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Класс в котором находятся юнит тесты, проверяющие работу методов сервиса ({@link PaymentServiceImpl})
 */
public class PaymentServiceImplTest {

    @Mock
    private Client client;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Тест проверяющий метод оплаты.
     */
    @Test
    void paymentTest() {
        when(client.getMoneyInBank()).thenReturn(4800);
        when(client.getBonuses()).thenReturn(20);
        when(client.isOnlineShop()).thenReturn(true);
        when(client.getCurrentPurchase()).thenReturn(300);
        when(client.canPurchase(300)).thenReturn(true);

        var result = paymentService.payment(300, ShopType.ONLINE);
        assertEquals(PaymentStatus.SUCCESS, result.getPaymentStatus());
        assertEquals(4800, result.getMoneyInBank());
    }

    /**
     * Тест проверяющий метод оплаты, если оплата не успешна.
     */
    @Test
    void paymentTestFailed() {
        when(client.getMoneyInBank()).thenReturn(4800);
        when(client.getBonuses()).thenReturn(20);
        when(client.isOnlineShop()).thenReturn(true);
        when(client.getCurrentPurchase()).thenReturn(300);
        when(client.canPurchase(300)).thenReturn(false);

        var result = paymentService.payment(300, ShopType.ONLINE);
        assertEquals(PaymentStatus.FAILED, result.getPaymentStatus());
        assertEquals(4800, result.getMoneyInBank());
    }

}
