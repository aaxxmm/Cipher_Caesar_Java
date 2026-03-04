

package ru.javarush.kozlov.caesarcipher.attack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import static ru.javarush.kozlov.caesarcipher.cipher.CipherAlphavit.SMALL_ALPHA_RUS;

public class StatisticalAnalyzer { //Для статистического анализа при расшифровке.

    private Set<String> dictionary = new HashSet<>();// Словарь из файла

    public StatisticalAnalyzer(Set<String> dictionary) {
        this.dictionary = dictionary;
    }
    public String statisticalAnalysis(String encryptedText, String representativeText) throws IOException {
        // Логика статистического анализа для определения сдвига

//void statisticalAnalysis (String inputFile, String outputFile,String optionalSampleFile)

// ----- Криптоанализ методом статистического анализа ----

        ru.javarush.kozlov.caesarcipher.attack.BruteForce service = new ru.javarush.kozlov.caesarcipher.attack.BruteForce();

        // 1. Загружаем словарь из файла
        loadDictionary();
        // 3. Пробуем все возможные сдвиги
        if (encryptedText == null) {
            throw new RuntimeException("Файл не найден");
        }

        return tryAllShifts(encryptedText.toLowerCase());

    } //statisticalAnalysis

    //Загружает словарь из resources/input.txt

    public void loadDictionary() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("input.txt");

        if (inputStream == null) {
            throw new IOException("Файл input.txt не найден в resources!");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            dictionary = reader.lines()
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .map(String::toLowerCase)
                    .map(this::process)
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toSet());

            //System.out.println("Загружено слов в словаре: " + dictionary.size());
            // Для отладки можно вывести первые 20 слов
            //dictionary.stream().limit(20).forEach(w -> System.out.println("  - " + w));
        }
    }

    //все возможные сдвиги и ищет совпадения со словарём
    /// @return

    public String tryAllShifts(String text) {
        System.out.println("\n=== ПОИСК РАСШИФРОВКИ ===");
        System.out.println("Исходный текст: " + text);
      //  System.out.println("Размер алфавита: " + SMALL_ALPHA_RUS.size());

        int bestMatchCount = 0;
        String bestDecryption = "";
        int bestStep = 0;

        for (int step = 1; step <= SMALL_ALPHA_RUS.size(); step++) {
            String decrypted = decrypt1(text, step);

            // Разбиваем расшифрованный текст на слова
            String[] words = decrypted.split("[^а-яё]+"); // разбиваем по не-буквам

            // Считаем, сколько слов из расшифровки есть в словаре
            int matchCount = 0;
            Set<String> matchedWords = new HashSet<>();

            for (String word : words) {
                if (word.length() >= 2 && dictionary.contains(word)) { // слова от 2 букв
                    matchCount++;
                    matchedWords.add(word);
                }
            }

            System.out.printf("Шаг %2d: Совпадений: %2d | Расшифровка: %s%n",
                    step, matchCount, truncate(decrypted, 50));

            // Запоминаем лучший результат
            if (matchCount > bestMatchCount) {
                bestMatchCount = matchCount;
                bestDecryption = decrypted;
                bestStep = step;
            }
        }
       
        System.out.println("\n=== ЛУЧШАЯ РАСШИФРОВКА ===");
        System.out.println("Шаг: " + bestStep);
        System.out.println("Совпадений со словарём: " + bestMatchCount + " слова");
        System.out.println("Расшифрованный текст: " + bestDecryption);

        // Разбиваем на слова для наглядности
        String[] words = bestDecryption.split("[^а-яё]+");
        System.out.println("Слова из словаря: " +
                Arrays.stream(words)
                        .filter(w -> w.length() >= 2 && dictionary.contains(w))
                        .collect(Collectors.joining(", ")));
        return bestDecryption;
    }

    // Дешифровка текста с заданным сдвигом

    private String decrypt1(String text1, int step) {
        StringBuilder result = new StringBuilder();
        for (char c : text1.toCharArray()) {
            int index = SMALL_ALPHA_RUS.indexOf(c);
            if (index >= 0) {
                int newIndex = (index - step) % SMALL_ALPHA_RUS.size();
                if (newIndex < 0) {
                    newIndex += SMALL_ALPHA_RUS.size();
                }
                result.append(SMALL_ALPHA_RUS.get(newIndex));
            } else {
                result.append(c); // если символ не найден в алфавите, оставляем как есть
            }
        }
        return result.toString();
    }

    // Очистка слова от знаков препинания

    String process(String word1) {
        return word1.chars()
                .filter(Character::isLetterOrDigit)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // Обрезает строку до указанной длины для вывода

    String truncate(String s, int maxLength) {
        if (s.length() <= maxLength) return s;
        return s.substring(0, maxLength) + "...";
    }


} //class StatisticalAnalyzer


