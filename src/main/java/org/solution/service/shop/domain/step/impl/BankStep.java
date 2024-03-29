package org.solution.service.shop.domain.step.impl;

import org.solution.service.shop.domain.MoneyContext;
import org.solution.service.shop.domain.enums.StepName;
import org.solution.service.shop.domain.step.Step;

import static org.solution.service.shop.util.BonusesCalcUtil.calcBonuses;

/**
 * Шаг/состояние банка.
 */
public class BankStep implements Step {

    /**
     * Наименование/enum шага.
     */
    private static final StepName stepName = StepName.BANK_STEP;

    /**
     * Получить наименование шага.
     * @return наименование/enum шага.
     */
    @Override
    public StepName getStepName() {
        return stepName;
    }

    /**
     * Следующий шаг/состояние, перед переходом на который выполняется бизнес логика
     * начисления бонусов на счет клиента.
     * @param context денежный контекст в рамках которого меняется состояние.
     */
    @Override
    public void nextStep(MoneyContext context) {
        //получаем клинта из контекста
        var client = context.getClient();
        //провеврим сумму покупки в утилити классе
        //если сумма больше 300, то бонусы начисляются в размере 30%
        //если просто онлайн магазин и сумма менее 300 и больше 20, то начисляется 17%
        //если просто магазин и сумма менее 300 и это не онлайн, то начисляется 10%
        //для случая, когда сумма меньше 20 и это онлайн магазин происходит возврат и балы не начисляются
        var additionalBonuses = calcBonuses(client.getCurrentPurchase(), client.isOnlineShop());
        //добавим сумму бонусов
        client.increaseBonuses(additionalBonuses);
        //следующий шаг/состояние
        context.setStep(new ClientStep());
    }

}
