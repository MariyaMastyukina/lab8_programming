package Server.Collection;

import java.util.Comparator;

public class IDComparator implements Comparator<City> {
    @Override
    public int compare(City city1, City city2) {
        return city1.getIdOfCity().compareTo(city2.getIdOfCity());
    }
}
