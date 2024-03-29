package org.solution.service.shop.controller;

import lombok.RequiredArgsConstructor;
import org.solution.service.shop.domain.PaymentResult;
import org.solution.service.shop.domain.enums.ShopType;
import org.solution.service.shop.service.BonusesService;
import org.solution.service.shop.service.MoneyService;
import org.solution.service.shop.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер оплаты, получения информация по счету и количества бонусов.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final PaymentService paymentService;
    private final BonusesService bonusesService;
    private final MoneyService moneyService;

    /**
     * Оплатить покупку.
     * @param amount сумма покупки.
     * @return статус оплаты.
     */
    @GetMapping("/payment/{shopType}/{amount}")
    public ResponseEntity<PaymentResult> pay(
            @PathVariable("amount") String amount,
            @PathVariable("shopType") String shopType) {
        var shop = ShopType.valueOf(shopType.toUpperCase());
        return ResponseEntity.ok(paymentService.payment(Integer.parseInt(amount), shop));
    }

    /**
     * Получить количество бонусов клиента.
     * @return количестиво бонусов клиента.
     */
    @GetMapping("/bankAccountOfEMoney")
    public ResponseEntity<Integer> getBonuses() {
        return ResponseEntity.ok(bonusesService.getBonuses());
    }

    /**
     * Получить остаток денег на счету.
     * @return остаток денег.
     */
    @GetMapping("/money")
    public ResponseEntity<Integer> getMoney() {
        return ResponseEntity.ok(moneyService.getMoney());
    }
}
