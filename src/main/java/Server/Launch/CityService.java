package Server.Launch;

import Server.Model.City;
import Utils.ConnectionUtils.Request;
import Utils.DBUtils.CityDAO;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class CityService {
    public Request getTable() throws SQLException {
        return new Request("", CityDAO.getAllCities(), "Success");
    }

    public Request clear(String user) throws SQLException {
        String response = CityDAO.deleteAll(user);
        if (response.isEmpty()) {
            return new Request("Все удалено!", new CopyOnWriteArrayList<>(), "Success");
        }
        return new Request("Команда clear выполнена, но было отказано в доступе к объектам с именами и id: \n" + response, CityDAO.getAllCities(), "Success");
    }

    public Request removeLast(String user) throws SQLException {
        long id = getIdLast(user);
        if (id != 0) {
            CityDAO.removeById(id, user);
            return new Request("Элемент удален!", CityDAO.getAllCities(), "Success");
        }
        return new Request("Удаление не выполнено! Не создано ни одного города.", null, "Error");
    }

    public Request removeByMetersAboveSeaLevel(int metersAboveSeaLevel, String login) throws SQLException {
        String response = CityDAO.removeByMetersAboveSeaLevel(metersAboveSeaLevel, login);
        if (response.isEmpty())
            return new Request("Все города с metersAboveSeaLevel=" + metersAboveSeaLevel + "удалены!", CityDAO.getAllCities(), "Success");
        return new Request("Команда remove_all_by_meters_above_sea_level выполнена, но вы не смогли удалить следующие объекты из-за отказа в доступе:\n" + response, CityDAO.getAllCities(), "Success");

    }

    public Request groupCountingByPopulation() throws SQLException {
        CopyOnWriteArrayList<City> list = CityDAO.getAllCities();
        if (list.size() == 0) {
            return new Request("Команда group_counting_by_population не выполнена. Коллекция пуста, групировка элементов невозможна", null, "Error");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Команда group_counting_by_population выполнена. Количество элементов полученных групп элементов коллекции:").append("\n");
            Map<Integer, Long> collectionPerPopulation = list.stream().collect(groupingBy(City::getPopulation, counting()));
            sb.append(collectionPerPopulation.toString());
            return new Request(sb.toString(), null, "Success");

        }
    }

    public Request sort(Comparator<City> comparator) throws SQLException {
        CopyOnWriteArrayList<City> list = CityDAO.getAllCities();
        if (list.size() == 0) {
            return new Request("Команда sort не выполнена. Коллекция пуста, сортировка невозможна", null, "Error");
        } else {
            StringBuilder sb = new StringBuilder();
            list.sort(comparator);
            sb.append("Команда sort выполнена.").append("\n");
            return new Request(sb.toString(), list, "Success");
        }
    }

    public Request history(ControlUnit controlUnit) {
        String response = controlUnit.getListCommand();
        if (response == null)
            new Request("Команда history не выполнена. Вы использовали меньше 8 команд", null, "Error");

        return new Request(response, null, "Success");
    }

    public Request add(City city, String login) throws SQLException {
        CityDAO.createCity(city, false, login);
        return new Request("Город создан!", CityDAO.getAllCities(), "Success");

    }

    public Request removeById(long id, String user) throws SQLException {
        String response = CityDAO.removeById(id, user);
        if (response == null)
            return new Request("Город с id=" + id + " успешно удален", CityDAO.getAllCities(), "Success");
        return new Request(response, CityDAO.getAllCities(), "Error");
    }

    private long getIdLast(String user) throws SQLException {
        CopyOnWriteArrayList<City> list = CityDAO.getAllCities();
        long max = 0;
        for (City c : list) {
            if (c.getId() > max && c.getUser().equals(user)) {
                max = c.getId();
            }
        }
        return max;
    }

    public Request update(City newCity) throws SQLException {
        String response = CityDAO.updateId(newCity.getId(), newCity);
        if (response == null)
            return new Request("Команда update выполнена. Значение элемента коллекции с id " + newCity.getId() + " обновлено, введите команду \"show\", чтобы увидеть содержимое коллекции", CityDAO.getAllCities(), "Success");
        return new Request(response, CityDAO.getAllCities(), "Error");
    }
}
