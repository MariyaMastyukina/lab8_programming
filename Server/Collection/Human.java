package Server.Collection;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс человека со свойством name.
 */

public class Human implements Serializable{
    /** Поле имя человека*/
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param name- имя человека
     */
    public Human(String name){
        this.name=name;
    }
    /**
     * Функция переопределения метода toString
     * @return объект в строковом представлении
     */
    @Override
    public String toString(){
        return this.name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return name.equals(human.getName());
    }

}
