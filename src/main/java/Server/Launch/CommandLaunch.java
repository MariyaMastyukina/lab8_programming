package Server.Launch;

import Server.Commands.*;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.util.concurrent.Callable;


public class CommandLaunch implements Callable<Request> {
    private final CommandUtils commandUtils;
    private final ControlUnit controlUnit;

    public CommandLaunch(CityService cityService, CommandUtils commandUtils, ControlUnit controlUnit) throws IOException {
        this.controlUnit = controlUnit;
        this.commandUtils = commandUtils;
        initCommands(cityService, controlUnit);
    }

    private void initCommands(CityService cityService, ControlUnit controlUnit) throws IOException {
        new GroupCountingByPopulationCommand(cityService, controlUnit);
        new HelpCommand(controlUnit);
        new SortCommand(controlUnit, cityService);
        new RemoveByIdCommand(controlUnit, cityService);
        new RemoveAllBYMetersAboveSeaLevelCommand(cityService, controlUnit);
        new ClearCommand(controlUnit, cityService);
        new HistoryCommand(controlUnit, cityService);
        new RemoveLastCommand(controlUnit, cityService);
        new AddCommand(controlUnit, cityService);
        new UpdateCommand(controlUnit, cityService);
        new SignInCommand(controlUnit);
        new CheckInCommand(controlUnit);
        new GetTableCommand(cityService, controlUnit);
    }

    @Override
    public Request call() throws Exception {
        Request answer = null;
        try {
            answer = controlUnit.executeCommand(commandUtils.getNameCommand(), commandUtils);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }
}


