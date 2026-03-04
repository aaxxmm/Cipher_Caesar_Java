
package ru.javarush.kozlov.caesarcipher.file;
import ru.javarush.kozlov.caesarcipher.cipher.Cipher;
import ru.javarush.kozlov.caesarcipher.validation.Validator;
import java.util.List;

public class CipherCoder {
    //вынесено как константы
    private Cipher caesarCipher;  //шифратор
    private Validator validator;  //проверка
    private FileManager fileManager;  // запись/чтение файла

    public CipherCoder()  {
        this.caesarCipher = new Cipher();
        this.validator = new Validator();
        this.fileManager = new FileManager();
    } //где лежит файл
    public void encrypt(String inputFileName, String outputFileName, int key) {
        validator.validateForReading(inputFileName); //проверка файлов
        fileManager.writeFile("", outputFileName);
        validator.validateForWriting(outputFileName);
//считывем файл
        List<String> sourcelines = fileManager.readFile(inputFileName);
        for (String sourceline: sourcelines) { //для каждой строки шифруем
            String encryptedLine = caesarCipher.encrypt(sourceline, key);//используем шифр цезаря
            fileManager.writeFile("", outputFileName);
            fileManager.appendToFile(outputFileName, encryptedLine);//записываем в файл
        }
    }

    public void decrypt(String inputFileName, String outputFileName, int key) {
        validator.validateForReading(inputFileName);
        fileManager.writeFile("", outputFileName);
        validator.validateForWriting(outputFileName);

        List<String> sourcelines = fileManager.readFile(inputFileName);
        for (String sourceline: sourcelines) {
            String decryptedLine = caesarCipher.decrypt(sourceline, key);
            fileManager.appendToFile(outputFileName, decryptedLine);
        }
    }
} // class CipherCoder

