package org.solution.service.shop.domain.step;

import org.solution.service.shop.domain.MoneyContext;
import org.solution.service.shop.domain.enums.StepName;

/**
 * Интерфейс текущего шага/состояния.
 */
public interface Step {

    /**
     * Получить наименование шага.
     * @return наименование/enum шага.
     */
    StepName getStepName();

    /**
     * Следующий шаг/состояние, перед переходом происзводятся логические проверки и выполняется некоторя бизнес логика,
     * зависящая от текущего шага и пожеланий клиента.
     * @param context денежный контекст в рамках которого меняется состояние.
     */
    void nextStep(MoneyContext context);
}
