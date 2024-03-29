package org.solution.service.shop.domain;

import lombok.Getter;
import lombok.Setter;
import org.solution.service.shop.domain.step.Step;

/**
 * Денежный контекст в котором мы знаем текущий шаг/состояние, и есть информацию о клиенте,
 * для которого мы и узнаем состояние.
 */
@Getter
public class MoneyContext {
    /**
     * Шаг/состояние, текущее.
     */
    @Setter
    private Step step;
    /**
     * Клиент.
     */
    private final Client client;

    /**
     * Конструктор
     * @param step начальный шаг.
     * @param client клиент.
     */
    public MoneyContext(Step step, Client client) {
        this.step = step;
        this.client = client;
    }

    /**
     * Следующий шаг/состояние.
     */
    public void nextStep() {
        step.nextStep(this);
    }
}
