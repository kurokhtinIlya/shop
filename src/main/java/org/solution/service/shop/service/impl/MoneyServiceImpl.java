package org.solution.service.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.solution.service.shop.domain.Client;
import org.solution.service.shop.service.MoneyService;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с дженьгами клиента.
 */
@Service
@RequiredArgsConstructor
public class MoneyServiceImpl implements MoneyService {

    private final Client client;

    /**
     * Получить остаток денег на счету.
     * @return сумма денег на счету.
     */
    @Override
    public int getMoney() {
        return client.getMoneyInBank();
    }
}
