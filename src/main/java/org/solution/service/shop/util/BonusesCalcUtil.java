package org.solution.service.shop.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BonusesCalcUtil {

    /**
     * Получение расчитанной дополнительной суммы бонусов.
     * @param amount сумма покупки.
     * @param isOnlineShop является ли магазин онлайн магазином.
     * @return сумма бонусов.
     */
    public static int calcBonuses(int amount, boolean isOnlineShop) {
        //если сумма больше 300, то бонусы начисляются в размере 30%
        //если просто онлайн магазин и сумма менее 300 и больше 20, то начисляется 17%
        //если просто магазин и сумма менее 300 и это не онлайн, то начисляется 10%
        //для случая, когда сумма меньше 20 и это онлайн магазин происходит возврат и балы не начисляются
        if (amount > 300) {
            return (int) (amount * 0.3);
        } else if (isOnlineShop && amount > 20){
            return (int) (amount * 0.17);
        } else if (!isOnlineShop) {
            return (int) (amount * 0.1);
        } else {
            return 0;
        }
    }
}
