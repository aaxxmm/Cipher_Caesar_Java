
package ru.javarush.kozlov.caesarcipher.cipher;

import static ru.javarush.kozlov.caesarcipher.cipher.CipherAlphavit.SMALL_ALPHA_RUS;

// определяем алфавит, в котором мы будем работать,а также методы для шифрования, расшифровки
public class Cipher { //Класс, реализующий функциональность шифра Цезаря и дешифровки

        //------------------------"шифрование")
        public String encrypt (String text,int key) {

            String lowerCaseString = text.toLowerCase();

            StringBuilder encryptedString = new StringBuilder();
            for (int i = 0; i < lowerCaseString.length(); i++) {
                for (int j = 0; j < SMALL_ALPHA_RUS.size(); j++) {
                    if (lowerCaseString.charAt(i) == SMALL_ALPHA_RUS.get(j)) {
                        encryptedString.append(SMALL_ALPHA_RUS.get((j + key) % SMALL_ALPHA_RUS.size()));
                    }
                }
            }
            System.out.print("Шифр: " + encryptedString);
            System.out.println();
            System.out.println("Введеные слова или буквы: " + lowerCaseString);
            System.out.println("шаг для ключа: " + key);
            return encryptedString.toString();//почему его?
        }//--------



    // --------- "расшифровка"
    public String decrypt(String text, int key) {

        String lowerCaseString = text.toLowerCase();

        StringBuilder decryptedString = new StringBuilder();
        for (char c : lowerCaseString.toCharArray()) {
            int index = SMALL_ALPHA_RUS.indexOf(c);
            if (index >= 0) {
                int newIndex = (index - key) % SMALL_ALPHA_RUS.size();
                if (newIndex < 0) {
                    newIndex += SMALL_ALPHA_RUS.size();
                }
                decryptedString.append(SMALL_ALPHA_RUS.get(newIndex));
            } else {
                decryptedString.append(c); // если символ не найден в алфавите, оставляем как есть
            }
        }
        System.out.print("расшифровка: " + decryptedString);
        System.out.println();
        System.out.println("Введеные слова или буквы: " + lowerCaseString);
        System.out.println("шаг для ключа: " + key);
       return decryptedString.toString();//почему его?
    }  //--------


} //class Cipher

