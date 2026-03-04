
package ru.javarush.kozlov.caesarcipher.attack;

import ru.javarush.kozlov.caesarcipher.file.FileManager;
import static ru.javarush.kozlov.caesarcipher.cipher.CipherAlphavit.RULETTERS;
import static ru.javarush.kozlov.caesarcipher.cipher.CipherAlphavit.SMALL_ALPHA_RUS;

public class BruteForce { //Реализация метода перебора всех ключей для взлома.

    public static String decryptByBruteForce(String encryptedText, String outputFilename) {
        //принимает зашифрованный текст и имя файла для сохранения
        FileManager fileService = new FileManager();
        String lowerCaseText = encryptedText.toLowerCase();

        // Массив для хранения значений частоты букв для каждого смещения
        StringBuilder allVariants = new StringBuilder();//для сохранения всех вариантов
        double[] chiSquares = new double[SMALL_ALPHA_RUS.size()];//для хранения значений
        String[] decipheredMessages = new String[SMALL_ALPHA_RUS.size()];//для хранения расшифрованных текстов

        System.out.println("=== ПЕРЕБОР ВСЕХ СДВИГОВ ===");

        for (int offset = 0; offset < SMALL_ALPHA_RUS.size(); offset++) {
            String decipheredMessage = decipher(lowerCaseText, offset);
            decipheredMessages[offset] = decipheredMessage;

            // Считаем наблюдаемые частоты
            long[] observedFrequencies = countAllCharacters(decipheredMessage);

            // Рассчитываем частоту
            double chiSquare = calculateChiSquare(observedFrequencies, lowerCaseText.length());
            chiSquares[offset] = chiSquare;

            // Выводим результат
            String preview = decipheredMessage.length() > 70
                    ? decipheredMessage.substring(0, 70) + "..."
                    : decipheredMessage;

            System.out.printf("Шаг %2d: частота = %.2f%% Текст: %s%n",
                    offset, chiSquare, preview);

            // Сохраняем в общую строку
            allVariants.append("=== СДВИГ ").append(offset).append(" (частота: ")
                    .append(String.format("%.2f%%", chiSquare)).append(") ===\n")
                    .append(decipheredMessage).append("\n");
        }

        // Находим наиболее вероятный сдвиг (минимальный хи-квадрат)
        int probableOffset = findBestOffset(chiSquares);
        String bestResult = decipheredMessages[probableOffset];

        System.out.println("=".repeat(70));
        System.out.println("Наиболее вероятное смещение: " + probableOffset);
        System.out.println("=".repeat(70));
        System.out.println("Расшифрованное сообщение: " + bestResult);
        // Сохраняем все варианты в отдельный файл
//        String allVariantsFilename = outputFilename.replace(".txt", "_all_variants.txt");
//        fileService.writeFile(allVariants.toString(), allVariantsFilename);
//        System.out.println("Все варианты сохранены в: " + allVariantsFilename);

        return bestResult;

    }

    private static String decipher(String text, int offset) {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            int index = SMALL_ALPHA_RUS.indexOf(c);
            if (index != -1) {
                // Сдвигаем индекс с учетом размера алфавита
                int newIndex = (index - offset + SMALL_ALPHA_RUS.size()) % SMALL_ALPHA_RUS.size();
                result.append(SMALL_ALPHA_RUS.get(newIndex));
            } else {
                // Если символ не найден в алфавите, оставляем как есть
                result.append(c);
            }
        }
        return result.toString();
    }

    private static long[] countAllCharacters(String input) {
        long[] frequencies = new long[SMALL_ALPHA_RUS.size()];

        for (char c : input.toCharArray()) {
            int index = SMALL_ALPHA_RUS.indexOf(c);
            if (index != -1) {
                frequencies[index]++;
            }
            // Игнорируем символы не из алфавита
        }

        return frequencies;
    }

    private static double calculateChiSquare(long[] observed, int textLength) {
        double chiSquare = 0;

        for (int i = 0; i < SMALL_ALPHA_RUS.size(); i++) {
            double expected = RULETTERS.get(i) * textLength / 100.0;
            if (expected > 0) {
                double diff = observed[i] - expected;
                chiSquare += (diff * diff) / expected;
            }
        }

        return chiSquare;
    }

    private static int findBestOffset(double[] chiSquares) {
        int bestOffset = 0;
        double minChi = chiSquares[0];

        for (int i = 1; i < chiSquares.length; i++) {
            if (chiSquares[i] < minChi) {
                minChi = chiSquares[i];
                bestOffset = i;
            }
        }

        return bestOffset;
    }


// --------------- BRUTEFORCE

} //class BruteForce
//todo запись номера шага и расщифровки в мапу (Map)
        /*
        1. берём репрезентативный текст иразбиваем его на Set из слов
        2. заводим переменную, которая будет содежать максимальное количество совпадений (например, int а = 0)
        3. заводим переменную, которая бдует хранить самый достоверный шаг
        4. бегаем по мапе из вариантов перевода
            4.1. рабиваем перевод на сет слов
            4.2 считаем кол-во совпадений слов в репрезентативным сетом (для этого нужна локальная переменная внутри цикла)
            4.3. Если полеченное кол-во содпадений больше, чем значение, хранящееся в переменной а,
            то перезаписываем это значение в переменную а и записываем номер шага в переменную, которая хранит самый достоверный шаг
        5. вытаскиваем из мапы с вариантами перевода нужный нам перевод по "ключу" (номеру шага)
         */

//void bruteForce

//Расшифровка методом brute force (перебором всех возможных сдвигов);
// На входе - адрес зашифрованного файла, (опционально) адрес файла с текстом
// который является примером текста что был зашифрован
// (например другой труд того же автора) и адрес файла
// который должен содержать расшифрованный текст.

 //-----------------------------

/*  ----- //шифр на три словаря
        char[] smallAlphaRus = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
                'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
                'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
        char[] upAlphaRus = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М',
                'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'} ;
        char[] digits = {
                '0','1','2','3','4','5','6','7','8','9',
                '.', ',', '-', '/', '|', '\'', '«', '»', '"', ':', '!', '?', ' ',
                '\0'};
        //char[] newAllR = new char[smallAlphaRus.length + upAlphaRus.length + digits.length];
        //System.arraycopy(smallAlphaRus, 0, newAllR, 0, smallAlphaRus.length);
        //System.arraycopy(upAlphaRus, 0, newAllR, smallAlphaRus.length, upAlphaRus.length);
        //System.arraycopy(digits, 0, newAllR, smallAlphaRus.length + upAlphaRus.length, digits.length);
        //System.out.println(Arrays.toString(newAllR));
       int shift = 3;
        String input = "Зшгом45"; // "Ехали12" //
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < newAllR.length; j++) {
                if (input.charAt(i) == newAllR[j]) {//перебор эл-тов
                    result.append(newAllR[(j-shift) % newAllR.length]);//запись значения
                }
                System.out.println("Шаг: " + newAllR[j] + "Шифр: " + result);
            }
        }//System.out.println(result); */ //--------------------

