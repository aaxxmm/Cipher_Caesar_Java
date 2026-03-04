
package ru.javarush.kozlov.caesarcipher.exception;

public enum Operation {
    EXIT(0,"exit"),
    ENCRYPTION(1,"Шифрование"),
    DECRYPTION(2,"Расшифровать файл"),
    BRUTEFORCE(3,"Перебор ключей"),
    STATISTICALANALYZER(4,"Транскрипционный статистический анализ");

    private final int number; //номер операции,который вводит пользователь

    private final String decription;//описание этой операции(текст)

//Конструктор принимает номер операции и действие (Runnable), которое нужно выполнить.
    Operation(int number, String decription) {
        this.number = number;

        this.decription = decription;

    }

    public int getNumber() {
        return number;
    }


    public String getDecription() {
        return decription;
    }


//Ищет операцию по номеру. Если не находит - выбрасывает исключение.
    public static Operation getByNumber(int number) {
        for (Operation operation : values()) {
            if (operation.getNumber() == number) {
                return operation;
            }
        }

        throw new IllegalArgumentException("Wrong number for operation");
    }

}
/*
// Получаем операцию по номеру и выполняем её
int userInput = 2; // например, пользователь ввёл 2
Operation operation = Operation.getByNumber(userInput);
operation.run(); // Выведет: "Выполняется операция 2"
*/

// ---------------

