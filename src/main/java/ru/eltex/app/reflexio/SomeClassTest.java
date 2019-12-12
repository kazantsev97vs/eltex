package ru.eltex.app.reflexio;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SomeClassTest {

    public static void main(String[] args) {
        SomeClass someClass = new SomeClass("Anya");

        /**
         * 3 способа вывести полное имя класса вместе с пакетом
         * someClass.getClass()
         * SomeClass.class
         * Class.forName("ru.eltex.app.reflexio.SomeClass");
         *
         * Вывод:
         * class ru.eltex.app.reflexio.SomeClass
         */
        Class aClass = someClass.getClass();
        Class aClass2 = SomeClass.class;
        Class aClass3 = null;
        try {
            aClass3 = Class.forName("ru.eltex.app.reflexio.SomeClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("");
        System.out.println(someClass);
        System.out.println("");
        System.out.println(aClass);
        System.out.println("");
        System.out.println(aClass2);
        System.out.println("");
        System.out.println(aClass3);
        System.out.println("");

        SomeClass someClass2 = null;
        try {
            /**
             * Создание нового пустого объекта типа new SomeClass()
             */
            someClass2 = (SomeClass) aClass2.newInstance();
            System.out.println(someClass2);

            someClass.showName();
            someClass2.showName();
            someClass2.setName("Вадим");
            someClass.showName();
            someClass2.showName();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        /**
         * Получаем все текущие конструкторы
         */
        Constructor[] constructors = aClass.getConstructors();

        for (Constructor c :
                constructors) {
            System.out.println(c);
            System.out.println(c.getName());
            System.out.println(c.getModifiers());
        }


        System.out.println("");
        System.out.println("Methods:");
        Method[] methods = aClass.getMethods();
        for (Method m :
                methods) {
            System.out.println(m.getName());
        }
        System.out.println("");
        System.out.println("Declared methods:");
        Method[] methods2 = aClass2.getDeclaredMethods();
        for (Method m :
                methods2) {
            System.out.println(m.getName());
        }

        System.out.println("");
        try {
            methods2[0].invoke(someClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}

class SomeClass {
    private String name;

    public SomeClass() {
    }

    public SomeClass(String name) {
        this.name = name;
    }

    private String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void showName() {
        System.out.println(getName());
    }
}
