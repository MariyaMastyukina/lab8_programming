package Server.Comparators;

import Server.Collection.City;

import java.util.Comparator;

public class UserComparator implements Comparator<City> {
    @Override
    public int compare(City city1, City city2) {
        return city1.getUser().compareTo(city2.getUser());
    }
}
