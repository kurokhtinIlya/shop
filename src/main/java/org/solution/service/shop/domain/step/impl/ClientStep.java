package org.solution.service.shop.domain.step.impl;

import org.solution.service.shop.domain.MoneyContext;
import org.solution.service.shop.domain.enums.StepName;
import org.solution.service.shop.domain.step.Step;

/**
 * Шаг состояние клинета. Начальный шаг/состояние.
 */
public class ClientStep implements Step {

    /**
     * Наименование/enum шага.
     */
    private static final StepName stepName = StepName.CLIENT_STEP;

    /**
     * Получить наименование шага.
     * @return наименование/enum шага.
     */
    @Override
    public StepName getStepName() {
        return stepName;
    }

    /**
     * Следующий шаг/состояние, перед переходом на который проверяется в какой магазин обратился клиент
     * и в зависимости от магазина выбирается следующий шаг/состояние.
     * @param context денежный контекст в рамках которого меняется состояние.
     */
    @Override
    public void nextStep(MoneyContext context) {
        //получаем клинта из контекста
        var client = context.getClient();
        //проверяем флаг онлайн магазина, если это онлайн магазин то тогда слеющий шаг OnlineShopStep
        //если не онлайн магазин, тогда слеющий шаг ShopStep
        if (client.isOnlineShop()) {
            context.setStep(new OnlineShopStep());
        } else {
            context.setStep(new ShopStep());
        }
    }
}
