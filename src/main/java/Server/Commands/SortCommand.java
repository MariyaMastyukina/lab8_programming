package Server.Commands;

import Server.Comparators.*;
import Server.Launch.CityService;
import Server.Launch.ControlUnit;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.sql.SQLException;

public class SortCommand implements Command {
    private final CityService cityService;
    private final int argsSize = 0;
    private final String name = "sort";
    private final boolean isButton = false;

    public SortCommand(ControlUnit controlUnit, CityService cityService) throws IOException {
        controlUnit.addCommand(name, this);
        this.cityService = cityService;
    }

    @Override
    public Request execute(CommandUtils commandUtils) throws IOException, SQLException {
        if (commandUtils.getOption().equals("id")) {
            return cityService.sort(new IDComparator());
        } else if (commandUtils.getOption().equals("name")) {
            return cityService.sort(new NameComparator());
        } else if (commandUtils.getOption().equals("x")) {
            return cityService.sort(new XComparator());
        } else if (commandUtils.getOption().equals("y")) {
            return cityService.sort(new YComparator());
        } else if (commandUtils.getOption().equals("date")) {
            return cityService.sort(new DateComparator());
        } else if (commandUtils.getOption().equals("area")) {
            return cityService.sort(new AreaComparartor());
        } else if (commandUtils.getOption().equals("population")) {
            return cityService.sort(new PopulationComparator());
        } else if (commandUtils.getOption().equals("meters")) {
            return cityService.sort(new MetersComparator());
        } else if (commandUtils.getOption().equals("capital")) {
            return cityService.sort(new CapitalComparator());
        } else if (commandUtils.getOption().equals("climate")) {
            return cityService.sort(new ClimateComparator());
        } else if (commandUtils.getOption().equals("government")) {
            return cityService.sort(new GovernmentComparator());
        } else if (commandUtils.getOption().equals("governor")) {
            return cityService.sort(new GovernorComparator());
        } else {
            return cityService.sort(new UserComparator());
        }
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
