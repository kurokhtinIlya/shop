package org.solution.service.shop.domain.step.impl;

import org.solution.service.shop.domain.MoneyContext;
import org.solution.service.shop.domain.enums.StepName;
import org.solution.service.shop.domain.step.Step;

/**
 * Шаг/состояние онлайн магазин.
 */
public class ShopStep implements Step {

    /**
     * Наименование/enum шага.
     */
    private static final StepName stepName = StepName.SHOP_STEP;

    /**
     * Получить наименование шага.
     * @return наименование/enum шага.
     */
    @Override
    public StepName getStepName() {
        return stepName;
    }

    /**
     * Следующий шаг/состояние, перед переходом на котором выполняется оплата покупки,
     * следующий шаг банк.
     * @param context денежный контекст в рамках которого меняется состояние.
     */
    @Override
    public void nextStep(MoneyContext context) {
        //получаем клинта из контекста
        var client = context.getClient();
        //оплата покупки
        client.purchase();
        //шаг/состояние банк
        context.setStep(new BankStep());
    }
}
