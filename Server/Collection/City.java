package Server.Collection;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс города со свойствами id, name, coordiantes, creationDate, area, population, metersAboveSeaLevel, capital, climate, government, governor.
 */
public class City implements Serializable {
    /** Поле id*/
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /** Поле название*/
    private String name; //Поле не может быть null, Строка не может быть пустой
    /** Поле координаты*/
    private Coordinates coordinates; //Поле не может быть null
    /** Поле дата создания*/
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /** Поле площадь*/
    private Double area; //Значение поля должно быть больше 0, Поле не может быть null
    /** Поле население*/
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    /** Поле метры на уровнем моря*/
    private Integer metersAboveSeaLevel;
    /** Поле столица*/
    private Boolean capital; //Поле может быть null
    /** Поле климат*/
    private Climate climate; //Поле может быть null
    /** Поле правительство*/
    private Government government; //Поле может быть null
    /** Поле губернатор*/
    private Human governor; //Поле может быть null
    private String user;
    /** Список аргументов класса City */
     /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param args -список аргументов определенного объекта
     */
    public City(List<String> args) {
        if (args.size()==10) {
            this.name = args.get(0);
            this.coordinates = new Coordinates(new ArrayList<>(args.subList(1, 3)));
            this.creationDate = LocalDateTime.now();
            this.area = Double.parseDouble(args.get(3));
            this.population = Integer.parseInt(args.get(4));
            this.metersAboveSeaLevel = Integer.parseInt(args.get(5));
            this.capital = Boolean.parseBoolean(args.get(6));
            if (args.get(7) == null) this.climate = null;
            else this.climate = Climate.valueOf(args.get(7));
            if (args.get(8) == null) this.government = null;
            else this.government = Government.valueOf(args.get(8));
            this.governor = new Human(args.get(9));
        }
        else if (args.size()==12){
            this.id=Long.parseLong(args.get(0));
            this.name = args.get(1);
            this.coordinates = new Coordinates(new ArrayList<>(args.subList(2, 4)));
            this.creationDate = Timestamp.valueOf(args.get(4)).toLocalDateTime();
            this.area = Double.parseDouble(args.get(5));
            this.population = Integer.parseInt(args.get(6));
            this.metersAboveSeaLevel = Integer.parseInt(args.get(7));
            this.capital = Boolean.parseBoolean(args.get(8));
            if (args.get(9) == null) this.climate = null;
            else this.climate = Climate.valueOf(args.get(9));
            if (args.get(10) == null) this.government = null;
            else this.government = Government.valueOf(args.get(10));
            this.governor = new Human(args.get(11));
        }
    }
    /**
     * Функция получения значения поля {@link City#id}
     * @return возвращает id города
     */
    public long getIdOfCity(){
        return id;
    }
    /**
     * Функция получения значения поля {@link City#metersAboveSeaLevel}
     * @return возвращает метры над уровнем моря
     */
    public Integer getMetersAboveSeaLevel(){
        return metersAboveSeaLevel;
    }
    /**
     * Функция переопределения метода toString
     * @return объект в строковом представлении
     */
    @Override
    public String toString() {
        return "City:"+"\n"+
                "id=" + this.id +"\n"+
                "name='" + this.name +"\n" +
                "coordinates:" + this.coordinates.toString() +"\n"+
                "creationDate=" + this.creationDate +"\n"+
                "area=" + this.area +"\n"+
                "population="+this.population+"\n"+
                "metersAboveSeaLevel=" + this.metersAboveSeaLevel +"\n"+
                "capital=" + this.capital +"\n"+
                "government=" + this.government +"\n"+
                "governor=" + this.governor +"\n"+
                "climate=" + this.climate;
    }
    /**
     * Функция получения значения поля {@link City#population}
     * @return возвращает население города
     */
    public Integer getPopulation(){
        return population;
    }
    /**
     * Функция получения значения поля {@link City#name}
     * @return имя города
     */
    public String getNameCity(){
        return name;
    }

    public Boolean getCapital() {
        return capital;
    }

    public Climate getClimate() {
        return climate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Double getArea() {
        return area;
    }

    public Government getGovernment() {
        return government;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Human getGovernor() {
        return governor;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setId(long id) {
        this.id = id;
    }
    public Object[] getDataRow(){
        return new Object[]{id, name,coordinates.getX(),coordinates.getY(),creationDate,area,population,metersAboveSeaLevel,capital,climate,government,governor};
    }
}
