

package ru.javarush.kozlov.caesarcipher.validation;
import ru.javarush.kozlov.caesarcipher.exception.CaesarCodingException;
import ru.javarush.kozlov.caesarcipher.exception.FileProcessingException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.util.List;

public class Validator { //Валидация входных данных,
    // таких как существование файла, допустимость ключа.
// Проверка ключа (0 до размера алфавита)

    private static final List<String> FORBIDDEN_DIRS_FILES =
            List.of(".bash_history", ".bash_profile", "etc", "proc");//mac

    public boolean isValidKey(int key,String alphabet) throws InvalidKeyException {
        // Проверка ключа
        if (key < 0) {
            throw new CaesarCodingException("Ключ не может быть отрицательным: " + key);
        }
        if (alphabet.length() != 40) {
            throw new CaesarCodingException("Ключ слишком большой: " + key + ", максимум: " + (alphabet.length() - 1));
        }
        return true;
    }

    public boolean isFileExists(String filePath) {
        // Проверка существования файла

        return Files.exists(Paths.get(filePath));
    }

    public boolean validateForWriting(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            if (Files.isDirectory(path)) {//папка?
                throw new FileProcessingException("File " + path + "  is directory");
            }
            if (!Files.isWritable(path)) {//файл доступен?
                throw new FileProcessingException("File " + path + " is not accessible for writing");
            }
        } else {// Проверяем, можно ли создать файл в этой директории
            Path parent = path.getParent();
            if (parent != null && !Files.exists(parent)) {
                throw new FileProcessingException("Директория не существует: " + parent);
            }
            if (parent != null && !Files.isWritable(parent)) {
                throw new FileProcessingException("Нет прав на запись в директорию: " + parent);
            }
        }
        return true;
    }

    public boolean validateForReading(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.notExists(path)) {//файл есть?
            throw new FileProcessingException("Файл не существует: " + filePath);
        }

        if (Files.isDirectory(path)) {//папка?
            throw new FileProcessingException("Указанный путь является папкой: " + filePath);
        }

        if (!Files.isReadable(path)) {//файл доступен?
            throw new FileProcessingException("Файл недоступен для чтения: " + filePath);
        }
        //System.out.println("Файл прошел проверку: " + filePath); // для отладки
        return true;
    }

    private Path validatePath(String filePath) {
        for (String pathPart : filePath.split(System.getProperty("file.separator"))) {
            if (FORBIDDEN_DIRS_FILES.contains(pathPart)) { //запрещеные папки
                throw new FileProcessingException("Path contains forbidden part + " + pathPart);
            }
        }
        try {
            Path path = Paths.get(filePath);
            return path;
        } catch (InvalidPathException ex) {
            throw new FileProcessingException("Invalid path.Reason: " + ex.getMessage());
        }

    }

} //class validation.Validator