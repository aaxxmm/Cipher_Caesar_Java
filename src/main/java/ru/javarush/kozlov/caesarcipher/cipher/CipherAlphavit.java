
package ru.javarush.kozlov.caesarcipher.cipher;

import java.util.Arrays;
import java.util.List;

public class CipherAlphavit {
    // Алфавит
    public static final List<Character> SMALL_ALPHA_RUS = Arrays.asList(
            // буквы 33
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м',
            'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            // Символы 14
            '.', ',', '-', '/', '|', '\'',
            '«', '»', '"', ':', '!', '?', ' ', '\0'
    );

    // ---------------

    // Словари делаем константами класса
    private static final String UP_ALPHA_RUS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String SMALL_ALPHA_RUS1 = "абвгдеёжзийклмнопрстуфхцчшщъыьэя";
    private static final String UP_ALPHA_ENG = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SMALL_ALPHA_ENG = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789.,'«»\":\' \'!?";

    // Алфавит для дешифровки
 /*

    private static final List<Character> SMALL_ALPHA_ENG = Arrays.asList(
            // буквы 26
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            // Символы 14
            ".", ",", "-", "/", "|", "\\",   // обратный слеш экранирован
            "«", "»", "\"", ":", "!", "?", " ", "\0"
    );*/

    // массив, содержащий вероятности нахождения Частоты букв русского языка (в процентах)
    public static final List<Double> englishLetters = Arrays.asList( // букв 26
            0.073, // А,
            0.009, 0.030, 0.044, 0.130, 0.028, 0.016, 0.035, 0.074,
            0.002, 0.003, 0.035, 0.025, 0.078, 0.074, 0.027, 0.003,
            0.077, 0.063, 0.093, 0.027, 0.013, 0.016, 0.005, 0.019,
            0.001, // Z,
            // Символы 14
            0.50,  // .
            0.80,  // ,
            0.30,  // -
            0.05,  // /
            0.01,  // |
            0.02,  // '
            0.06,  // «
            0.06,  // »
            0.08,  // "
            0.10,  // :
            0.07,  // !
            0.08,  // ?
            17.50, // (пробел)
            0.03   // \0
    );
    // % частота использования букв в тексте
    public static final List<Double> RULETTERS = Arrays.asList( // буквы 33
            8.66, // А,
            1.51, 4.19, 1.41, 2.56, 8.10, 4.05, 0.78, 1.81, 7.45,
            1.31, 3.47, 4.32, 3.29, 6.35, 9.28, 3.35, 5.53, 5.45, 6.30, 2.90, 0.40,
            0.92, 0.52, 1.27, 0.77, 0.49, 0.04, 2.11, 1.90, 0.17, 1.03,
            2.22, // Я,
            // Символы 14
            0.50,  // .
            0.80,  // ,
            0.30,  // -
            0.05,  // /
            0.01,  // |
            0.02,  // '
            0.06,  // «
            0.06,  // »
            0.08,  // "
            0.10,  // :
            0.07,  // !
            0.08,  // ?
            17.50, // (пробел)
            0.03   // \0
            );



    //словарь
    //String upAlphaRus = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    //String smallAlphaRus = "абвгдеёжзийклмнопрстуфхцчшщъыьэя";
   // String upAlphaEng = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   // String smallAlphaEng = "abcdefghijklmnopqrstuvwxyz";
   // String digits = "0123456789.,'«»\":\' \'!?";

} // class CipherAlphavit
