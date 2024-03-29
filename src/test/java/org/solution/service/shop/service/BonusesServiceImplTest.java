package org.solution.service.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.solution.service.shop.domain.Client;
import org.solution.service.shop.service.impl.BonusesServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Класс в котором находятся юнит тесты сервиса работы с бонусами ({@link BonusesServiceImpl})
 */
public class BonusesServiceImplTest {

    @Mock
    private Client client;

    @InjectMocks
    private BonusesServiceImpl bonusesService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Тест проверяющий, что метод возвращает ожидаемое количество бонусов.
     */
    @Test
    void getBonusesTest() {
        var expected = 123;
        when(client.getBonuses()).thenReturn(expected);
        var result = bonusesService.getBonuses();
        assertEquals(expected, result);
    }
}
