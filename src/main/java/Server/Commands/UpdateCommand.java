package Server.Commands;

import Server.Launch.CityService;
import Server.Launch.ControlUnit;
import Server.Model.*;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UpdateCommand implements Command {
    private final CityService cityService;
    private final int argsSize = 1;
    private final String name = "update";
    private final boolean isButton = true;

    public UpdateCommand(ControlUnit controlUnit, CityService cityService) {
        controlUnit.addCommand(name, this);
        this.cityService = cityService;
    }

    @Override
    public Request execute(CommandUtils commandUtils) throws IOException, SQLException {
        City city = updateCity(commandUtils.getArgs(), commandUtils.getLogin(), Long.parseLong(commandUtils.getOption()));
        return cityService.update(city);
    }

    private City updateCity(List<String> args, String login, long id) {
        City city = new City();
        city.setId(id);
        city.setName(args.get(0));
        city.setCoordinates(new Coordinates(args.subList(1, 3)));
        city.setCreationDate(LocalDateTime.now());
        city.setArea(Double.parseDouble(args.get(3)));
        city.setPopulation(Integer.parseInt(args.get(4)));
        city.setMetersAboveSeaLevel(Integer.parseInt(args.get(5)));
        city.setCapital(Boolean.parseBoolean(args.get(6)));
        city.setClimate(Climate.valueOf(args.get(7)));
        city.setGovernment(Government.valueOf(args.get(8)));
        city.setGovernor(new Human(args.get(9)));
        city.setUser(login);
        return city;
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