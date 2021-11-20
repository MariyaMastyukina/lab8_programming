package Utils.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private Connection connection;

    public Connection connectDB(String URL, String login, String password) throws SQLException {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(URL, login, password);
            initializeDB();
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Не удалось подключиться к указанной базе данных((");
            System.exit(1);
            return null;
        }
    }

    private void initializeDB() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS USERS" +
                "(id SERIAL PRIMARY KEY, " +
                " login VARCHAR(255), " +
                " password text, " +
                " salt VARCHAR(255));";
        statement.executeUpdate(sql);
        sql = "CREATE TABLE IF NOT EXISTS CITIES" +
                "(id SERIAL PRIMARY KEY, " +
                " name VARCHAR(255), " +
                " coord_x float, " +
                " coord_y float," +
                " creation_date timestamp," +
                " area double precision," +
                " population int," +
                " meters_above_sea_level int," +
                " isCapital boolean," +
                " climate text," +
                " government text," +
                " governor text," +
                " login text);";
        statement.executeUpdate(sql);
    }
}

