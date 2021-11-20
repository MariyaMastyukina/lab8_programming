package Server.Commands;

import Server.Launch.ControlUnit;
import Utils.ConnectionUtils.Request;
import Utils.DBUtils.UserDAO;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class SignInCommand implements Command {
    private final int argsSize = 0;
    private final String name = "sign_in";
    private final boolean isButton = false;

    public SignInCommand(ControlUnit controlUnit) {
        controlUnit.addCommand(name, this);
    }

    @Override
    public Request execute(CommandUtils commandUtils) throws IOException, SQLException, NoSuchAlgorithmException {
        String response = UserDAO.signIn(commandUtils.getLogin(), commandUtils.getPassword());
        if (response == null)
            return new Request("Вход произошел успешно", null, "Success");
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
