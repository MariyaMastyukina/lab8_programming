package Utils.DBUtils;

import Server.Model.*;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CityDAO {
    private static Connection connection;
    private static String collTable;

    public CityDAO(Connection connection, String collTable) {
        CityDAO.connection = connection;
        CityDAO.collTable = collTable;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public static void createCity(City city, boolean id, String user) throws SQLException {
        PreparedStatement pr;
        if (!id) {
            pr = connection.prepareStatement("INSERT INTO " + collTable + " VALUES ( DEFAULT ,?,?,?,?,?,?,?,?,?,?,?,?)");
        } else {
            pr = connection.prepareStatement("INSERT INTO " + collTable + " VALUES (" + city.getId() + ",?,?,?,?,?,?,?,?,?,?,?,?)");
        }
        pr.setString(1, city.getName());
        pr.setFloat(2, city.getCoordinates().getX());
        pr.setInt(3, city.getCoordinates().getY());
        pr.setTimestamp(4, Timestamp.valueOf(city.getCreationDate()));
        pr.setDouble(5, city.getArea());
        pr.setInt(6, city.getPopulation());
        pr.setInt(7, city.getMetersAboveSeaLevel());
        pr.setBoolean(8, city.getCapital());
        pr.setString(9, city.getClimate().toString());
        pr.setString(10, city.getGovernment().toString());
        pr.setString(11, city.getGovernor().toString());
        pr.setString(12, user);
        pr.execute();
        pr.close();
        if (!id) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + collTable + " WHERE name ='" + city.getName() + "'");
            rs.next();
            city.setId(rs.getLong(1));
        }
    }

    public static CopyOnWriteArrayList<City> getAllCities() throws SQLException {
        CopyOnWriteArrayList<City> list = new CopyOnWriteArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + collTable);
        while (rs.next()) {
            City city = new City();
            city.setId(rs.getLong(1));
            city.setName(rs.getString(2));
            city.setCoordinates(new Coordinates(List.of(String.valueOf(rs.getFloat(3)), String.valueOf(rs.getInt(4)))));
            city.setCreationDate(rs.getTimestamp(5).toLocalDateTime());
            city.setArea(rs.getDouble(6));
            city.setPopulation(rs.getInt(7));
            city.setMetersAboveSeaLevel(rs.getInt(8));
            city.setCapital(rs.getBoolean(9));
            city.setClimate(Climate.valueOf(rs.getString(10)));
            city.setGovernment(Government.valueOf(rs.getString(11)));
            city.setGovernor(new Human(rs.getString(12)));
            city.setUser(rs.getString(13));
            list.add(city);
        }
        statement.close();
        return list;
    }

    public static String deleteAll(String user) throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder sb = new StringBuilder();
        statement.executeUpdate("DELETE FROM " + collTable + " WHERE login ='" + user + "'");
        ResultSet rs = statement.executeQuery("SELECT * FROM " + collTable);
        while (rs.next()) {
            sb.append(rs.getString(2) + ":id-" + rs.getLong(1)).append("\n");
        }
        statement.close();
        return sb.toString();
    }

    public static String removeById(Long id, String user) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("Select * from " + collTable + " WHERE id = " + id);
        if (!rs.next())
            return "Города с id=" + id + " не существует!";
        if (!rs.getString("login").equals(user))
            return "У вас нет прав на удлаение города с id=" + id;
        statement.executeUpdate("DELETE FROM " + collTable + " WHERE id = '" + id + "'");

        statement.close();
        return null;
    }

    public static String removeByMetersAboveSeaLevel(Integer metersAboveSeaLevel, String user) throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder sb = new StringBuilder();
        statement.execute("DELETE FROM " + collTable + " WHERE meters_above_sea_level ='" + metersAboveSeaLevel + "'AND login = '" + user + "'");
        ResultSet rs = statement.executeQuery("SELECT * FROM " + collTable + " WHERE meters_above_sea_level ='" + metersAboveSeaLevel + "'AND login != '" + user + "'");
        while (rs.next()) {
            sb.append(rs.getString(2) + ":id-" + rs.getLong(1)).append("\n");
        }
        statement.close();
        return sb.toString();
    }

    public static String updateId(long id, City city) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + collTable + " WHERE id = '" + id + "'");
        if (rs.next()) {
            if (!rs.getString("login").equals(city.getUser())) {
                statement.close();
                return "Извините, вы не имеете прав для замены этого объекта";
            } else {
                removeById(id, city.getUser());
                createCity(city, true, city.getUser());
            }
        } else {
            return "Элемента с таким id нет. Введите команду \"show\", чтобы увидеть элементы коллекции и их id.";
        }
        statement.close();
        return null;
    }
}
