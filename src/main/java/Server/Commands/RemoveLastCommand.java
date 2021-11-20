package Server.Commands;

import Server.Launch.CityService;
import Server.Launch.ControlUnit;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.sql.SQLException;

public class RemoveLastCommand implements Command {
    private final CityService cityService;
    private final int argsSize = 0;
    private final String name = "remove_last";
    private final boolean isButton = true;

    public RemoveLastCommand(ControlUnit controlUnit, CityService cityService) throws IOException {
        controlUnit.addCommand(name, this);
        this.cityService = cityService;
    }

    @Override
    public Request execute(CommandUtils commandUtils) throws IOException, SQLException {
        return cityService.removeLast(commandUtils.getLogin());
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
