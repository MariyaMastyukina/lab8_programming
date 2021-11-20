package Server.Model;

import java.io.Serializable;
import java.util.List;

public class Coordinates implements Serializable {
    private float x; //Значение поля должно быть больше -375
    private int y; //Значение поля должно быть больше -966
    private List<String> args;

    public Coordinates(List<String> args) {
        this.args = args;
        x = Float.parseFloat(args.get(0));
        y = Integer.parseInt(args.get(1));
    }

    public Float getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "x=" + this.getX() + "\n" +
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
