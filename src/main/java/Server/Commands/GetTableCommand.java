package Server.Commands;

import Server.Launch.CityService;
import Server.Launch.ControlUnit;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class GetTableCommand implements Command {
    private final CityService cityService;
    private final String name = "getTable";
    private final int argsSize = 0;
    private final boolean isButton = false;

    public GetTableCommand(CityService cityService, ControlUnit controlUnit) {
        controlUnit.addCommand(name, this);
        this.cityService = cityService;
    }

    @Override
    public Request execute(CommandUtils commandUtils) throws IOException, SQLException, NoSuchAlgorithmException {
        return cityService.getTable();
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
