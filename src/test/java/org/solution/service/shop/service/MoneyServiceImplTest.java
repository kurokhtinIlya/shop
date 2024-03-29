package org.solution.service.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.solution.service.shop.domain.Client;
import org.solution.service.shop.service.impl.MoneyServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Класс в котором находятся юнит тесты методов сервиса работы с остатками денежных средств в банке.
 */
public class MoneyServiceImplTest {

    @Mock
    private Client client;

    @InjectMocks
    private MoneyServiceImpl moneyService;

    @BeforeEach
    void setUp() {
        //инициализация моков.
        initMocks(this);
    }

    /**
     * Тест проверяющий, что метод возвращает ожидаемое количество денег.
     */
    @Test
    void getMoneyTest() {
        var expected = 3463;
        when(client.getMoneyInBank()).thenReturn(expected);
        var result = moneyService.getMoney();
        assertEquals(expected, result);
    }
}
