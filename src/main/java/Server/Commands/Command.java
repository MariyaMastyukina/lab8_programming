package Server.Commands;

import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface Command {

    Request execute(CommandUtils commandUtils) throws IOException, SQLException, NoSuchAlgorithmException;

    String getName();

    int getArgsSize();

    boolean isButton();
}
