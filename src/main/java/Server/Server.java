package Server;

import Server.Launch.ServerLaunch;

import java.io.IOException;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws IOException, SQLException {
        ServerLaunch.startServer();
    }
}
