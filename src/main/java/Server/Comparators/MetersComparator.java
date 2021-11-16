package Server.Comparators;

import Server.Collection.City;

import java.util.Comparator;

public class MetersComparator implements Comparator<City> {
    @Override
    public int compare(City city1, City city2) {
        return city1.getMetersAboveSeaLevel().compareTo(city2.getMetersAboveSeaLevel());
    }
}
