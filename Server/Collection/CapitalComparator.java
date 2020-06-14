package Server.Collection;

import java.util.Comparator;

public class CapitalComparator implements Comparator<City> {
    @Override
    public int compare(City city1, City city2) {
        return city1.getCapital().compareTo(city2.getCapital());
    }
}