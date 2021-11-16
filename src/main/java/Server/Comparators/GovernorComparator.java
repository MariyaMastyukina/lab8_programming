package Server.Comparators;

import Server.Collection.City;

import java.util.Comparator;

public class GovernorComparator implements Comparator<City> {
    @Override
    public int compare(City city1, City city2) {
        return city1.getGovernor().getName().compareTo(city2.getGovernor().getName());
    }
}
