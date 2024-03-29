package org.solution.service.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.solution.service.shop.domain.Client;
import org.solution.service.shop.service.BonusesService;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с бонусами клиента.
 */
@Service
@RequiredArgsConstructor
public class BonusesServiceImpl implements BonusesService {
    private final Client client;

    /**
     * Получить сумму бонусов клиента.
     * @return количество бонусов.
     */
    @Override
    public int getBonuses() {
        return client.getBonuses();
    }
}
