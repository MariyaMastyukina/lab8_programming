package Server.DBUtils;

import Server.Collection.City;
import Server.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionDB {
    private static Connection connection;
    private static String collTable;
    public CollectionDB(Connection connection,String collTable){
        this.connection=connection;
        this.collTable=collTable;
    }
    public static ResultSet getTable() throws SQLException {
        Statement statement=connection.createStatement();
        ResultSet rs=statement.executeQuery("select * from "+collTable);
        return rs;
    }
    public void closeConnection() throws SQLException {
        connection.close();
    }
    public static void insertColl(City city,boolean id,String user) throws SQLException {
        PreparedStatement pr;
        if (!id){
            pr=connection.prepareStatement("INSERT INTO "+collTable+" VALUES ( DEFAULT ,?,?,?,?,?,?,?,?,?,?,?,?)");
        }
        else{
            pr=connection.prepareStatement("INSERT INTO "+collTable+" VALUES ("+city.getIdOfCity()+",?,?,?,?,?,?,?,?,?,?,?,?)");
        }
        pr.setString(1,city.getNameCity());
        pr.setFloat(2,city.getCoordinates().getX());
        pr.setInt(3,city.getCoordinates().getY());
        pr.setTimestamp(4, Timestamp.valueOf(city.getCreationDate()));
        pr.setDouble(5,city.getArea());
        pr.setInt(6,city.getPopulation());
        pr.setInt(7,city.getMetersAboveSeaLevel());
        pr.setBoolean(8,city.getCapital());
        pr.setString(9,city.getClimate().toString());
        pr.setString(10,city.getGovernment().toString());
        pr.setString(11,city.getGovernor().toString());
        pr.setString(12,user);
        pr.execute();
        pr.close();
        if (!id){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + collTable+" WHERE name ='"+city.getNameCity()+"'");
            rs.next();
            city.setId(rs.getLong(1));
        }
    }
    public CopyOnWriteArrayList<City> loadListFromDB() throws SQLException {
        CopyOnWriteArrayList<City> list = new CopyOnWriteArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + collTable);
        while (rs.next()) {
            List<String> args = new ArrayList<>();
            args.add(String.valueOf(rs.getLong(1)));
            args.add(rs.getString(2));
            args.add(String.valueOf(rs.getFloat(3)));
            args.add(String.valueOf(rs.getInt(4)));
            args.add(String.valueOf(rs.getTimestamp(5)));
            args.add(String.valueOf(rs.getDouble(6)));
            args.add(String.valueOf(rs.getInt(7)));
            args.add(String.valueOf(rs.getInt(8)));
            args.add(String.valueOf(rs.getBoolean(9)));
            args.add(rs.getString(10));
            args.add(rs.getString(11));
            args.add(rs.getString(12));
            City city = new City(args);
            city.setUser(rs.getString(13));
            list.add(city);
        }
        statement.close();
        return list;
    }
    public static String clearDB(String user) throws SQLException {
        Statement statement=connection.createStatement();
        StringBuilder sb=new StringBuilder();
        statement.executeUpdate("DELETE FROM "+collTable+" WHERE login ='"+user+"'");
        ResultSet rs=statement.executeQuery("SELECT * FROM "+collTable);
        while(rs.next()){
            sb.append(rs.getString(2)+":id-"+rs.getLong(1)).append("\n");
        }
        statement.close();
        return sb.toString();
    }
     public static String removeIDBD(Long id,String user) throws SQLException {
        Statement statement=connection.createStatement();
        ResultSet rs=statement.executeQuery("SELECT login FROM "+collTable+" WHERE id ='"+id+"'");
        rs.next();
        if(!rs.getString("login").equals(user)) {
            statement.close();
            return "Извините, вы не имеете прав для удаления этого объекта";
        }
        statement.executeUpdate("DELETE FROM "+collTable+ " WHERE id = '"+id+"'");
         statement.close();
         return null;
    }
    public static String removeMASLBD(Integer meters_above_sea_level, String user) throws SQLException {
        Statement statement=connection.createStatement();
        StringBuilder sb=new StringBuilder();
        statement.execute("DELETE FROM "+collTable+" WHERE metersabovesealevel ='"+meters_above_sea_level+"'AND login = '"+user+"'");
        ResultSet rs=statement.executeQuery("SELECT * FROM "+collTable+" WHERE metersabovesealevel ='"+meters_above_sea_level+"'AND login != '"+user+"'");
        while(rs.next()){
            sb.append(rs.getString(2)+":id-"+rs.getLong(1)).append("\n");
        }
        statement.close();
        return sb.toString();
    }
    public static String UpdateIDDB(long id,City city) throws SQLException {
        Statement statement=connection.createStatement();
        ResultSet rs=statement.executeQuery("SELECT * FROM "+collTable+" WHERE id = '"+id+"'");
        if(rs.next()){
            if (!rs.getString("login").equals(city.getUser())) {
                statement.close();
                return "Извините, вы не имеете прав для замены этого объекта";
            }
        else{
            removeIDBD(id,city.getUser());
            insertColl(city,true,city.getUser());
        }
        }
        statement.close();
        return null;
    }
}
