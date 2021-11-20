package Server.Model;

import java.io.Serializable;

public class Human implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой

    public Human(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
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
