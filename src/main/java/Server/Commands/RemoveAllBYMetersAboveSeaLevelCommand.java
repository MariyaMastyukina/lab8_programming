package Server.Commands;

import Server.Launch.CityService;
import Server.Launch.ControlUnit;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.sql.SQLException;


public class RemoveAllBYMetersAboveSeaLevelCommand implements Command {
    private final CityService cityService;
    private final int argsSize = 1;
    private final String name = "remove_all_by_meters_above_sea_level";
    private final boolean isButton = true;

    public RemoveAllBYMetersAboveSeaLevelCommand(CityService cityService, ControlUnit controlUnit) throws IOException {
        controlUnit.addCommand(name, this);
        this.cityService = cityService;
    }

    @Override
    public Request execute(CommandUtils commandUtils) throws IOException, SQLException {
        return cityService.removeByMetersAboveSeaLevel(Integer.parseInt(commandUtils.getOption()), commandUtils.getLogin());
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
