package Server.Comparators;

import Server.Collection.City;

import java.util.Comparator;

/**
 * Класс сравнение объектов класса Server.Server.Server.Commands.AddCommand.City по имени, реализующий интерфейс Comparator
 */
public class NameComparator implements Comparator<City> {
    /**
     * Функция переопределения метода compare
     *
     * @return число
     */
    @Override
    public int compare(City city1, City city2) {
        return city1.getNameCity().compareTo(city2.getNameCity());
    }
}
