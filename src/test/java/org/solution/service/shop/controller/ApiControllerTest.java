package org.solution.service.shop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.solution.service.shop.domain.Client;
import org.solution.service.shop.domain.enums.PaymentStatus;
import org.solution.service.shop.domain.enums.ShopType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.solution.service.shop.util.BonusesCalcUtil.calcBonuses;

/**
 * Класс, сотдержащий тесты для ({@link ApiController})
 */
@ComponentScan(basePackages = "org.solution")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApiControllerTest {
    @Autowired
    private ApiController controller;
    @Autowired
    private Client client;

    /**
     * Перед тестом устанавливаем дефолтные значения для клиента.
     */
    @BeforeEach
    void setUp() {
        client.setMoneyInBank(5000);
        client.setBonuses(0);
    }

    /**
     * Тест проверяющий апи оплаты.
     */
    @Test
    void payTest() {
        //запрос на оплату 100
        var result = controller.pay("100", ShopType.ONLINE.toString());
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //проверяем, что статус оплаты SUCCESS
        assertEquals(result.getBody().getPaymentStatus(), PaymentStatus.SUCCESS);
        //проверка, что у клиента бонусов стало 17
        assertEquals(17, client.getBonuses());
        //проверка, что у клиента остаток денег стал 4900
        assertEquals(4900, client.getMoneyInBank());
        //проверка, что у клинета количество денег на остатке равно остатку из ответа
        assertEquals(client.getMoneyInBank(), result.getBody().getMoneyInBank());
    }

    /**
     * Тест проверяющий апи оплаты.
     */
    @Test
    void payFailedTest() {
        client.setMoneyInBank(99);
        //запрос на оплату 100
        var result = controller.pay("100", ShopType.ONLINE.toString());
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //проверяем, что статус оплаты SUCCESS
        assertEquals(result.getBody().getPaymentStatus(), PaymentStatus.FAILED);
        //проверка, что у клиента бонусов стало 0
        assertEquals(0, client.getBonuses());
        //проверка, что у клиента остаток денег стал 4900
        assertEquals(99, client.getMoneyInBank());
        //проверка, что у клинета количество денег на остатке равно остатку из ответа
        assertEquals(client.getMoneyInBank(), result.getBody().getMoneyInBank());
    }

    /**
     * Тест проверяющий апи получения бонусов.
     */
    @Test
    void getBonusesTest() {
        //проверка бонусов до оплаты, ожидается 0
        var before = client.getBonuses();
        //запрос на получение количества бонусов
        var result = controller.getBonuses();
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //сравнение, что бонусов на счету равно ожидаемому, то есть 0
        assertEquals(before, result.getBody());
        //запрос на оплату 200
        controller.pay("200", ShopType.SHOP.toString());
        //запрос на получение бонусов
        result = controller.getBonuses();
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //сравниваем количество бонусов, ожидаем 20
        assertEquals(20, result.getBody());
        //сравнием, что у клиента количество бонусов равно числу бонусов из ответа
        assertEquals(client.getBonuses(), result.getBody());
    }

    /**
     * Тест проверяющий апи получения количества денег на счету.
     */
    @Test
    void getMoneyTest() {
        //денег изначально у клиента. 5000
        var before = client.getMoneyInBank();
        //запрос остатка денег в банке. Ожидается 5000
        var result = controller.getMoney();
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //сравниваем, что количество денег на остатке из ответа равно первоначальному,
        //полученному из клиента до операции
        assertEquals(before, result.getBody());
        //запрос на оплату 200
        controller.pay("200", ShopType.SHOP.toString());
        //запрос остатка, ожидается 4800
        result = controller.getMoney();
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //сравнение денег полученных запросом, ожидается 4800
        assertEquals(4800, result.getBody());
        //сравнение, что у текущего клиента количенство денег равно количеству денег из запроса
        assertEquals(client.getMoneyInBank(), result.getBody());
    }

    /**
     * Полный тест из тз.
     * 1. Online/100
     * 2. Shop/120
     * 3. Online/301
     * 4. Online/17
     * 5. Shop/1000
     * 6. Online/21
     * 7. Shop/570
     * 8. Online/700
     */
    @Test
    void fullTest() {
        //начальное количество денег 5000
        //параметры теста 1
        //сумма покупки = 100
        //магазин = онлайн
        var stepAmount = 100;
        var stepShop = ShopType.ONLINE;
        //ожидаемое количество денег
        var expectedMoney = expectedMoney(5000, stepAmount, stepShop);
        //ожидаемое количество бонусов
        var expectedBonuses = expectedBonuses(0, stepAmount, stepShop);

        //выполняем запрос на оплату
        var result = controller.pay(String.valueOf(stepAmount), stepShop.toString());
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //проверяем, что статус оплаты SUCCESS
        assertEquals(result.getBody().getPaymentStatus(), PaymentStatus.SUCCESS);
        //проверяем бонусы
        assertEquals(expectedBonuses, client.getBonuses());
        //проверяем остаток на счету
        assertEquals(expectedMoney, client.getMoneyInBank());
        //проверяем, что денег у клиента столько, сколько в ответе
        assertEquals(client.getMoneyInBank(), result.getBody().getMoneyInBank());

        //параметры теста 2
        //сумма покупки = 120
        //магазин = физический
        stepAmount = 120;
        stepShop = ShopType.SHOP;
        expectedMoney = expectedMoney(expectedMoney, stepAmount, stepShop);
        expectedBonuses = expectedBonuses(expectedBonuses, stepAmount, stepShop);

        //выполняем запрос на оплату
        result = controller.pay(String.valueOf(stepAmount), stepShop.toString());
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //проверяем, что статус оплаты SUCCESS
        assertEquals(result.getBody().getPaymentStatus(), PaymentStatus.SUCCESS);
        //проверяем бонусы
        assertEquals(expectedBonuses, client.getBonuses());
        //проверяем остаток на счету
        assertEquals(expectedMoney, client.getMoneyInBank());
        //проверяем, что денег у клиента столько, сколько в ответе
        assertEquals(client.getMoneyInBank(), result.getBody().getMoneyInBank());

        //параметры теста 3
        //сумма покупки = 301
        //магазин = онлайн
        stepAmount = 301;
        stepShop = ShopType.ONLINE;
        expectedMoney = expectedMoney(expectedMoney, stepAmount, stepShop);
        expectedBonuses = expectedBonuses(expectedBonuses, stepAmount, stepShop);

        //выполняем запрос на оплату
        result = controller.pay(String.valueOf(stepAmount), stepShop.toString());
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //проверяем, что статус оплаты SUCCESS
        assertEquals(result.getBody().getPaymentStatus(), PaymentStatus.SUCCESS);
        //проверяем бонусы
        assertEquals(expectedBonuses, client.getBonuses());
        //проверяем остаток на счету
        assertEquals(expectedMoney, client.getMoneyInBank());
        //проверяем, что денег у клиента столько, сколько в ответе
        assertEquals(client.getMoneyInBank(), result.getBody().getMoneyInBank());

        //параметры теста 4
        //сумма покупки = 17
        //магазин = онлайн
        stepAmount = 17;
        stepShop = ShopType.ONLINE;
        expectedMoney = expectedMoney(expectedMoney, stepAmount, stepShop);
        expectedBonuses = expectedBonuses(expectedBonuses, stepAmount, stepShop);

        //выполняем запрос на оплату
        result = controller.pay(String.valueOf(stepAmount), stepShop.toString());
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //проверяем, что статус оплаты SUCCESS
        assertEquals(result.getBody().getPaymentStatus(), PaymentStatus.SUCCESS);
        //проверяем бонусы
        assertEquals(expectedBonuses, client.getBonuses());
        //проверяем остаток на счету
        assertEquals(expectedMoney, client.getMoneyInBank());
        //проверяем, что денег у клиента столько, сколько в ответе
        assertEquals(client.getMoneyInBank(), result.getBody().getMoneyInBank());

        //параметры теста 5
        //сумма покупки = 1000
        //магазин = физический
        stepAmount = 1000;
        stepShop = ShopType.SHOP;
        expectedMoney = expectedMoney(expectedMoney, stepAmount, stepShop);
        expectedBonuses = expectedBonuses(expectedBonuses, stepAmount, stepShop);

        //выполняем запрос на оплату
        result = controller.pay(String.valueOf(stepAmount), stepShop.toString());
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //проверяем, что статус оплаты SUCCESS
        assertEquals(result.getBody().getPaymentStatus(), PaymentStatus.SUCCESS);
        //проверяем бонусы
        assertEquals(expectedBonuses, client.getBonuses());
        //проверяем остаток на счету
        assertEquals(expectedMoney, client.getMoneyInBank());
        //проверяем, что денег у клиента столько, сколько в ответе
        assertEquals(client.getMoneyInBank(), result.getBody().getMoneyInBank());

        //параметры теста 6
        //сумма покупки = 21
        //магазин = онлайн
        stepAmount = 21;
        stepShop = ShopType.ONLINE;
        expectedMoney = expectedMoney(expectedMoney, stepAmount, stepShop);
        expectedBonuses = expectedBonuses(expectedBonuses, stepAmount, stepShop);

        //выполняем запрос на оплату
        result = controller.pay(String.valueOf(stepAmount), stepShop.toString());
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //проверяем, что статус оплаты SUCCESS
        assertEquals(result.getBody().getPaymentStatus(), PaymentStatus.SUCCESS);
        //проверяем бонусы
        assertEquals(expectedBonuses, client.getBonuses());
        //проверяем остаток на счету
        assertEquals(expectedMoney, client.getMoneyInBank());
        //проверяем, что денег у клиента столько, сколько в ответе
        assertEquals(client.getMoneyInBank(), result.getBody().getMoneyInBank());

        //параметры теста 7
        //сумма покупки = 570
        //магазин = физический
        stepAmount = 570;
        stepShop = ShopType.SHOP;
        expectedMoney = expectedMoney(expectedMoney, stepAmount, stepShop);
        expectedBonuses = expectedBonuses(expectedBonuses, stepAmount, stepShop);

        //выполняем запрос на оплату
        result = controller.pay(String.valueOf(stepAmount), stepShop.toString());
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //проверяем, что статус оплаты SUCCESS
        assertEquals(result.getBody().getPaymentStatus(), PaymentStatus.SUCCESS);
        //проверяем бонусы
        assertEquals(expectedBonuses, client.getBonuses());
        //проверяем остаток на счету
        assertEquals(expectedMoney, client.getMoneyInBank());
        //проверяем, что денег у клиента столько, сколько в ответе
        assertEquals(client.getMoneyInBank(), result.getBody().getMoneyInBank());

        //параметры теста 8
        //сумма покупки = 700
        //магазин = онлайн
        stepAmount = 700;
        stepShop = ShopType.SHOP;
        expectedMoney = expectedMoney(expectedMoney, stepAmount, stepShop);
        expectedBonuses = expectedBonuses(expectedBonuses, stepAmount, stepShop);

        //выполняем запрос на оплату
        result = controller.pay(String.valueOf(stepAmount), stepShop.toString());
        //проверка, что пришел ответ не null
        assertNotNull(result);
        //проверяем, что тело не null
        assertNotNull(result.getBody());
        //проверяем, что статус оплаты SUCCESS
        assertEquals(result.getBody().getPaymentStatus(), PaymentStatus.SUCCESS);
        //проверяем бонусы
        assertEquals(expectedBonuses, client.getBonuses());
        //проверяем остаток на счету
        assertEquals(expectedMoney, client.getMoneyInBank());
        //проверяем, что денег у клиента столько, сколько в ответе
        assertEquals(client.getMoneyInBank(), result.getBody().getMoneyInBank());

        //проверяем, что после всех операций денег осталось 2186, расчетное
        assertEquals(2186, client.getMoneyInBank());
        //проверяем, что бонусов после всех операций 803, расчетное
        assertEquals(803, client.getBonuses());
    }

    /**
     * Ожидаемое количество бонусов.
     * @param currentBonuses текущее значение.
     * @param amount сумма покупки.
     * @param shopType тип магазина.
     * @return обновленное количество бонусов.
     */
    private int expectedBonuses(int currentBonuses, int amount, ShopType shopType) {
        var isOnlineShop = shopType.equals(ShopType.ONLINE);
        return currentBonuses + calcBonuses(amount, isOnlineShop);
    }

    /**
     * Ожидаемое количество денег.
     * @param actual актуальное/текущее количество денег.
     * @param amount сумма покупки.
     * @param shopType тип магазина.
     * @return ожидаемое обновленное количество денег.
     */
    private int expectedMoney(int actual, int amount, ShopType shopType) {
        var isOnlineShop = shopType.equals(ShopType.ONLINE);
        if (isOnlineShop && amount < 20) {
            return (int) (actual + - amount * 0.1);
        } else {
            return actual - amount;
        }
    }
}
