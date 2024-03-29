package org.solution.service.shop.domain;

import lombok.Data;
import org.solution.service.shop.domain.enums.PaymentStatus;

/**
 * Результат оплаты.
 */
@Data
public class PaymentResult {
    /**
     * Статус. По умолчанию SUCCESS.
     */
    private PaymentStatus paymentStatus = PaymentStatus.SUCCESS;
    /**
     * Остаток денег в банке.
     */
    private int moneyInBank;
}
