package org.solution.service.shop.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Client {
    @Value("${client.moneyInBank}")
    private int moneyInBank;
    private int bonuses = 0;
    private int currentPurchase = 0;
    private boolean onlineShop;

    public boolean canPurchase(int purchasePrice) {
        return moneyInBank - purchasePrice >= 0;
    }

    public void purchase() {
        this.moneyInBank -= this.currentPurchase;
    }

    public void refund() {
        moneyInBank += (int) (currentPurchase - currentPurchase * 0.1);
    }

    public void increaseBonuses(int additionalBonuses) {
        this.bonuses += additionalBonuses;
    }

}
