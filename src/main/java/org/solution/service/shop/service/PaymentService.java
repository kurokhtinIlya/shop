package org.solution.service.shop.service;

import org.solution.service.shop.domain.PaymentResult;
import org.solution.service.shop.domain.enums.ShopType;


/**
 * Интерфейс для оплаты.
 */
public interface PaymentService {

    /**
     * Оплата покупки.
     * @param purchasePrice сумма покупки.
     * @param shop тип магазина.
     * @return результат оплаты, содержащий в себе статус и остаток на счету.
     */
    PaymentResult payment(int purchasePrice, ShopType shop);
}
