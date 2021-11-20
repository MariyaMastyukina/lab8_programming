package Server.Commands;

import Server.Launch.CityService;
import Server.Launch.ControlUnit;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
public class HistoryCommand implements Command {
    private final CityService cityService;
    private final int argsSize = 0;
    private final String name = "history";
    private final boolean isButton = true;
    private ControlUnit controlUnit;

    public HistoryCommand(ControlUnit controlUnit, CityService cityService) throws IOException {
        controlUnit.addCommand(name, this);
        this.controlUnit = controlUnit;
        this.cityService = cityService;
    }

    @Override
    public Request execute(CommandUtils commandUtils) throws IOException {
        return cityService.history(controlUnit);
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

