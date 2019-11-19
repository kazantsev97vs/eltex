package ru.eltex.app.tools;

public class Parser {

    /**
     * Преобразует массив данных в строку, разделенную предоставленным шаблоном
     * @param data - массив данных типа String
     * @param PATTERN - шаблон типа String
     * @return String
     */
    public static String convertToString (String[] data, final String PATTERN) {
        StringBuilder result = new StringBuilder(data[0]);

        for (int dataIndex = 1; dataIndex < data.length; dataIndex++) {
            result.append(PATTERN).append(data[dataIndex]);
        }

        return result.toString();
    }

    /**
     * Разбор преобразованной строки по предоставленному шаблону в массив
     * @param convertedData - строка
     * @param PATTERN - шаблон типа String
     * @return - массив данных типа String
     */
    public static String[] parseFromString(String convertedData, final String PATTERN) {
        return convertedData.split(PATTERN);
    }


}