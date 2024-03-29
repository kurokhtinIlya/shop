package org.solution.service.shop.domain.step.impl;

import org.solution.service.shop.domain.MoneyContext;
import org.solution.service.shop.domain.enums.StepName;
import org.solution.service.shop.domain.step.Step;

/**
 * Шаг/состояние возврата.
 */
public class RefundStep implements Step {

    /**
     * Наименование/enum шага.
     */
    private static final StepName stepName = StepName.REFUND_STEP;

    /**
     * Получить наименование шага.
     * @return наименование/enum шага.
     */
    @Override
    public StepName getStepName() {
        return stepName;
    }

    /**
     * Следующий шаг/состояние, перед переходом на котором выполняется возврат денежных средств с комиссией банка,
     * далее шаг банк.
     * @param context денежный контекст в рамках которого меняется состояние.
     */
    @Override
    public void nextStep(MoneyContext context) {
        //получаем клинта из контекста
        var client = context.getClient();
        //возврат средств клиенту с комиссией банка
        client.refund();
        //шаг/состояние банк
        context.setStep(new BankStep());
    }
}
