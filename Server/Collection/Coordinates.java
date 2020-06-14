package Server.Collection;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс координат со свойствами x y.
 */

public class Coordinates implements Serializable{
    /** Поле координата X*/
    private float x; //Значение поля должно быть больше -375
    /** Поле координата Y*/
    private int y; //Значение поля должно быть больше -966
    /** Список текущих координат*/
    private List<String> args;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param args- список координат
     */
    public Coordinates(List<String> args){
        this.args=args;
        x=Float.valueOf(args.get(0));
        y=Integer.valueOf(args.get(1));
    }
    /**
     * Функция получения значения поля {@link Coordinates#x}
     * @return возвращает id города
     */
    public Float getX(){
        return this.x;
    }
    /**
     * Функция получения значения поля {@link Coordinates#y}
     * @return возвращает id города
     */
    public Integer getY(){
        return this.y;
    }
    /**
     * Функция переопределения метода toString
     * @return объект в строковом представлении
     */
    @Override
    public String toString() {
        return "x=" + this.getX()+"\n" +
                "y=" + this.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(that.getX(), x) == 0 &&
                y == that.getY();
    }

}
