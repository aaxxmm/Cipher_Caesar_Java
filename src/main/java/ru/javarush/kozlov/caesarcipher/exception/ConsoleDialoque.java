
package ru.javarush.kozlov.caesarcipher.exception;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import ru.javarush.kozlov.caesarcipher.attack.BruteForce;
import ru.javarush.kozlov.caesarcipher.attack.StatisticalAnalyzer;
import ru.javarush.kozlov.caesarcipher.file.CipherCoder;
import ru.javarush.kozlov.caesarcipher.file.FileManager;


public class ConsoleDialoque implements Dialoque{

    private static final String WELCOME_MESSAGE =
            """
                    ***********
                    **Welcome**
                    ***********
                    """;
    private static final String OPERATION_PATTERN = "%d - %s;";
//номер операции и её текст

    private static final String TRY_AGAIN_COMMAND = "Повтор";

    private final Scanner in;

    private final CipherCoder caesarCoder;

    public ConsoleDialoque() {
        in = new Scanner(System.in);
        caesarCoder = new CipherCoder();
    }

    @Override
    public void start() {
        showMenu();//показали меню
        Operation operation = readOperation(); //выбрать операцию
        processOperation(operation);//выполнили операцию
    }

    private void showMenu() {
        System.out.println(WELCOME_MESSAGE);
        System.out.println("Выберите для продолжения");

        for (Operation operation : Operation.values()) {//вывод всех операций
            String message = String.format("%d - %s", operation.getNumber(),
                     operation.getDecription()
            );

            System.out.println(message);
        }
    }

    private Operation readOperation(){//возвращает enum
        boolean shouldTryAgain = false;
        do {
            try{
                int option = readInt();
                return Operation.getByNumber(option);
            } catch (IllegalArgumentException | InvalidUserInputException ex) {
                System.out.println("Operation number is wrong");
                System.out.println("Reason: " + ex.getMessage());
                System.out.println("Enter 'again' for trying again and something other for exit");

                String Input = readString();
                if (TRY_AGAIN_COMMAND.equalsIgnoreCase(Input)) {
                    shouldTryAgain = true;
                }
            }

        } while (shouldTryAgain);
        return Operation.EXIT;
    }

    private void processOperation(Operation operation){
        switch (operation){
            case EXIT -> processExit();
            case ENCRYPTION -> processEncryptionOperation();
            case DECRYPTION -> processDecryptionOperation();
            case BRUTEFORCE -> processBruteforceOperation();
            case STATISTICALANALYZER -> processStatisticalAnalysis();
        }
    }

    private void processEncryptionOperation() {
        System.out.println("Введите путь к тексту в файле: ");
        String inputFilename = readString();//путь к файлу

        System.out.println("Введите путь для обработанного файла: ");
        String outputFilename = readString();//путь куда сохранить // ❌ int -> String

        System.out.println("Введите шаг-ключ: ");//ключ
        int key = readInt();

        try {
            caesarCoder.encrypt(inputFilename, outputFilename, key);
            System.out.print("Готово!");
        } catch (FileProcessingException | CaesarCodingException ex) {
            System.out.println("Error happened. Reason: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void processDecryptionOperation() {
        System.out.println("Введите путь к тексту в файле: ");
        String inputFilename = readString();

        System.out.println("Введите путь для обработанного файла: ");
        String outputFilename = readString(); // ❌ int -> String

        System.out.println("Введите шаг-ключ: ");
        int key = readInt();

        try {
            caesarCoder.decrypt(inputFilename, outputFilename, key);
            System.out.print("Готово!");
        } catch (FileProcessingException | CaesarCodingException ex) {
            System.out.println("Error happened. Reason: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void processBruteforceOperation() {
        FileManager fileService = new FileManager();

        System.out.println("Введите путь к тексту в файле: ");
        String inputFilename = readString();//путь к файлу

        System.out.println("Введите путь для обработанного файла: ");
        String outputFilename = readString();//путь куда сохранить // ❌ int -> String

        try { // Читаем зашифрованный текст из файла
            String encryptedText = String.join("\n",
                    fileService.readFile(inputFilename));
            // Вызываем брутфорс с правильными параметрами
            String result = BruteForce.decryptByBruteForce(encryptedText, outputFilename);
            // Сохраняем результат в файл
            fileService.writeFile(result, outputFilename);
            System.out.print("Готово!");
        } catch (FileProcessingException | CaesarCodingException ex) {
            System.out.println("Error happened. Reason: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void processStatisticalAnalysis() {
        FileManager fileService = new FileManager();

        System.out.println("Введите путь к тексту в файле:");
        String inputFilename = readString();

        System.out.println("Введите путь для словаря:");
        String dictionaryFilename = readString();

        System.out.println("Введите путь для обработанного файла:");
        String outputFilename = readString();

        String encryptedText = String.join("\n",
                fileService.readFile(inputFilename));

        Set<String> dictionary = fileService.readFile(dictionaryFilename)
                .stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        StatisticalAnalyzer analyzer =
                new StatisticalAnalyzer(dictionary);

        String bestDecryption =
                String.valueOf(analyzer.tryAllShifts(encryptedText.toLowerCase()));

        fileService.writeFile(bestDecryption, outputFilename);

       // System.out.println("Расшифровка завершена.");
    }

    private void processExit() {
            System.out.print("Bye!");
        }

    private int readInt() {
            String input = in.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                throw new InvalidUserInputException("Integer value is wrong. " + ex);
            }
    }

    private String readString() {

        return in.nextLine();
    }

} //class ConsoleDialoque

