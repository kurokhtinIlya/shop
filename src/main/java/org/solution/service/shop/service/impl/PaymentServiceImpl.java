package org.solution.service.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.solution.service.shop.domain.Client;
import org.solution.service.shop.domain.step.impl.ClientStep;
import org.solution.service.shop.domain.MoneyContext;
import org.solution.service.shop.domain.PaymentResult;
import org.solution.service.shop.domain.enums.PaymentStatus;
import org.solution.service.shop.domain.enums.ShopType;
import org.solution.service.shop.domain.enums.StepName;
import org.solution.service.shop.service.PaymentService;
import org.springframework.stereotype.Service;

/**
 * Сервис оплаты.
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final Client client;

    /**
     * Оплата покупки.
     * @param purchasePrice сумма покупки.
     * @param shop тип магазина.
     * @return результат оплаты, содержащий в себе статус и остаток на счету.
     */
    @Override
    public PaymentResult payment(int purchasePrice, ShopType shop) {
        var paymentResult = new PaymentResult();
        client.setOnlineShop(shop.equals(ShopType.ONLINE));
        client.setCurrentPurchase(purchasePrice);
        if (client.canPurchase(purchasePrice)) {
            MoneyContext moneyContext = new MoneyContext(new ClientStep(), client);
            do {
                moneyContext.nextStep();
            } while (!moneyContext.getStep().getStepName().equals(StepName.CLIENT_STEP));
            paymentResult.setMoneyInBank(client.getMoneyInBank());
        } else {
            paymentResult.setPaymentStatus(PaymentStatus.FAILED);
            paymentResult.setMoneyInBank(client.getMoneyInBank());
        }
        return paymentResult;
    }
}
