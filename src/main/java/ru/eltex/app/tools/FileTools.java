package ru.eltex.app.tools;

import java.io.*;

public class FileTools {

    public static String read (String filePath) {
        StringBuilder fileContents = new StringBuilder();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = null;

            while ((line = reader.readLine()) != null) {
                fileContents.append(line);
            }

            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContents.toString();
    }

    public static void write(String filePath, String content) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}