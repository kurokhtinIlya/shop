package org.solution.service.shop.domain.step.impl;

import org.solution.service.shop.domain.MoneyContext;
import org.solution.service.shop.domain.enums.StepName;
import org.solution.service.shop.domain.step.Step;

/**
 * Шаг/состояние онлайн магазин.
 */
public class OnlineShopStep implements Step {

    /**
     * Наименование/enum шага.
     */
    private static final StepName stepName = StepName.ONLINE_SHOP_STEP;

    /**
     * Получить наименование шага.
     * @return наименование/enum шага.
     */
    @Override
    public StepName getStepName() {
        return stepName;
    }

    /**
     * Следующий шаг/состояние, перед переходом на котором выполняется оплата онлайн покупки,
     * проверка, что сумма более 20, если менее, то следующий шаг возврат, если более, то следующий шаг банк.
     * @param context денежный контекст в рамках которого меняется состояние.
     */
    @Override
    public void nextStep(MoneyContext context) {
        //получаем клинта из контекста
        var client = context.getClient();
        //оплата покупки
        client.purchase();
        //если сумма менее 20, то следующий шаг возврат
        //иначе шаг банк
        if (client.getCurrentPurchase() < 20) {
            context.setStep(new RefundStep());
        } else {
            context.setStep(new BankStep());
        }
    }
}
