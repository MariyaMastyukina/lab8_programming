package Server.Commands;

import Server.Launch.ControlUnit;
import Utils.ConnectionUtils.Request;
import Utils.DBUtils.UserDAO;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CheckInCommand implements Command {
    private final String name = "check_in";
    private final int argsSize = 0;
    private final boolean isButton = false;

    public CheckInCommand(ControlUnit controlUnit) {
        controlUnit.addCommand(name, this);
    }

    @Override
    public Request execute(CommandUtils commandUtils) throws IOException, SQLException, NoSuchAlgorithmException {
        String response = UserDAO.checkIn(commandUtils.getLogin(), commandUtils.getPassword());
        if (response == null)
            return new Request("Пользователь " + commandUtils.getNameCommand() + " зарегистрирован", null, "Success");
        return new Request(response, null, "Error");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getArgsSize() {
        return argsSize;
    }

    @Override
    public boolean isButton() {
        return isButton;
    }
}
