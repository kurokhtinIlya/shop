package org.solution.service.shop.service;

/**
 * Интерфейс для работы с деньгами на счету клиента.
 */
public interface MoneyService {

    /**
     * Получить остаток денег на счету.
     * @return сумма денег на счету.
     */
    int getMoney();
}
