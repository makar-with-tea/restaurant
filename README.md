# Макаревич Мария, БПИ228
Домашнее задание 2 по дисциплине "Конструирование программного обеспечения": Система управления заказами в ресторане
## Механизм работы с приложением
Взаимодействие с приложением происходит с помощью ввода пользователем номеров команд, а также необходимой текстовой информации. 
В начале работы приложения пользователь может создать новый аккаунт или зайти в существующий. 
Если в какой-то момент пользователь выходит из аккаунта, ему также становятся доступны лишь эти две функции.
Для зарегистрированного пользователя интерфейс различается в зависимости от того, является пользователь администратором или посетителем.

### 1. Возможности администратора
Администратор ресторана может выбрать одну из 10 доступных команд:

1. Посмотреть меню
2. Добавить блюдо в меню
3. Удалить блюдо из меню
4. Изменить количество единиц блюда в ресторане
5. Изменить цену блюда
6. Изменить время приготовления блюда
7. Посмотреть прибыль ресторана
8. Зайти в другой аккаунт
9. Удалить аккаунт
10. Завершить работу приложения

### 2. Возможности посетителя
Посетитель ресторана может выбрать одну из 11 доступных команд:

1. Посмотреть меню
2. Начать оформление заказа
3. Добавить блюдо из меню в заказ
4. Завершить оформление заказа
5. Отменить заказ
6. Узнать статус заказа
7. Оплатить заказ
8. Зайти в другой аккаунт
9. Удалить аккаунт
10. Посмотреть список id Ваших заказов
11. Завершить работу приложения
### 3. Общие принципы
При выборе команд, выполнение которых требует дополнительных данных (например, id или название блюда), приложение запрашивает у пользователя эти данные и выводит подсказки для их корректного ввода.

Ввод всех данных в программе реализован таким образом: пользователь вводит данные, приложение проверяет их на корректность, и, если проверка не пройдена, напоминает об ограничениях, свойственных этим данным, и просит ввести данные повторно. Это позволяет приложению не допускать ошибок и корректно работать при любых входных данных.

После выполнения команды пользователя программа запрашивает новую команду, выводя список всех возможных команд. Если же во время выполнения команды происходит ошибка, приложение выводит ее содержание в консоль и переходит к обработке следующей команды пользователя.

## Шаблоны
В приложении использованы паттерны Одиночка и Фасад.
### Одиночка
Данный паттерн применен в классе RestaurantDaoImpl, так как в программе существует лишь один ресторан
### Фасад