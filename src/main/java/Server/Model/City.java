package Server.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


public class City implements Serializable {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double area; //Значение поля должно быть больше 0, Поле не может быть null
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    private Integer metersAboveSeaLevel;
    private Boolean capital; //Поле может быть null
    private Climate climate; //Поле может быть null
    private Government government; //Поле может быть null
    private Human governor; //Поле может быть null
    private String user;

    public long getId() {
        return id;
    }

    public Integer getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public Integer getPopulation() {
        return population;
    }

    public String getName() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public void setMetersAboveSeaLevel(Integer metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    public List<String> getRow() {
        return List.of(Long.toString(id),
                name,
                getCoordinates().getX().toString(),
                getCoordinates().getY().toString(),
                creationDate.toString(),
                area.toString(),
                population.toString(),
                metersAboveSeaLevel.toString(),
                capital.toString(),
                climate.name(),
                government.name(),
                governor.getName(),
                user);
    }

    @Override
    public String toString() {
        return "City:id=" + this.id + ", name='" + this.name + '\'' + ", coordinates=" + this.coordinates + ", creationDate=" + this.creationDate + ", area=" + this.area + " , population=" + this.population + ", metersAboveSeaLevel=" + this.metersAboveSeaLevel + ", capital=" + this.capital + ", government=" + this.government + ", governor=" + this.governor + ", climate=" + this.climate;
    }
}
