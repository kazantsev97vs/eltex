package ru.eltex.app.reflexio_mysql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reflexio {
    // Класс получаемого объекта
    private Class objectClass;
    // Название класса объекта
    private String objectClassName;
    // Поля объекта
    private List<Field> fields = new ArrayList<>();
    // Родители объекта
    private List <Class> parentsClasses = new ArrayList<>();

    public Reflexio(Object object) {
        // Получаемый объект
        this.objectClass = object.getClass();
        this.objectClassName = objectClass.getSimpleName();
        this.setFields();

        System.out.print("Object:");
        System.out.println(object);
        System.out.println("\nobjectClass:");
        System.out.println(objectClass);
        System.out.println("");
    }

    public Class getObjectClass() {
        return objectClass;
    }

    public String getObjectClassName() {
        return objectClassName;
    }

    public List<Class> getParentsClasses() {
        return parentsClasses;
    }

    private void setFields() {
        // Поля текущего класса
        fields.addAll(Arrays.asList(objectClass.getDeclaredFields()));
        Class parentClass = objectClass;

        while (
                (parentClass = parentClass.getSuperclass()) != null // пока есть родитель
                        && parentClass != Object.class // и этот родитель не Object
        ) {
            // Запоминаем класс родителя
            parentsClasses.add(parentClass);
            // Записываем все поля
            fields.addAll(Arrays.asList(parentClass.getDeclaredFields()));
        }
    }

    public List<Field> getFields() {
        return fields;
    }
}
