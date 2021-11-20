package Server.Comparators;

import Server.Model.City;

import java.util.Comparator;

public class GovernmentComparator implements Comparator<City> {
    @Override
    public int compare(City city1, City city2) {
        return city1.getGovernment().compareTo(city2.getGovernment());
    }
}