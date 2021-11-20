package Server.Comparators;

import Server.Model.City;

import java.util.Comparator;

public class IDComparator implements Comparator<City> {
    @Override
    public int compare(City city1, City city2) {
        return Long.compare(city1.getId(), city2.getId());
    }
}
