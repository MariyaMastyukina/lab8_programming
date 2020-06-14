package Server.Collection;

import java.util.Comparator;

public class XComparator implements Comparator<City> {
@Override
public int compare(City city1, City city2) {
        return city1.getCoordinates().getX().compareTo(city2.getCoordinates().getX());
        }
        }
