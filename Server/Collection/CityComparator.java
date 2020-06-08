package Server.Collection;

import java.util.Comparator;

/**
 * Класс сравнение объектов класса Server.Server.Server.Commands.AddCommand.City по населению, реализующий интерфейс Comparator
 */
public class CityComparator implements Comparator<City> {
    /**
     * Функция переопределения метода compare
     * @return число
     */
    @Override
    public int compare(City city1, City city2) {
        return city1.getPopulation().compareTo(city2.getPopulation());
        }
        }