package Server.Comparators;

import Server.Collection.City;

import java.util.Comparator;

public class DateComparator implements Comparator<City> {
    @Override
    public int compare(City city1, City city2) {
        return city1.getCreationDate().compareTo(city2.getCreationDate());
    }
}
