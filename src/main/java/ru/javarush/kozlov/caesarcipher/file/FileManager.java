

package ru.javarush.kozlov.caesarcipher.file;

import ru.javarush.kozlov.caesarcipher.exception.FileProcessingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager { //Отвечает за чтение и запись файлов.

    public List<String> readFile(String filePath) {
        // Читаете файл и возвращаете строку с содержимым

            //Path path = Paths.get("input.txt");// get(filePath)
        List<String> lines = new ArrayList<>();
        // Используем try-with-resources для автоматического закрытия
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath),
                StandardCharsets.UTF_8)) {
            String line;  // Построчное чтение
            while ((line = reader.readLine()) != null) {
                lines.add(line);  // Сохраняем строку
                //System.out.println(line);
            }
            return lines;
        } catch (IOException | InvalidPathException ex) {
            System.out.println("Чтение файла");
            throw  new FileProcessingException(ex.getMessage(), ex);
        }

    }

    public void writeFile(String content, String filePath) {
        // Записываете content в файл
        try { Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING); // Запись строки
            //System.out.println("Файл успешно записан");
        }
        catch(IOException ex){
            throw new FileProcessingException("Не удалось записать файл: " + ex.getMessage());
        }
        //System.out.println("Логика записи файла");

    }

    // Проверка существования файла
    public boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    //метод для записи списка строк
    public void writeFile(String filePath, List<String> lines) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8)))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException ex) {
            throw new FileProcessingException("Не удалось записать файл: " + filePath, ex);
        }
    }

    public void appendToFile(String filePath, String line) throws FileProcessingException {
        try {
            Files.write(Paths.get(filePath),
                    (line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FileProcessingException("Не удалось добавить данные в файл: " + filePath, e);
        }
    }
} //class FileManager

// -----------------
//FileReader открывает файл для чтения символов.
//BufferedReader оборачивает FileReader для буферизации, ускоряя чтение.
//reader.readLine() возвращает null, когда файл заканчивается.
//Блок try-with-resources гарантирует закрытие потока, даже если возникнет ошибка.


