package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;

    public Connection connectDB(String URL, String login, String password) throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, login, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось подключиться к указанной базе данных((");
            System.exit(1);
            return null;
        }
    }
}

