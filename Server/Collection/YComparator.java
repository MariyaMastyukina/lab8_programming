package Server.Collection;

import java.util.Comparator;

public class YComparator implements Comparator<City> {
    @Override
    public int compare(City city1, City city2) {
        return city1.getCoordinates().getY().compareTo(city2.getCoordinates().getY());
    }
}
