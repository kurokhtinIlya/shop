# shop-service
Сервис осуществляющий процессы оплаты, получения количества бонусов и остатка на счету в банке, посредством rest api
# Список api
api доступны дефолтно порту 8080 по ссылке http://localhost:8080
Изменить порт можно в application.yml, параметр server.port
Оплата покупки GET /api/payment/{Shop|Online}/{amount} Пример http://localhost:8080/api/payment/Online/100 (Оплата покупки в онлайн магазине на сумму 100)
Количество бонусов на счете GET /api/bankAccountOfEMoney Пример http://localhost:8080/api/bankAccountOfEMoney
Количество наличнный/безналичных GET /api/money Пример http://localhost:8080/api/money

# Реализация(основные сущности)
Реализовано с помощью поведенческого шаблона State
Интерфейс описывающий текущий статус/состояние org.solution.service.shop.domain.step.Step
Далее список классов реализующих данный интерфейс
org.solution.service.shop.domain.step.impl.ClientStep
org.solution.service.shop.domain.step.impl.ShopStep
org.solution.service.shop.domain.step.impl.OnlineShopStep
org.solution.service.shop.domain.step.impl.RefundStep
org.solution.service.shop.domain.step.impl.BankStep
Контекст состояния/статуса денег org.solution.service.shop.domain.MoneyContext, посредством которого осуществляется переход между статусами.

Добавлены вспомогатеные enum, для типа магазина, статуса оплаты и наименования статуса.
org.solution.service.shop.domain.enums.ShopType
org.solution.service.shop.domain.enums.PaymentStatus
org.solution.service.shop.domain.enums.StepName

Сущность клиента
org.solution.service.shop.domain.Client, которая хранит в себе текущее состояние клиента, так как мы оперируем с одним только клинетов на протяжение всего времени работы сервиса

Реализованы сервисы
org.solution.service.shop.service.impl.PaymentServiceImpl реализующий оплату посредством управления MoneyContext и переходом на нужный статус/шаг
org.solution.service.shop.service.impl.BonusesServiceImpl получение количества бонусов, посредством получения их сущности клиента
org.solution.service.shop.service.impl.MoneyServiceImpl получение количества денег на счету, посредством получения их сущности клиента

# Реализация(смена статусов)
Осуществив запрос GET /api/payment/{Shop|Online}/{amount} мы направляемся в метод
org.solution.service.shop.service.impl.PaymentServiceImpl#payment
Сначала данный метод проверяет тип магазина в текущем запросе и устанавливает параметр isOnlineShop сущности клиента
Далее метод устанавливает параметр currentPurchase клиенту, что в дальнешем в контексте была известна сумма покупки
Далее проверям, что денег достаточно, чтобы совершить оплату, в проотивном случае, отправляем статус FAILED и остаток денег на счету
В случае, если денег достаточно, начинаем работу с контекстом и производим переход по шагам до того момента, пока не достигнем шага ClientStep
Шаги:
1) Начальный ClientStep, если в сущности клиента isOnlineShop = true, тогда следующий шаг OnlineShopStep иначе следующий шаг ShopStep

   ClientStep --> ShopStep
   ClientStep - если isOnlineShop-> OnlineShopStep

2) На шаге магазина происходит списание денег со счета клиента
посредством метода org.solution.service.shop.domain.Client#purchase
2.1) В случае, если это был физический магазин, состояние ShopStep, то следующим шагом будет BankStep
ShopStep --> BankStep
2.2) В случае, если это был онлайн магазин, состояние OnlineShopStep, то будет произведена проверка, 
если сумма окажется менее 20, тогда следующий шаг будет RefundStep(возврат), иначе BankStep
OnlineShopStep --> RefundStep
OnlineShopStep --> BankStep
3) На шаге RefundStep происходит возврат средст с комиссией банка в 10% и дальнейший переход на шаг BankStep
RefundStep --> BankStep
4) На шаге BankStep проверяется сумма покупки и учетом суммы покупки и где она была осуществлена начисляются бонусы
Если сумма больше 300, то количество бонусов = 30%
Если покупка в физ магазине и сумма менее 300, тогда количество бонуснов = 10%
Если онлайне магазин, тогда количество бонусов равно 17%
Если был возврат, то бонусы не начисляются
После расчет и начисления бонусов осуществляется переход в состояние ClientStep и окончание работы метода оплаты
BankStep --> ClientStep
5) На шаге ClientStep заканчиваем работу с контекстом MoneyContext и возвращаем статус оплаты и остаток на счете

# Приступая к работе
Клонировать проект
```bash
git clone https://github.com/kurokhtinIlya/shop-service.git
```

# Предварительные условия
* Liberica JDK 17
* Maven >= 3.5

## Сборка проекта
```bash
mvn clean verify
```
# Запуск
```bash
cd target
java -jar shop-service-1.0.jar
```

